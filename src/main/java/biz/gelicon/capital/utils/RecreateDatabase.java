package biz.gelicon.capital.utils;

import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.repository.MaterialRepository;
import biz.gelicon.capital.repository.MateriallevelRepository;
import biz.gelicon.capital.repository.MaterialunitmeasureRepository;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.MeasureunitRepository;
import biz.gelicon.capital.repository.ServiceRepository;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import biz.gelicon.capital.repository.UnitmeasurerecalcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * Пересоздание всех таблиц базы данных capital
 */
@Repository
public class RecreateDatabase {

    static Logger logger = LoggerFactory.getLogger(RecreateDatabase.class);

    @Autowired
    private PlatformTransactionManager transactionManager;
    DefaultTransactionDefinition defaultTransactionDefinition;
    TransactionStatus transactionStatus;

    @Autowired
    MeasureRepository measureRepository;
    @Autowired
    UnitmeasureRepository unitmeasureRepository;
    @Autowired
    MeasureunitRepository measureunitRepository;
    @Autowired
    UnitmeasurerecalcRepository unitmeasurerecalcRepository;
    @Autowired
    MateriallevelRepository materiallevelRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    MaterialunitmeasureRepository materialunitmeasureRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void create() {
        logger.info("Creating tables ...");
        try {
            measureRepository.create();
            unitmeasureRepository.create();
            measureunitRepository.create();
            unitmeasurerecalcRepository.create();
            materiallevelRepository.create();
            materialRepository.create();
            serviceRepository.create();
            materialunitmeasureRepository.create();
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        logger.info("Creating tables ... Ok");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void drop() {
        logger.info("Dropping tables ...");
        try {
            materialunitmeasureRepository.drop();
            serviceRepository.drop();
            materialRepository.drop();
            materiallevelRepository.drop();
            unitmeasurerecalcRepository.drop();
            measureunitRepository.drop();
            unitmeasureRepository.drop();
            measureRepository.drop();
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        logger.info("Dropping tables ... Ok");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void load() {
        logger.info("Loading tables ...");
        try {
            measureRepository.load();
            unitmeasureRepository.load();
            measureunitRepository.load();
            unitmeasurerecalcRepository.load();
            materiallevelRepository.load();
            materialRepository.load();
            serviceRepository.load();
            materialunitmeasureRepository.load();
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        logger.info("Loading tables ... Ok");
    }

    public void recreate() {
        // Открываем таранзакцию
        defaultTransactionDefinition = new DefaultTransactionDefinition();
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            drop();
            create();
            load();
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
            throw new RuntimeException(errText, e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete() {
        logger.info("Deleting tables ...");
        try {
            materialunitmeasureRepository.deleteAll();
            serviceRepository.deleteAll();
            materialRepository.deleteAll();
            materiallevelRepository.deleteAll();
            unitmeasurerecalcRepository.deleteAll();
            measureunitRepository.deleteAll();
            unitmeasureRepository.deleteAll();
            measureRepository.deleteAll();
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        logger.info("Deleting tables ... Ok");
    }

}
