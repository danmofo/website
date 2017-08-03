package com.dmoffat.website.view.pagination;

import java.util.Collections;
import java.util.List;

/**
 * Used for displaying partial result sets and pagination.
 *
 * This interface is returned from service methods that have pageable results. Once acquired, you can get PageRequest
 * objects for other pages which can then be directly passed to the service methods to return that page of results.
 *
 * The actual fetching is done in the service layer, this is purely for providing information to the view so that it
 * can display pagination.
 *
 * Example:
 *
 * // First page of results
 * PageRequest request = new PageRequestImpl(1);
 * Page<Post> posts = service.findAllPosts(request);
 *
 * // Next page of results
 * Page<Post> morePosts = service.findAllPosts(posts.nextPage());
 *
 * @author danielmoffat
 */
public interface Page<T> extends Iterable<T> {

    // Represents a page with no results. Any queries that return no results will return this versus null.
    static <T> Page<T> emptyPage() {
        return new PageImpl<>(Collections.emptyList(), PageRequest.firstPage(), 0L);
    }

    List<T> getResults();
    Long getTotalResults();
    PageRequest nextPage();
    PageRequest prevPage();
    PageRequest lastPage();
    PageRequest firstPage();
    boolean hasNextPage();
    boolean hasPrevPage();
    PageRequest getPageRequest();
}
