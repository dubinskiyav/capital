package biz.gelicon.capital.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static Date datetimeToDate(Date d) {
        return d == null ? null : strToDate(dateToStr(d));
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
        if (pageable == null) {return "";} // Чтобы не заморачиваться там где вызываем
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
            // Чтобы не заморачиваться там где вызываем
            return "";
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
