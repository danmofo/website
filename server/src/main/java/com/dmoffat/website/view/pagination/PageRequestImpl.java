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
    private Sort sort       = Sort.defaultSort();

    public PageRequestImpl(Integer page) {
        this.page = page;
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

    private PageRequestImpl(Builder builder) {
        page = builder.page;
        rows = builder.rows;
        sort = builder.sort;
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

    /**
     * A builder that allows you to build A PageRequest without having to worry if the arguments in the constructor
     * are null or not.
     *
     *
     * If the arguments are null, it returns null
     */
    public static final class Builder {
        private Integer page;
        private Integer rows;
        private Sort sort;

        public Builder() {
            this.page = 1;
            this.rows = 10;
            this.sort = Sort.defaultSort();
        }

        public Builder page(Integer val) {
            if(val == null) {
                return this;
            }

            page = val;
            return this;
        }

        public Builder rows(Integer val) {
            if(val == null) {
                return this;
            }

            rows = val;
            return this;
        }

        public Builder sort(Sort val) {
            if(val == null) {
                return this;
            }

            sort = val;
            return this;
        }

        public PageRequestImpl build() {
            return new PageRequestImpl(this);
        }
    }
}
