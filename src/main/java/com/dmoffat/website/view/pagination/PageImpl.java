package com.dmoffat.website.view.pagination;

import com.dmoffat.website.model.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author danielmoffat
 */
public class PageImpl<T> implements Page<T> {

    @JsonProperty("params")
    private PageRequest pageRequest;

    @JsonView(Views.Summary.class)
    private List<T> items;

    private Long resultCount;

    private int currentPage;
    private int totalPages;

    public PageImpl(List<T> items, PageRequest pageRequest, Long resultCount) {
        this.items = items;
        this.pageRequest = pageRequest;
        this.resultCount = resultCount;
        this.totalPages = calculateTotalPages();
        this.currentPage = pageRequest.getPage();
    }

    private int calculateTotalPages() {
        double result = Math.ceil((double)resultCount / (double)pageRequest.getRows());
        return (int)result;
    }

    @JsonView(Views.Summary.class)
    @Override
    public boolean hasNextPage() {
        return currentPage + 1 <= totalPages;
    }

    @JsonView(Views.Summary.class)
    @Override
    public boolean hasPrevPage() {
        return currentPage - 1 >= 1;
    }

    @JsonView(Views.Summary.class)
    @Override
    public PageRequest getPageRequest() {
        return pageRequest;
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }

    @Override
    public List<T> getResults() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public Long getTotalResults() {
        return resultCount;
    }

    @Override
    public PageRequest nextPage() {
        return hasNextPage() ? new PageRequestImpl(currentPage + 1) : null;
    }

    @Override
    public PageRequest prevPage() {
        return hasPrevPage() ? new PageRequestImpl(currentPage - 1) : null;
    }

    @Override
    public PageRequest lastPage() {
        return new PageRequestImpl(totalPages);
    }

    @Override
    public PageRequest firstPage() {
        return PageRequest.firstPage();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageImpl{");
        sb.append("items=").append(items.size());
        sb.append(", pageRequest=").append(pageRequest);
        sb.append(", totalPages=").append(totalPages);
        sb.append('}');
        return sb.toString();
    }
}
