package biz.gelicon.capital.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Описание текущей страницы запроса указывается, какую страницу надо вернуть и какой размер
 * страницы в строках и какая сортировка.
 * Нумерация страниц начинается с нуля
 */
@Schema(description = "Параметры выборки данных в таблицу")
public class GridDataOption {

    public static final int DEFAULT_PAGE_SIZE = 25; // Рзамер страницы в строках по умолчанию

    @Schema(description = "Текущая страница, нумерация с 0")
    private int pageNumber;

    @Schema(description = "Размер страницы в строках")
    private int pageSize; //

    @Schema(description = "Установленные сортировки")
    private List<OrderBy> sort; //

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

    /**
     * Строит и возвращает PageRequest из pageNumber, pageSize и List<OrderBy> sort
     * @return PageRequest
     */
    public PageRequest buildPageRequest(){
        List<Sort.Order> orders = new ArrayList<>();
        if (sort != null && sort.size()>0) {
            orders = sort.stream()
                    .map(o -> new Sort.Order(Sort.Direction.values()[o.direction],o.fieldName))
                    .collect(Collectors.toList());
        }
        return PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(orders));
    }

    @Override
    public String toString() {
        return "GridDataOption {page=" + this.pageNumber
                + ", size=" + this.pageSize
                + ", sort=" + this.sort.stream()
                .map(OrderBy::toString)
                .collect(Collectors.joining(",", "{", "}")) + "}"; // todo - сделать
    }

    /**
     * Вспомогательный класс для сортровки Имя поля - как в мобели, а не как в базе
     */
    @Schema(description = "Сортировка по полю")
    public static class OrderBy {

        @Schema(description = "Имя поля для сортировки", example = "measure_name или measureName")
        private String fieldName;
        @Schema(description = "Направление сортировки", example = "0 - по возрастанию, 1 - по убыванию")
        private int direction;

        public OrderBy() {
        }

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

        @Override
        public String toString() {
            return fieldName + " " + direction;
        }
    }

}
