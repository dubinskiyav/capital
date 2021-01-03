package biz.gelicon.capital;

import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import biz.gelicon.capital.utils.DatebaseUtils;
import biz.gelicon.capital.utils.Proba;
import biz.gelicon.capital.utils.TestAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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


    public static void main(String[] args) {
        SpringApplication.run(CapitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("StartApplication...");

        // Установим тип СУБД
        DatebaseUtils.setDbType(jdbcTemplate);
        // Тесты
        UnitmeasureRepository unitmeasureRepository = new UnitmeasureRepository();
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setId(1);
        unitmeasureRepository.delete(unitmeasure);

        unitmeasure.setName("имя");
        System.out.println(unitmeasure.getName());
        Field field = unitmeasure.getClass().getDeclaredField("name");
        field.setAccessible(true);
        System.out.println((String) field.get(unitmeasure));
        field.set(unitmeasure, "new value");
        System.out.println((String) field.get(unitmeasure));


        TestAnnotation testAnnotation = new TestAnnotation();
        testAnnotation.get();
        Class<? extends Class> cls = TestAnnotation.class.getClass();
        System.out.println(cls);
        System.out.println((new TestAnnotation()).getClass());
        System.out.println(testAnnotation.getClass());
        Class<? extends TestAnnotation> cls1  = testAnnotation.getClass();
        Class cls2  = testAnnotation.getClass();
        Method m = cls1.getMethod("get");
        if(m.isAnnotationPresent(Proba.class)) {
            Proba an = (Proba) m.getDeclaredAnnotations()[0];
            System.out.println(an.id());
            System.out.println(an.type());
        }
    }

}

