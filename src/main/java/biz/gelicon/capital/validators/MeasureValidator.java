package biz.gelicon.capital.validators;

import biz.gelicon.capital.model.Measure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

// Валидоатор для объекта measure
@Component
public class MeasureValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    @Override
    public boolean supports(Class<?> aClass) {
        return Measure.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measure measure = (Measure) target;
        // вызов стандартного валидатора
        // Но есди этого не сделать - стандартная валидация не вызовется
        // Сформируем коллекцию из ошибок, если они есть
        Set<ConstraintViolation<Measure>> validates = validator.validate(measure);
        // Цикл по коллекции ошибок
        for (ConstraintViolation<Measure> constraintViolation : validates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            // Пуляем ошибку
            errors.rejectValue(propertyPath, "", message);
        }
        // Дополнительные ручные проверки
        if (measure.getName() != null && measure.getName().equalsIgnoreCase("наименование")) {
            errors.rejectValue("name", "",
                    "Наименование не должны быть равно значению '" + measure.getName() + "'");
        }
    }
}
