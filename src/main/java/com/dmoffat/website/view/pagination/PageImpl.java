package com.dmoffat.website.view.pagination;

import java.util.List;

/**
 * @author danielmoffat
 */
public class PageImpl<T> implements Page<T> {
    private List<T> items;
    private PageRequest pageRequest;
    private Long resultCount;

    public PageImpl(List<T> items, PageRequest pageRequest, Long resultCount) {
        this.items = items;
        this.pageRequest = pageRequest;
        this.resultCount = resultCount;
    }

    public PageImpl(List<T> items, Long resultCount) {
        this.items = items;
        this.pageRequest = PageRequest.firstPage();
        this.resultCount = resultCount;
    }

    @Override
    public PageRequest nextPage() {
        return null;
    }

    @Override
    public PageRequest prevPage() {
        return null;
    }

    @Override
    public PageRequest lastPage() {
        return null;
    }

    @Override
    public PageRequest firstPage() {
        return PageRequest.firstPage();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageImpl{");
        sb.append("items=").append(items);
        sb.append(", pageRequest=").append(pageRequest);
        sb.append('}');
        return sb.toString();
    }
}
