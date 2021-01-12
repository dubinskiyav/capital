package biz.gelicon.capital.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Описание текущей страницы запроса указывается, какую страницу надо вернуть и какой размер
 * страницы в строках. нумерация страниц начинается с нуля и какая сортировка
 */
public class GridDataOption {

    public static final int DEFAULT_PAGE_SIZE = 25; // Рзамер страницы в строках по умолчанию

    private int pageNumber; // Текущая страница, нумерация с 0

    private int pageSize; // Размер страницы в строках

    private List<OrderBy> sort; // Установленные сортировки

    public GridDataOption() {
        this.pageNumber = 0;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.sort = new ArrayList<>();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public List<OrderBy> getSort() {
        return sort;
    }

    public void setSort(List<OrderBy> sort) {
        this.sort = sort;
    }

    public void buildPageRequest(){

    }

    /*
    public List<OrderBy> getSort() {
        if (true) {
            return ConvertUnils.getStreamFromIterator(this.pageRequest.getSort().iterator())
                    .map(o -> new OrderBy(o.getProperty(), o.getDirection().ordinal()))
                    .collect(Collectors.toList());
        } else {
            List<OrderBy> orderByList = new ArrayList<>();
            this.pageRequest.getSort().iterator().forEachRemaining(o ->
                    orderByList.add(new OrderBy(o.getProperty(), o.getDirection().ordinal())));
            return orderByList;
        }
    }

    public void SetSort(List<OrderBy> orderByList) {
        List<Sort.Order> orders = new ArrayList<>();
        if (orderByList != null) {
            orders = orderByList.stream()
                    .map(o -> new Sort.Order(Sort.Direction.values()[o.direction], o.fieldName))
                    .collect(Collectors.toList());
        }
        this.pageRequest = PageRequest.of(
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                Sort.by(orders)
        );
    }

     */

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

    @Override
    public String toString() {
        return "GridDataOption {page=" + this.pageNumber
                + ", size=" + this.pageSize
                + ", sort=" + this.sort; // todo - сделать
    }


}
