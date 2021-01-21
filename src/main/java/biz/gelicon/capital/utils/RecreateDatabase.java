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

    public void recreateAllTables() {
        System.out.println("Tests started");
        // Открываем таранзакцию
        defaultTransactionDefinition = new DefaultTransactionDefinition();
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            // Удаляем все таблицы
            materialunitmeasureRepository.drop();
            serviceRepository.drop();
            materialRepository.drop();
            materiallevelRepository.drop();
            unitmeasurerecalcRepository.drop();
            measureunitRepository.drop();
            unitmeasureRepository.drop();
            measureRepository.drop();

            // Создаем все таблицы

            measureRepository.create();
            unitmeasureRepository.create();
            measureunitRepository.create();
            unitmeasurerecalcRepository.create();
            materiallevelRepository.create();
            materialRepository.create();
            serviceRepository.create();
            materialunitmeasureRepository.create();

            // Грузим все таблицы
            measureRepository.load();
            unitmeasureRepository.load();
            measureunitRepository.load();
            unitmeasurerecalcRepository.load();
            materiallevelRepository.load();
            materialRepository.load();
            serviceRepository.load();
            materialunitmeasureRepository.load();

            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
        }
        List<Measure> measureList =
                measureRepository.findAll();
        System.out.println("Tests ended");

    }


}
