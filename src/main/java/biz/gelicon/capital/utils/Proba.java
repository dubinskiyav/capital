package biz.gelicon.capital.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Аннотация @Proba применима только к методам
@Retention(RetentionPolicy.RUNTIME) // жизненный цикл создаваемой аннотации - в процессе исполнения программы
public @interface Proba {
    int id();
    String type() default "жопа";
}
