package com.dmoffat.website.view.pagination;

/**
 * Represents a request for a Page<T> object
 *
 * Each request should contain the page number and the row count at a minimum.
 *
 *
 * @author danielmoffat
 */
public interface PageRequest {
    Integer getPage();
    Integer getRows();
    Sort getSort();
    Integer getStartCount();

    // Returns a PageRequest for the first page
    static PageRequest firstPage() {
        return new PageRequestImpl(1);
    }
}
