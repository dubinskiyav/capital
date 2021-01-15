package biz.gelicon.capital.controllers;

import biz.gelicon.capital.exceptions.BadArgumentsException;
import biz.gelicon.capital.exceptions.DeleteRecordException;
import biz.gelicon.capital.exceptions.PostRecordException;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.utils.GridDataOption;
import biz.gelicon.capital.validators.MeasureValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping(value = "json", method = RequestMethod.POST)
    public List<Measure> measure(
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
        logger.info("measure: gridDataOptionAsString = " + gridDataOptionAsString);
        List<Measure> measureList =
                measureRepository.findAll(gridDataOption.buildPageRequest());
        return measureList;
    }



    /**
     * Добавление меры измерения Получение данных для заполнения формы добавления начальными
     * значениями
     *
     * @return
     */
    @RequestMapping(value = "add", // если не указать "/" в начале, то будет добавляться к value
            // всего класса, в данном случае "/measure/add"
            method = RequestMethod.GET) // Получение данных
    // будет производить json в результате, и он будет отправлен в ответ.
    public Measure add() {
        Measure measure = new Measure();
        measure.setName("Длина");
        return measure;
    }

    /**
     * Редактирование меры измерения
     *
     * @return
     */
    @RequestMapping(value = "upd/{id}", method = RequestMethod.GET)
    public Measure upd(
            // Берем из пути id https://coderoad.ru/19803731/Spring-%D0%B2-MVC-PathVariable
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

    /**
     * Множественное удаление
     *
     * @param ids
     */
    @RequestMapping(value = "del/{ids}", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(@PathVariable("ids") String ids) {
        for (String s : ids.replaceAll("\\s+", "").split(",")) {
            Integer id = Integer.parseInt(s);
            try {
                measureRepository.delete(id);
            } catch (RuntimeException e) {
                String errText = "Ошибка удаления записи с id = " + id;
                logger.error(errText);
                throw new DeleteRecordException(errText, e);
            }
        }
    }

    /**
     * Выполнение добавления или изменения
     *
     * @return
     */
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
            throw new PostRecordException(dataBinder.getBindingResult());
        }
        try {
            measureRepository.insertOrUpdate(measure);
        } catch (RuntimeException e) {
            String errText = "Ошибка сохранения записи " + measure.toString();
            logger.error(errText);
            dataBinder.getBindingResult().rejectValue("id", "", e.getMessage());
            throw new PostRecordException(dataBinder.getBindingResult());
        }
        return measure;
    }

    @GetMapping("throw")
    public void getException() throws Exception {
        logger.info("/exception/throw");
        throw new BadArgumentsException("bad arguments");
    }

}
