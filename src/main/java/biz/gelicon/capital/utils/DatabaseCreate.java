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
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Чистит и загружает данные в базу
 */
@Service
public class DatabaseCreate {

    static Logger logger = LoggerFactory.getLogger(DatabaseCreate.class);

    @Autowired
    private PlatformTransactionManager transactionManager;
    DefaultTransactionDefinition defaultTransactionDefinition;
    TransactionStatus transactionStatus;

    // todo переделать. Сделать упорядоченный массив и вызывать deleteAll
    // хотя может и не надо
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

    // Чистит все таблицы базы данных
    public void clear() {
        // Каждая операция - в своей транзакции, так как TableRepository аннотирован как
        // @Transactional(propagation = Propagation.REQUIRED)
        defaultTransactionDefinition = new DefaultTransactionDefinition();
        // Чистим
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            materialunitmeasureRepository.deleteAll();
            serviceRepository.deleteAll();
            materialRepository.deleteAll();
            materiallevelRepository.deleteAll();
            unitmeasurerecalcRepository.deleteAll();
            measureunitRepository.deleteAll();
            unitmeasureRepository.deleteAll();
            measureRepository.deleteAll();
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
        }
    }

    // Первоначальная зугрузка базы данных
    public void load() {
        defaultTransactionDefinition = new DefaultTransactionDefinition();
        transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            measureRepository.load();
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            String errText = String.format("Error. Transaction will be rolled back");
            logger.error(errText, e);
            transactionManager.rollback(transactionStatus);
        }

    }

}
