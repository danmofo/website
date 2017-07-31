package com.dmoffat.website.view.pagination;

/**
 * Represents a request for a Page<T> object
 *
 * @author danielmoffat
 */
public interface PageRequest {
    Integer getPage();
    Integer getRows();

    static PageRequest firstPage() {
        return new PageRequestImpl(1);
    }
}
