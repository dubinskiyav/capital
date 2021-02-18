package biz.gelicon.capital.controllers;

import biz.gelicon.capital.dto.UnitmeasureDTO;
import biz.gelicon.capital.exceptions.PostRecordException;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.TableRepository;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import biz.gelicon.capital.utils.ConvertUnils;
import biz.gelicon.capital.utils.DatabaseUtils;
import biz.gelicon.capital.utils.GridDataOption;
import biz.gelicon.capital.utils.TableMetadata;
import biz.gelicon.capital.validators.UnitmeasureValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер Справочника единиц измерения
 */
//это композиция из @Controller и @ResponseBody, если мы не используем @ResponseBody
// в сигнатуре метода, то нам нужно использовать @Restcontroller
// @ResponceBody уже не требуется
// https://java.fandom.com/ru/wiki/@RequestMapping
@RestController
@Tag(name = "Единицы измерения", description = "Контроллер для справочника единиц измерения")
@RequestMapping(value = "/unitmeasure",    // задаёт "каталог", в котором будут размещаться методы контроллера
        consumes = "application/json; charset=UTF-8", // определяет, что Content-Type запроса клиента должен быть "application/json"
        produces = "application/json; charset=UTF-8")
// определяет, что возвращать будет "application/json"
public class UnitmeasureController {

    private static final Logger logger = LoggerFactory.getLogger(UnitmeasureController.class);

    @Autowired
    UnitmeasureRepository unitmeasureRepository;

    @Autowired
    private UnitmeasureValidator unitmeasureValidator; // Валидатор для дополнительной проверки полей

    @Autowired
    JdbcTemplate jdbcTemplate;

    @InitBinder   // Чтобы не вызывать самому валидатор - он сам вызовется
    protected void initBinder(WebDataBinder binder) { // todo Непонятно что это
        //binder.setValidator(unitmeasureValidator); // todo вернуть установку автовалидатора - непонятно что
    }

    @RequestMapping(value = "json", method = RequestMethod.POST)
    @Operation(
            summary = "Список единиц измерения",
            description = "Возвращает список единиц измерения"
    )
    public List<Unitmeasure> unitmeasure(
            @RequestBody GridDataOption gridDataOption
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        String gridDataOptionAsString = null;
        try {
            gridDataOptionAsString = objectMapper.writeValueAsString(gridDataOption);
        } catch (JsonProcessingException e) {
            String errText = "Ошибка при преобразовании";
            logger.error(errText);
            throw new RuntimeException(errText);
        }
        logger.info("unitmeasure: gridDataOptionAsString = " + gridDataOptionAsString);
        List<Unitmeasure> unitmeasureList =
                unitmeasureRepository.findAll(gridDataOption.buildPageRequest());
        return unitmeasureList;
    }

    @Operation(
            summary = "Список DTO единиц измерения",
            description = "Возвращает список DTO единиц измерения"
    )
    @RequestMapping(value = "dto", method = RequestMethod.POST)
    public List<UnitmeasureDTO> unitmeasureDTO(
            @RequestBody GridDataOption gridDataOption
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        String gridDataOptionAsString = null;
        try {
            gridDataOptionAsString = objectMapper.writeValueAsString(gridDataOption);
        } catch (JsonProcessingException e) {
            String errText = "Ошимбка при преобразовании";
            logger.error(errText);
            throw new RuntimeException(errText);
        }
        logger.info("unitmeasure: gridDataOptionAsString = " + gridDataOptionAsString);
        String sqlText = "\n"
                + "SELECT UM.unitmeasure_id,\n"
                + "       UM.unitmeasure_name,\n"
                + "       UM.unitmeasure_shortname,\n"
                + "       MU.measureunit_id,\n"
                + "       MU.measureunit_priority,\n"
                + "       M.measure_id,\n"
                + "       M.measure_name\n"
                + "FROM   unitmeasure UM\n"
                + "       LEFT OUTER JOIN measureunit MU ON MU.unitmeasure_id = UM.unitmeasure_id\n"
                + "       LEFT OUTER JOIN measure M ON M.measure_id = MU.measure_id\n";
        // из параметра получим page
        Pageable page = gridDataOption.buildPageRequest();
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = TableRepository.tableMetadataMap.get("unitmeasure");
        // Поправим названия полей - заменим имена на колонки
        // все остальыне поля должны совпадать с выбираемыми !!!
        page = ConvertUnils.transformSortColumnName(page, tableMetadata);

        String orderBy = ConvertUnils.buildOrderByFromPegable(page);
        String limit = ConvertUnils.buildLimitFromPegable(page);
        if (orderBy != null) {
            sqlText = sqlText + " " + orderBy;
        }
        if (limit != null) {
            sqlText = sqlText + " " + limit;
        }
        //logger.info(sqlText);

        List<UnitmeasureDTO> retList = jdbcTemplate.query(sqlText,
                (rs, rowNum) ->
                        new UnitmeasureDTO(
                                rs.getInt("unitmeasure_id"),
                                rs.getString("unitmeasure_name"),
                                rs.getString("unitmeasure_shortname"),
                                rs.getInt("measureunit_id"),
                                rs.getInt("measureunit_priority"),
                                rs.getInt("measure_id"),
                                rs.getString("measure_name")
                        )
        );
        return retList;
    }


    @Operation(
            summary = "Начальные данные для добавления",
            description = "Возвращает сущность с установленными начальными данными для добавления"
    )
    @RequestMapping(value = "add", // если не указать "/" в начале, то будет добавляться к value
            // всего класса, в данном случае "/unitmeasure/add"
            method = RequestMethod.GET) // Получение данных
    // будет производить json в результате, и он будет отправлен в ответ.
    public Unitmeasure add() {
        Unitmeasure unitmeasure = new Unitmeasure();
        //unitmeasure.setUnitmeasureName("Длина");
        return unitmeasure;
    }

    @Operation(
            summary = "Начальные данные для изменения",
            description = "Возвращает сущность по id с установленными начальными данными для изменения"
    )
    @RequestMapping(value = "upd/{id}", method = RequestMethod.GET)
    public Unitmeasure upd(
            // Берем из пути id https://coderoad.ru/19803731/Spring-%D0%B2-MVC-PathVariable
            @PathVariable("id") Integer id
    ) {
        Unitmeasure unitmeasure = unitmeasureRepository.findById(id);
        if (unitmeasure == null) {
            String errText = "Запись с id = " + id + " не существует";
            logger.error(errText);
            throw new RuntimeException(errText);
        }
        return unitmeasure;
    }

    @Operation(
            summary = "Удаление",
            description = "Удяление списка сущностей разделенных запятой"
    )
    @RequestMapping(value = "del/{ids}", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(@PathVariable("ids") String ids) {
        for (String s : ids.replaceAll("\\s+", "").split(",")) {
            Integer id = Integer.parseInt(s);
            Unitmeasure unitmeasure = new Unitmeasure();
            unitmeasure.setId(id);
            DataBinder dataBinder = new DataBinder(unitmeasure);
            try {
                unitmeasureRepository.delete(id);
            } catch (RuntimeException e) {
                String errText = "Ошибка удаления записи с id = " + id;
                logger.error(errText);
                dataBinder.getBindingResult().rejectValue("id", "", e.getCause().getMessage());
                throw new PostRecordException(dataBinder.getBindingResult(), e);
            }
        }
    }

    @Operation(
            summary = "Сохранение",
            description = "Добавляет или изменяет сущность в базе данных"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "post", method = RequestMethod.POST)
    public Unitmeasure post(
            @RequestBody Unitmeasure unitmeasure
    ) {
        DataBinder dataBinder = new DataBinder(unitmeasure);
        dataBinder.addValidators(unitmeasureValidator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            logger.error(dataBinder.getBindingResult().getAllErrors().toString());
            throw new PostRecordException(dataBinder.getBindingResult(), new Throwable());
        }
        try {
            unitmeasureRepository.insertOrUpdate(unitmeasure);
        } catch (RuntimeException e) {
            String errText = "Ошибка сохранения записи " + unitmeasure.toString();
            logger.error(errText);
            dataBinder.getBindingResult()
                    .rejectValue("id", "", unitmeasure.toString() + " " + e.getMessage());
            throw new PostRecordException(dataBinder.getBindingResult(), e);
        }
        return unitmeasure;
    }

}