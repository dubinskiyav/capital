package biz.gelicon.capital.validators;

import biz.gelicon.capital.model.Edizm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

// Валидоатор для объекта edizm
@Component
public class EdizmValidator implements Validator {

    @Autowired
    private javax.validation.Validator validator;

    @Override
    public boolean supports(Class<?> aClass) {
        return Edizm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Edizm edizm = (Edizm) target;
        // вызов стандартного валидатора
        // Но есди этого не сделать - стандартная валидация не вызовется
        // Сформируем коллекцию из ошибок, если они есть
        Set<ConstraintViolation<Edizm>> validates = validator.validate(edizm);
        // Цикл по коллекции ошибок
        for (ConstraintViolation<Edizm> constraintViolation : validates) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            // Пуляем ошибку
            errors.rejectValue(propertyPath, "", message);
        }
        // Дополнительные ручные проверки
        if(edizm.getCode().equalsIgnoreCase("код")) {
            errors.rejectValue("code", "",
                    "Поле 'Код' не может иметь значение '" + edizm.getCode() + "'");
        }
        if (edizm.getName() != null && edizm.getName().equalsIgnoreCase("наименование")) {
            errors.rejectValue("name", "",
                    "Наименование не должны быть равно значению '" + edizm.getName() + "'");
        }
    }
}
