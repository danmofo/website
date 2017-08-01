package com.dmoffat.website.view.pagination;

import com.dmoffat.website.model.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author danielmoffat
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PageRequestImpl implements PageRequest {
    private Integer page    = 1;
    private Integer rows    = 10;
    private Sort sort;

    public PageRequestImpl(Integer page) {
        this.page = page;
        this.sort = Sort.idDesc();
    }

    public PageRequestImpl(Integer page, Sort sort) {
        this.page = page;
        this.sort = sort;
    }

    public PageRequestImpl(Integer page, Sort sort, Integer rows) {
        this.page = page;
        this.rows = rows;
        this.sort = sort;
    }

    @Override
    @JsonIgnore
    public Integer getStartCount() {
        return (page - 1) * 10;
    }

    @Override
    @JsonView(Views.Summary.class)
    public Sort getSort() {
        return sort;
    }

    @Override
    @JsonView(Views.Summary.class)
    public Integer getPage() {
        return page;
    }

    @Override
    @JsonView(Views.Summary.class)
    public Integer getRows() {
        return rows;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageRequestImpl{");
        sb.append("page=").append(page);
        sb.append(", rows=").append(rows);
        sb.append(", sort=").append(sort);
        sb.append('}');
        return sb.toString();
    }
}
