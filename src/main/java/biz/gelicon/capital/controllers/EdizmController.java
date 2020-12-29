package biz.gelicon.capital.controllers;

import biz.gelicon.capital.model.Edizm;
import biz.gelicon.capital.validators.EdizmValidator;
import biz.gelicon.capital.repository.EdizmRepositoryJdbc;
import biz.gelicon.capital.utils.DatebaseUtils;
import biz.gelicon.capital.utils.ErrorJ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Controller  /* Аннотация @Controller - говорит о том, что данный класс является контроллером.
                Контроллеры в Spring обрабатывают HTTP запросы на определенный адреса. */
@RequestMapping(value = "/edizm")  /* Данная аннотация говорит нам о том, что данный класс
                                          обрабатывает HTTP GET запросы на адрес /edizm.
                                          Иными словами, данный метод сработает, если кто-то
                                          перейдет по адресу /edizm. */
public class EdizmController {

    private static final Logger logger = LoggerFactory.getLogger(EdizmController.class);
    Connection connection;

    @Autowired
    private EdizmRepositoryJdbc edizmRepositoryJdbc;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private EdizmValidator validator; // Валидатор для дополнительной проверки полей

    @InitBinder
    protected void initBinder(WebDataBinder binder) { // todo Непонятно что это
        binder.setValidator(validator);
    }

    // Главная форма
    @RequestMapping(value = "index")
    public String edizm(
            /* пары ключ-значение для передачи данных из Java кода в html страницы */
            Model model,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "name_mask", required = false) String name_mask
    ) {
        logger.info("edizm - Start");
        List<Edizm> edizmList = new ArrayList<>();
        int masterCount = 0;
        if (page == null) { page = 0; }
        if (name_mask == null) {
            edizmList = edizmRepositoryJdbc.findAll();
        } else {
            edizmList = edizmRepositoryJdbc.findByName(name_mask);
        }
        masterCount = edizmList.size();
        masterCount = edizmRepositoryJdbc.count();
        String alias = context.getEnvironment().getProperty("spring.datasource.url");
        model.addAttribute("alias", alias);
        model.addAttribute("edizmlist", edizmList);
        model.addAttribute("masterCount", masterCount);

        logger.info("edizmController - Finish");
        return "edizm/index"; /* метод контроллера должен вернуть имя представления.
                           Далее Spring будет искать html файл, с таким именем,
                           который и вернет в качестве ответа на HTTP запрос.
                           В данном случае возьмет edizm_null.html а вернет как edizm.html */
    }

    // Форма добавленя
    @RequestMapping(value = "add")
    public String add(
            /* пары ключ-значение для передачи данных из Java кода в html страницы */
            Model model,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "name_mask", required = false) String name_mask
    ) {
        logger.info("add - Start");
        Edizm edizm = new Edizm();
        // Для пробы
        edizm.setBlockflagB(false);
        model.addAttribute("edizm", edizm);
        logger.info("add - Finish");
        return "edizm/form";
    }

    // Изменение
    @RequestMapping(value = "upd/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    public String upd(
            Model model,
            @PathVariable("id") Integer id
    ) {
        logger.info("upd - Start edizm_id = " + id);
        // Сляпаем из id
        Edizm edizm = edizmRepositoryJdbc.findById(id);
        model.addAttribute("edizm", edizm);
        logger.info("upd - Finish with result = 1");
        return "edizm/form";
    }


    // Множественное удаление
    @RequestMapping(value = "del_ids/{ids}",
            method = RequestMethod.POST,
            produces = "application/json")
    @Transactional(propagation = Propagation.REQUIRED)
    @ResponseBody // Возвращает в ответ тело
    public ErrorJ delIds(
            @PathVariable("ids") String ids
    ) {
        logger.info("delele (" + ids + ")");
        ErrorJ errorJ = new ErrorJ();
        for (String s : ids.replaceAll("\\s+", "").split(",")) {
            Integer id = Integer.parseInt(s);
            try {
                Integer i = edizmRepositoryJdbc.delete(id);
            } catch (Exception e) {
                errorJ.setCode(516);
                errorJ.setMessage(DatebaseUtils.makeErrorMessage(e));
                errorJ.setExceptionText(e.getMessage());
                return errorJ;
            }
        }
        logger.info("Deleted");
        return errorJ;
    }

    // Выполнение добавления или изменения
    @RequestMapping(value = "post", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public String postEdizm(
            @Valid @ModelAttribute("edizm") Edizm edizm, // Говорим о том, что надо выполнить проверку по модели
            BindingResult result, // и результат закинуть во второй аргумент, то есть сюда
            Model model
    ) {
        logger.info("Saving... " + edizm.toString());
        // Сначала сами проверим
        // Это можно перенести в вализатор для Edizm
        // Здесь оставил только для примера
        if(edizm.getCode().toLowerCase().equals("код")) {
            result.rejectValue("code", "", "Поле 'Код' не может иметь значение '" + edizm.getCode() + "'");
        }
        // Проверим с помощью валидатора
        //validator.validate(edizm, result); // Это не надо так как сделали initBinder
                                              // И наш валидатор вызовется сам
        // Проверим на наличие ошибок
        if(result.hasErrors()) { // Если есть ошибки валидации полей - возвращаемся
            logger.error(result.getAllErrors().toString());
            return "edizm/form"; // result попадает в форму сам, об этом заботится не надо
            // в форме так
            // <div class="field">Обозначение*: <input type="text" th:field="*{notation}" placeholder="Введите обозначение"></div>
        }
        // Запомним что это удаление или добавление
        Integer idSaved = edizm.getId();
        try {
            edizmRepositoryJdbc.save(edizm);
        } catch (Exception e) {
            if (idSaved == null) {
                // Это было добавление - обнулим id
                edizm.setId(null);
            }
            if (e instanceof DataAccessException) { // Ошибка добавления в БД
                result.rejectValue("id", "", DatebaseUtils.makeErrorMessage(e));
            } else {
                result.rejectValue("id", "", e.getMessage());
            }
            return "edizm/form";
        }
        return "redirect:/edizm/index";
    }


}
