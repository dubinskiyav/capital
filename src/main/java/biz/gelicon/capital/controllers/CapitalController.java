package biz.gelicon.capital.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
@Tag(name="Контроллер Капитал", description="Корневой контроллер")
public class CapitalController {


    @RequestMapping(value = "/capital")
    public @ResponseBody String capitalController() {

        return "Capital WEB";
    }
}