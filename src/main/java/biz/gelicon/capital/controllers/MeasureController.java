package biz.gelicon.capital.controllers;

import biz.gelicon.capital.exceptions.PostRecordException;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.utils.GridDataOption;
import biz.gelicon.capital.validators.MeasureValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

/**
 * Контроллер Справочника единиц измерения
 */
//это композиция из @Controller и @ResponseBody, если мы не используем @ResponseBody
// в сигнатуре метода, то нам нужно использовать @Restcontroller
// @ResponceBody уже не требуется
// https://java.fandom.com/ru/wiki/@RequestMapping
@RestController
@Tag(name = "Меры измерения", description = "Контроллер для справочника мер измерения")
@RequestMapping(value = "/measure",    // задаёт "каталог", в котором будут размещаться методы контроллера
        consumes = "application/json; charset=UTF-8", // определяет, что Content-Type запроса клиента должен быть "application/json"
        produces = "application/json; charset=UTF-8") // определяет, что возвращать будет "application/json"
public class MeasureController {

    private static final Logger logger = LoggerFactory.getLogger(MeasureController.class);

    @Autowired
    MeasureRepository measureRepository;

    @Autowired
    private MeasureValidator measureValidator; // Валидатор для дополнительной проверки полей

    @InitBinder   // Чтобы не вызывать самому валидатор - он сам вызовется
    protected void initBinder(WebDataBinder binder) { // todo Непонятно что это
        //binder.setValidator(measureValidator); // todo вернуть установку автовалидатора - непонятно что
    }

    @Operation(
            summary = "Список мер измерения",
            description = "Возвращает список мер измерения"
    )
    @RequestMapping(value = "json", method = RequestMethod.POST)
    public List<Measure> measure(
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
        logger.info("measure: gridDataOptionAsString = " + gridDataOptionAsString);
        List<Measure> measureList =
                measureRepository.findAll(gridDataOption.buildPageRequest());
        return measureList;
    }

    @Operation(
            summary = "Начальные данные для добавления",
            description = "Возвращает сущность с установленными начальными данными для добавления"
    )
    @RequestMapping(value = "add", // если не указать "/" в начале, то будет добавляться к value
            // всего класса, в данном случае "/measure/add"
            method = RequestMethod.GET) // Получение данных
    // будет производить json в результате, и он будет отправлен в ответ.
    public Measure add() {
        Measure measure = new Measure();
        //measure.setMeasureName("Длина");
        //logger.info("Measure add " + measure.toString());
        return measure;
    }

    @Operation(
            summary = "Начальные данные для изменения",
            description = "Возвращает сущность по id с установленными начальными данными для изменения"
    )
    @RequestMapping(value = "upd/{id}", method = RequestMethod.GET)
    public Measure upd(
            @Parameter(description = "Идентификатор сущности")
            @PathVariable("id") Integer id
    ) {
        Measure measure = measureRepository.findById(id);
        if (measure == null) {
            String errText = "Запись с id = " + id + " не существует";
            logger.error(errText);
            throw new RuntimeException(errText);
        }
        return measure;
    }

    @Operation(
            summary = "Удаление",
            description = "Удаление списка сущностей разделенных запятой"
    )
    @RequestMapping(value = "del/{ids}", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(@PathVariable("ids") String ids) {
        for (String s : ids.replaceAll("\\s+", "").split(",")) {
            Integer id = Integer.parseInt(s);
            Measure measure = new Measure();
            measure.setId(id);
            DataBinder dataBinder = new DataBinder(measure);
            try {
                measureRepository.delete(id);
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
    public Measure post(
            @RequestBody Measure measure
    ) {
        DataBinder dataBinder = new DataBinder(measure);
        dataBinder.addValidators(measureValidator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            logger.error(dataBinder.getBindingResult().getAllErrors().toString());
            throw new PostRecordException(dataBinder.getBindingResult(), new Throwable());
        }
        try {
            measureRepository.insertOrUpdate(measure);
        } catch (RuntimeException e) {
            String errText = "Ошибка сохранения записи " + measure.toString();
            logger.error(errText);
            dataBinder.getBindingResult().rejectValue("id", "", measure.toString() + " " + e.getMessage());
            throw new PostRecordException(dataBinder.getBindingResult(), e);
        }
        return measure;
    }

}
