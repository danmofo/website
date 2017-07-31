package com.dmoffat.website.view.pagination;

import java.util.List;

/**
 * @author danielmoffat
 */
public class PageImpl<T> implements Page<T> {
    private PageRequest pageRequest;
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

    private boolean hasNextPage() {
        return currentPage + 1 <= totalPages;
    }

    private boolean hasPrevPage() {
        return currentPage - 1 >= 1;
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
