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

    private TransactionTemplate transactionTemplate;


    public void test1() {
        // Тесты
        transactionTemplate = new TransactionTemplate(transactionManager);
        Integer id = transactionTemplate.execute(status -> {
            measureRepository.delete(1);
            Measure measure = new Measure(1,"Длина");
            measureRepository.insert(measure);
            return measure.getId();
        });

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            Measure measure = new Measure(1,"Ширина");
            measureRepository.insert(measure);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }

        status = transactionManager.getTransaction(definition);
        try {
            measureRepository.delete(1);
            Measure measure = new Measure(1,"Высота");
            measureRepository.insert(measure);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }

        UnitmeasureRepository unitmeasureRepository =
                CapitalApplication.getApplicationContext().getBean(UnitmeasureRepository.class);
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setId(-1);
        unitmeasureRepository.delete(unitmeasure);

    }


}
