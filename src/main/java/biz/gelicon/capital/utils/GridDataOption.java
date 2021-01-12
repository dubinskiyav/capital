package biz.gelicon.capital.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Описание текущей страницы запроса указывается, какую страницу надо вернуть и какой размер
 * страницы в строках. нумерация страниц начинается с нуля и какая сортировка
 */
public class GridDataOption {

    /**
     * Рзамер страницы в строках по умолчанию
     */
    public static final int DEFAULT_PAGE_SIZE = 25;

    private PageRequest pageRequest;

    public GridDataOption() {
        this.pageRequest = PageRequest.of(
                0,
                GridDataOption.DEFAULT_PAGE_SIZE,
                Sort.by(Sort.DEFAULT_DIRECTION)
        );
    }

    public int getPageNumber() {
        return pageRequest.getPageNumber();
    }

    public void setPageNumber(int pageNumber) {
        this.pageRequest = PageRequest.of(
                pageNumber,
                pageRequest.getPageSize(),
                this.pageRequest.getSort()
        );
    }

    public int getPageSize() {
        return this.pageRequest.getPageSize();
    }

    public void setPageSize(int pageSize) {
        this.pageRequest = PageRequest.of(
                pageRequest.getPageNumber(),
                pageSize,
                this.pageRequest.getSort()
        );
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    /**
     * Из pageRequest Sort List<Sort.Order> возвращает сотрировку
     * в виде коллекции объектов OrderBy (fieldName, direction)
     * @return
     */
    public List<OrderBy> getSort() {
        if (true) {
            return ConvertUnils.getStreamFromIterator(this.pageRequest.getSort().iterator())
                    .map(o -> new OrderBy(o.getProperty(), o.getDirection().ordinal()))
                    .collect(Collectors.toList());
        } else  {
            List<OrderBy> orderByList = new ArrayList<>();
            this.pageRequest.getSort().iterator().forEachRemaining(o ->
                    orderByList.add(new OrderBy(o.getProperty(), o.getDirection().ordinal())));
            return orderByList;
        }
    }

    /**
     * Из коллекции объектов OrderBy (fieldName, direction) устанавливает значение
     * pageRequest Sort List<Sort.Order>
     * @param orderByList
     */
    public void SetSort(List<OrderBy> orderByList) {
        List<Sort.Order> orders = orderByList.stream()
                .map(o -> new Sort.Order(Sort.Direction.values()[o.direction],o.fieldName))
                .collect(Collectors.toList());
        this.pageRequest = PageRequest.of(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                Sort.by(orders)
        );

    }

    /**
     * Вспомогательный класс для сортровки Имя поля - как в мобели, а не как в базе
     */
    static class OrderBy {

        private String fieldName;
        private int direction;

        public OrderBy(String fieldName, int direction) {
            this.fieldName = fieldName;
            this.direction = direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public int getDirection() {
            return direction;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }
    }


}
