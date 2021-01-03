package biz.gelicon.capital.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // Аннотация @Proba применима к Class, interface (including annotation type), or enum declaration
@Retention(RetentionPolicy.RUNTIME) // жизненный цикл создаваемой аннотации - в процессе исполнения программы
public @interface Captable {
    String tableName();
    String tableDescription();
    String pkName() default "id";
}
