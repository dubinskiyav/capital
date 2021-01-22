package biz.gelicon.capital.config;

import biz.gelicon.capital.utils.RecreateDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApp implements ApplicationRunner {

    @Autowired
    RecreateDatabase recreateDatabase;

    private static final Logger logger = LoggerFactory.getLogger(InitApp.class);

    public void run(ApplicationArguments args) {
        logger.info("InitApp running...");
        logger.info("recreateDatabase...");
        recreateDatabase.recreate();
        logger.info("recreateDatabase...Ok");
        logger.info("InitApp running...Ok");
    }
}