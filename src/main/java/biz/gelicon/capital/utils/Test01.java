package biz.gelicon.capital.utils;

import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Measureunit;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.MeasureunitRepository;
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
 * Тестирование базы данных capital <p> различные добавления удаления выборки и пр
 */
@Repository
public class Test01 {

    static Logger logger = LoggerFactory.getLogger(Test01.class);

    @Autowired
    MeasureRepository measureRepository;
    @Autowired
    MeasureunitRepository measureunitRepository;
    @Autowired
    UnitmeasurerecalcRepository unitmeasurerecalcRepository;
    @Autowired
    UnitmeasureRepository unitmeasureRepository;


    @Autowired
    private PlatformTransactionManager transactionManager;
    DefaultTransactionDefinition defaultTransactionDefinition;
    TransactionStatus transactionStatus;

    public void test1() {
        System.out.println("Tests started");

        // Тесты
        defaultTransactionDefinition = new DefaultTransactionDefinition();
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
        }

        System.out.println("Tests ended");

    }


}
