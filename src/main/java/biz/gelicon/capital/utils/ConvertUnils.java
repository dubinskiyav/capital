package biz.gelicon.capital.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Общие методы конвертирования данных
public class ConvertUnils {

    private static final Logger logger = LoggerFactory.getLogger(ConvertUnils.class);
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    // Возвращает true для пустой или пробелльной строки и null
    public static boolean empty(String s) {
        return s == null || s.trim().isEmpty();
    }

    // Дату в строку формата "31.11.2020"
    public static String dateToStr(Date d) {
        return d == null ? null : dateFormat.format(d);
    }

    // Дату из строки формата "31.11.2020"
    public static Date strToDate(String s) {
        if (empty(s)) {return null;}
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // округляет дату-время до даты
    public static Date datetimeToDate(Date d) {
        return d == null ? null : strToDate(dateToStr(d));
    }

}
