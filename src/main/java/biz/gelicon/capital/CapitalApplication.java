package biz.gelicon.capital;

import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import biz.gelicon.capital.utils.DatebaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    MeasureRepository measureRepository;

    public static void main(String[] args) {
        SpringApplication.run(CapitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("StartApplication...");

        // Установим тип СУБД
        DatebaseUtils.setDbType(jdbcTemplate);

        // Тесты
        Measure measure = new Measure();
        measure.setId(1);
        measureRepository.delete(measure);


        UnitmeasureRepository unitmeasureRepository = applicationContext.getBean(UnitmeasureRepository.class);
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setId(-1);
        unitmeasureRepository.delete(unitmeasure);

    }

}

