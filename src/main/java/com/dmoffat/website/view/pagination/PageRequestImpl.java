package com.dmoffat.website.view.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author danielmoffat
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    public Sort getSort() {
        return sort;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
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
