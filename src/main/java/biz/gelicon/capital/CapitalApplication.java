package biz.gelicon.capital;

import biz.gelicon.capital.model.Edizm;
import biz.gelicon.capital.repository.EdizmRepository;
import biz.gelicon.capital.utils.DatebaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

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
    private EdizmRepository edizmRepository;

    public static void main(String[] args) {
        SpringApplication.run(CapitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("StartApplication...");
        if (false) {
            testEdizm();
        }

        // Установим тип СУБД
        DatebaseUtils.setDbType(jdbcTemplate);
    }

    private void testEdizm() {
        logger.info("testEdizm...Start");
        List<Edizm> edizmList = Arrays.asList(
                new Edizm(1, "Ампер", "А", 0, "а"),
                new Edizm(2, "Вольт", "В", 0, "в"),
                new Edizm(3, "Люмен", "Л", 0, "лм"),
                new Edizm(4, "Фунт", "Ф", 0, "ф")
        );
        edizmList.stream().map(e -> e.getId())
                .forEach(i -> logger.info("delete={}", edizmRepository.delete(i)));
        logger.info("insert");
        edizmList.forEach(edizm -> {
            logger.info("insert...{}", edizm.toString());
            edizmRepository.insert(edizm);
        });
        logger.info("count={}", edizmRepository.count());
        logger.info("update={}", edizmRepository.update(new Edizm(4, "Фут", "ФТ", 0, "фт")));
        logger.info("findAll:");
        edizmRepository.findAll().forEach(t -> logger.info(t.toString()));
        String s = "а";
        logger.info("findByName: {}", s);
        edizmRepository.findByName(s).forEach(t -> logger.info(t.toString()));
        int i = 1355549;
        logger.info("findById({})...", i);
        Edizm edizm = edizmRepository.findById(i);
        if (edizm != null) {
            logger.info(edizm.toString());
            logger.info("findById({})={}", i, edizm.toString());
        } else {
            logger.info("Не нашли с id={}", i);
        }
        int id = 2001;
        logger.info("getNameById({}) = {}", id, edizmRepository.getNameById(id));
        id = 2;
        logger.info("getNameById({}) = {}", id, edizmRepository.getNameById(id));
        edizmList.stream().map(e -> e.getId())
                .forEach(e -> logger.info("delete={}", edizmRepository.delete(e)));
    }

}

