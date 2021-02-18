package biz.gelicon.capital.validators;

import biz.gelicon.capital.model.Unitmeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Валидоатор для объекта unitmeasure
 * */
@Component
public class UnitmeasureValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    // Проверка на совпадение класса
    @Override
    public boolean supports(Class<?> aClass) {
        return Unitmeasure.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Unitmeasure unitmeasure = (Unitmeasure) target;
        // Дополнительные ручные проверки
        if (unitmeasure.getUnitmeasureName() != null && unitmeasure.getUnitmeasureName().equalsIgnoreCase("наименование")) {
            errors.rejectValue("name", "",
                    "Наименование не должны быть равно значению '" + unitmeasure.getUnitmeasureName() + "'");
        }

        if (true) {return;} // Так как стандартный валидлатор вызываем в контроллере почему то
        // вызов стандартного валидатора
        Set<ConstraintViolation<Unitmeasure>> validates = validator.validate(unitmeasure);
        // Цикл по коллекции ошибок
        for (ConstraintViolation<Unitmeasure> constraintViolation : validates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            // Пуляем ошибку
            errors.rejectValue(propertyPath, "", message);
        }
    }
}
