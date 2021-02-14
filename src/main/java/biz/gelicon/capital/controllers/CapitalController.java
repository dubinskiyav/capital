package biz.gelicon.capital.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CapitalController {


    @RequestMapping(value = "/capital")
    public @ResponseBody String capitalController() {

        return "Capital WEB";
    }
}