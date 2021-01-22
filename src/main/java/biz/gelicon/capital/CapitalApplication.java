package biz.gelicon.capital;

import biz.gelicon.capital.utils.RecreateDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
//@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class CapitalApplication{

    private static final Logger logger = LoggerFactory.getLogger(CapitalApplication.class);

    private static ApplicationContext applicationContext;

    @Value("${recreatedatabase}")
    private Boolean rcdb = false;

    public static void main(String[] args) {
        logger.info("SpringApplication.run...");
        SpringApplication.run(CapitalApplication.class, args);
        logger.info("SpringApplication.run...Ok");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext ac) {
        applicationContext = ac;
    }

}

