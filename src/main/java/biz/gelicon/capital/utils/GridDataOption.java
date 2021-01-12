package biz.gelicon.capital.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Параметры запроса
 */
public class GridDataOption {

    /**
     * Рзамер страницы в строках по умолчанию
     */
    public static final int DEFAULT_PAGE_SIZE = 25;

    /**
     * Сортировка данных запроса
     */
    private Sort sort;

    /**
     * Описание текущей страницы запроса указывается, какую страницу надо вернуть и какой размер
     * страницы в строках. нумерация страниц начинается с нуля
     */
    private PageRequest pageRequest;

    public GridDataOption() {
        this.sort = Sort.by(Sort.DEFAULT_DIRECTION);
        this.pageRequest = PageRequest.of(0, GridDataOption.DEFAULT_PAGE_SIZE, this.sort);
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getPageSize() {
        return this.pageRequest.getPageSize();
    }

    public void setPageSize(int pageSize) {
        this.pageRequest = PageRequest.of(getPageNumber(), pageSize, this.sort);
    }

    public int getPageNumber() {
        return pageRequest.getPageNumber();
    }

    public void setPageNumber(int pageNumber) {
        this.pageRequest = PageRequest.of(pageNumber, getPageSize(), getSort());
    }
}
