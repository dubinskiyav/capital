package biz.gelicon.capital.controllers;

import biz.gelicon.capital.exceptions.DeleteRecordException;
import biz.gelicon.capital.exceptions.PostRecordException;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import biz.gelicon.capital.utils.GridDataOption;
import biz.gelicon.capital.validators.UnitmeasureValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping(value = "/unitmeasure",    // задаёт "каталог", в котором будут размещаться методы контроллера
        consumes = "application/json; charset=UTF-8", // определяет, что Content-Type запроса клиента должен быть "application/json"
        produces = "application/json; charset=UTF-8") // определяет, что возвращать будет "application/json"
public class UnitmeasureController {

    private static final Logger logger = LoggerFactory.getLogger(UnitmeasureController.class);

    @Autowired
    UnitmeasureRepository unitmeasureRepository;

    @Autowired
    private UnitmeasureValidator unitmeasureValidator; // Валидатор для дополнительной проверки полей

    @InitBinder   // Чтобы не вызывать самому валидатор - он сам вызовется
    protected void initBinder(WebDataBinder binder) { // todo Непонятно что это
        //binder.setValidator(unitmeasureValidator); // todo вернуть установку автовалидатора - непонятно что
    }

    @RequestMapping(value = "json", method = RequestMethod.POST)
    public List<Unitmeasure> unitmeasure(
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
        List<Unitmeasure> unitmeasureList =
                unitmeasureRepository.findAll(gridDataOption.buildPageRequest());
        return unitmeasureList;
    }



    /**
     * Добавление меры измерения Получение данных для заполнения формы добавления начальными
     * значениями
     *
     * @return
     */
    @RequestMapping(value = "add", // если не указать "/" в начале, то будет добавляться к value
            // всего класса, в данном случае "/unitmeasure/add"
            method = RequestMethod.GET) // Получение данных
    // будет производить json в результате, и он будет отправлен в ответ.
    public Unitmeasure add() {
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setName("Длина");
        return unitmeasure;
    }

    /**
     * Редактирование меры измерения
     *
     * @return
     */
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
                unitmeasureRepository.delete(id);
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
    public Unitmeasure post(
            @RequestBody Unitmeasure unitmeasure
    ) {
        DataBinder dataBinder = new DataBinder(unitmeasure);
        dataBinder.addValidators(unitmeasureValidator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            logger.error(dataBinder.getBindingResult().getAllErrors().toString());
            throw new PostRecordException(dataBinder.getBindingResult());
        }
        try {
            unitmeasureRepository.insertOrUpdate(unitmeasure);
        } catch (RuntimeException e) {
            String errText = "Ошибка сохранения записи " + unitmeasure.toString();
            logger.error(errText);
            dataBinder.getBindingResult().rejectValue("id", "", e.getMessage());
            throw new PostRecordException(dataBinder.getBindingResult(), e);
        }
        return unitmeasure;
    }

}
