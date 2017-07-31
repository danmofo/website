package com.dmoffat.website.view.pagination;

import java.util.Collections;

/**
 * Represents a page of items of type T. Used for displaying partial result sets and pagination.
 *
 * @author danielmoffat
 */
public interface Page<T> {

    // Represents a page with no results. Any queries that return no results will return this versus null.
    static Page empty() {
        return new PageImpl(Collections.emptyList(), 0L);
    }

    PageRequest nextPage();
    PageRequest prevPage();
    PageRequest lastPage();
    PageRequest firstPage();
}
