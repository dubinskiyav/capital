package biz.gelicon.capital.utils;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.model.Unitmeasure;
import biz.gelicon.capital.repository.MeasureRepository;
import biz.gelicon.capital.repository.UnitmeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Test01 {

    @Autowired
    MeasureRepository measureRepository;

    public void test1() {
        // Тесты
        measureRepository.delete(1);

        Measure measure = new Measure();
        measure.setId(1);
        measureRepository.delete(measure);

        UnitmeasureRepository unitmeasureRepository =
                CapitalApplication.getApplicationContext().getBean(UnitmeasureRepository.class);
        Unitmeasure unitmeasure = new Unitmeasure();
        unitmeasure.setId(-1);
        unitmeasureRepository.delete(unitmeasure);

    }


}
