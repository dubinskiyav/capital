package biz.gelicon.capital.utils;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class Test01 {

    @Autowired
    MeasureRepository measureRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;
    DefaultTransactionDefinition defaultTransactionDefinition;
    TransactionStatus transactionStatus;

    public void test1() {
        // Тесты
        defaultTransactionDefinition = new DefaultTransactionDefinition();
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            measureRepository.delete(1);
            Measure measure = new Measure(1,"Длина");
            measureRepository.insert(measure);
            measureRepository.delete(measure);
            measure = new Measure(1,"Ширина");
            measureRepository.insert(measure);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
        }

        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            measureRepository.delete(1);
            Measure measure = new Measure(1,"Высота");
            measureRepository.insert(measure);
            measure.setName("Глубина");
            measureRepository.update(measure);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
        }

        UnitmeasureRepository unitmeasureRepository =
                CapitalApplication.getApplicationContext().getBean(UnitmeasureRepository.class);
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setId(-1);
        unitmeasureRepository.delete(unitmeasure);

    }


}
