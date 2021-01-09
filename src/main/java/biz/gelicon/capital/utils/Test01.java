package biz.gelicon.capital.utils;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Measureunit;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.MeasureunitRepository;
import biz.gelicon.capital.repository.TableRepository;
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
        Measureunit measureunit = measureunitRepository.findById(2);
        Integer measureCount = measureRepository.count();
        List<Measureunit> measureunitList = measureunitRepository.findAll();
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
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
        }

        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            measureRepository.delete(1);
            Measure measure = new Measure(1,"Высота");
            measureRepository.insert(measure);
            measure.setName("Глубина");
            measureRepository.update(measure);
            measure = new Measure(2,"Высота");
            measureRepository.insert(measure);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
            transactionManager.rollback(transactionStatus);
        }

        //UnitmeasureRepository unitmeasureRepository =
        //        CapitalApplication.getApplicationContext().getBean(UnitmeasureRepository.class);
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setId(1);
        unitmeasure.setName("Метр");
        unitmeasure.setShortName("м.");
        unitmeasureRepository.insert(unitmeasure);
        unitmeasure.setId(null);
        unitmeasure.setName("Хуиля");
        unitmeasure.setShortName("миля");
        unitmeasureRepository.insertOrUpdate(unitmeasure);
        unitmeasure.setName("Миля");
        unitmeasure.setShortName("миля");
        unitmeasureRepository.insertOrUpdate(unitmeasure);
        unitmeasure.setShortName("мил.");
        unitmeasureRepository.set(unitmeasure);
        unitmeasure.setId(2);
        unitmeasure.setName("Литр");
        unitmeasure.setShortName("л");
        unitmeasureRepository.set(unitmeasure);

        measureunit = new Measureunit();
        measureunit.setId(1);
        measureunit.setMeasureId(1);
        measureunit.setUnitmeasureId(1);
        measureunit.setPriority(0);
        measureunitRepository.insert(measureunit);
        measureunit = new Measureunit(2,2,1,0);
        measureunitRepository.insert(measureunit);

        System.out.println("Tests ended");

    }


}
