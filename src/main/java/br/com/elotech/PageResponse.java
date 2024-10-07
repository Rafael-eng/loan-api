package br.com.elotech;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public class PageResponse<T>  implements Serializable {
    private Page<T> data;
    private int totalPages;
    private long totalElements;

    public PageResponse(Page<T> data, int totalPages, long totalElements) {
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public PageResponse() {

    }
    public Page<T> getData() {
        return data;
    }

    public void setData(Page<T> data) {
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
