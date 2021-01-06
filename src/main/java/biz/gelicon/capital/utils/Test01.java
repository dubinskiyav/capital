package biz.gelicon.capital.utils;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public class Test01 {

    @Autowired
    MeasureRepository measureRepository;

    public void test1() {
        // Тесты
        // Старт транзакции
        Measure measure = new Measure(1,"Длина");
        measureRepository.insert(measure);
        // Коммит транзакции

        //measureRepository.delete(measure);

        measureRepository.delete(1);



        UnitmeasureRepository unitmeasureRepository =
                CapitalApplication.getApplicationContext().getBean(UnitmeasureRepository.class);
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setId(-1);
        unitmeasureRepository.delete(unitmeasure);

    }


}
