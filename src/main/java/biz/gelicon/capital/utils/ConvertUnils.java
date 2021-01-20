package biz.gelicon.capital.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Общие методы конвертирования данных
 */
public class ConvertUnils {

    private static final Logger logger = LoggerFactory.getLogger(ConvertUnils.class);
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            "dd.MM.yyyy HH:mm:ss");

    /**
     * Возвращает true для пустой или пробельной строки и null
     *
     * @param s строка
     * @return результат
     */
    public static boolean empty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Конвертирует дату в строку формата "31.11.2020"
     *
     * @param d дата
     * @return результат
     */
    public static String dateToStr(Date d) {
        return d == null ? null : dateFormat.format(d);
    }

    /**
     * Конвертирует строку формата "31.11.2020" в дату
     *
     * @param s строка
     * @return результат
     */
    public static Date strToDate(String s) {
        if (empty(s)) {return null;}
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            String errText = String.format("Converting %s to date filed", s);
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
    }

    /**
     * округляет дату-время до даты
     *
     * @param d датавремя
     * @return результат
     */
    // округляет дату-время до даты
    public static Date datetimeToDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getMinDate() {
        try {
            return dateFormat.parse("01.01.1900");
        } catch (ParseException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Date getMaxDate() {
        try {
            return dateFormat.parse("31.12.2099");
        } catch (ParseException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> Stream<T> getStreamFromIterator(Iterator<T> iterator) {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
        return StreamSupport.stream(spliterator, false);
    }

    /**
     * Составляет секцию ORDER BY из Pageable
     *
     * @param pageable
     * @return
     */
    public static String buildOrderByFromPegable(Pageable pageable) {
        if (pageable == null) {return null;}
        if (pageable.getSort().isEmpty()) {return null;}
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        pageable.getSort().iterator(),
                        Spliterator.ORDERED),
                false)
                .map(o -> o.getProperty() + " " + o.getDirection().toString())
                .collect(Collectors.joining(",", "ORDER BY ", ""));
    }

    /**
     * Составляет секцию LIMIT OFFSET из Pageable
     *
     * @param pageable
     * @return
     */
    public static String buildLimitFromPegable(Pageable pageable) {
        if (pageable == null || pageable.isUnpaged() || pageable.getPageSize() < 0) {
            return null;
        }
        // Выводим по страницам
        // Ограницим число строк размером страницы
        String limitPart = "LIMIT " + pageable.getPageSize();
        if (pageable.getPageNumber() > 0) { // Это не первая страница
            // Сместимсся на столько записей, сколько их в предудущих страницах
            limitPart = limitPart + " OFFSET " + pageable.getPageNumber() * pageable.getPageSize();
        }
        return limitPart;
    }


}
