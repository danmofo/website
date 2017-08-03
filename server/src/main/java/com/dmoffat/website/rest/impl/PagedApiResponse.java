package com.dmoffat.website.rest.impl;

import com.dmoffat.website.model.Views;
import com.dmoffat.website.rest.ApiResponse;
import com.dmoffat.website.view.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * An API result that contains a partial result set, and information about the partial result set (next page, total rows, etc.)
 *
 * @author dan
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PagedApiResponse implements ApiResponse {
    @JsonView(Views.Summary.class)
    private Page<?> page;

    public PagedApiResponse(Page<?> page) {
        this.page = page;
    }
}
