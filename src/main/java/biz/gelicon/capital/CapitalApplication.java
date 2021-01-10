package biz.gelicon.capital;

import biz.gelicon.capital.utils.Test01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication    /* Точка входа в приложение весенней загрузки — класс,
                             содержащий аннотацию @SpringBootApplication и метод main.
                             Spring Boot автоматически сканирует все компоненты,
                             включенные в проект, используя аннотацию @ComponentScan */
/* Указать Spring, где искать аннотированные классы */
/*
@ComponentScan(basePackages = {
        "biz.gelicon.capital.repository",
        "biz.gelicon.capital.controllers"})
 */
public class CapitalApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CapitalApplication.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        logger.info("Running...");
        applicationContext = SpringApplication.run(CapitalApplication.class, args);
        logger.info("Reading ApplicationContext...Ok");
        // Тесты
        Test01 test01 = CapitalApplication.getApplicationContext().getBean(Test01.class);
        test01.test1();
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("StartApplication...");
        logger.info("StartApplication...Ok");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}

