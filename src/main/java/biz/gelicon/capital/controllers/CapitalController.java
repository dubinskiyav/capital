package biz.gelicon.capital.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Класс, который обрабатывает HTTP запросы, должен быть отмечен аннотацией @Controller
public class CapitalController {

    private static final Logger logger = LoggerFactory.getLogger(CapitalController.class);

    @RequestMapping(value = "/capital") // за обработку запроса отвечает метод контроллера.
    // Такой метод должен быть объявлен с помощью аннотации @RequestMapping
    public String capitalController(
            Model model
    ) {
        logger.info("Capital start. ");

        return "capital";
    }
}
