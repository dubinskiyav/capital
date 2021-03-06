package biz.gelicon.capital.validators;

import biz.gelicon.capital.model.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Валидоатор для объекта measure
 * */
@Component
public class MeasureValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return Measure.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measure measure = (Measure) target;
        // Дополнительные ручные проверки
        if (measure.getMeasureName() != null && measure.getMeasureName().equalsIgnoreCase("наименование")) {
            // Добавляем к полю id так как потом из него вытаскиваем
            errors.rejectValue("id", "",
                    "Наименование не должны быть равно значению равному '" + measure.getMeasureName() + "'");
        }
        if (measure.getId() != null && measure.getId() == -1) {
            errors.rejectValue("id", "",
                    "Эту запись менять запрещено!");
        }

        if (true) {return;} // Так как стандартный валидлатор вызываем в контроллере почему то
        // вызов стандартного валидатора
        Set<ConstraintViolation<Measure>> validates = validator.validate(measure);
        // Цикл по коллекции ошибок
        for (ConstraintViolation<Measure> constraintViolation : validates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            // Пуляем ошибку
            errors.rejectValue(propertyPath, "", message);
        }
    }
}
