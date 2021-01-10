package biz.gelicon.capital.controllers;

import biz.gelicon.capital.exceptions.DeleteRecordException;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.utils.ErrorJ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер Справочника единиц измерения
 */
//это композиция из @Controller и @ResponseBody, если мы не используем @ResponseBody
// в сигнатуре метода, то нам нужно использовать @Restcontroller
// @ResponceBody уже не требуется
// https://java.fandom.com/ru/wiki/@RequestMapping
@RestController
@RequestMapping(value = "/measure",    // задаёт "каталог", в котором будут размещаться методы контроллера
        consumes = "application/json", // определяет, что Content-Type запроса клиента должен быть "application/json"
        produces = "application/json") // определяет, что возвращать будет "application/json"
public class MeasureController {

    private static final Logger logger = LoggerFactory.getLogger(MeasureController.class);

    @Autowired
    MeasureRepository measureRepository;

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
    @RequestMapping(value = "upd/{id}",
            method = RequestMethod.GET)
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

    /** Множественное удаление
     *
     * @param ids
     */
    @RequestMapping(value = "del/{ids}",
            method = RequestMethod.POST)
    public void del(@PathVariable("ids") String ids) {
        for (String s : ids.replaceAll("\\s+", "").split(",")) {
            Integer id = Integer.parseInt(s);
            try {
                measureRepository.delete(id);
            } catch (RuntimeException e) {
                String errText = "Ошибка удаления записи с id = " + id;
                logger.error(errText);
                throw new DeleteRecordException(errText,e);
            }
        }
    }

}
