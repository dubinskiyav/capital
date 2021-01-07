package biz.gelicon.capital.utils;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.MeasureunitRepository;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import biz.gelicon.capital.repository.UnitmeasurerecalcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class Test01 {

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
        System.out.println(measureRepository.count());
        // Каждая операция - в своей транзакции, так как TableRepository аннотирован как
        // @Transactional(propagation = Propagation.REQUIRED)
        measureunitRepository.deleteAll();
        measureRepository.deleteAll();
        unitmeasurerecalcRepository.deleteAll();
        unitmeasureRepository.deleteAll();
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
