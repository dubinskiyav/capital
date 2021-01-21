package biz.gelicon.capital.utils;

import java.util.Iterator;

public class UsefulUtils {

    /**
     * Возвращает диапазон целых чисел
     *
     * @param from начальное значение (включительно)
     * @param to   конечное значение (не включается)
     * @return Iterable, содержащий числа от from до to
     */
    public static Iterable<Integer> range(
            int from,
            int to
    ) {
        return () -> new Iterator<>() {
            int cursor = from;

            public boolean hasNext() {
                return cursor <= to;
            }

            public Integer next() {
                return cursor++;
            }
        };
    }

}
