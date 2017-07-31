package com.dmoffat.website.view.pagination;

/**
 * @author danielmoffat
 */
public interface PageRequest {
    Integer getPage();
    Integer getStart();
    Integer getRows();

    static PageRequest firstPage() {
        return new PageRequestImpl(1);
    }
}
