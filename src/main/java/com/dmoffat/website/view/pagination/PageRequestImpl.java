package com.dmoffat.website.view.pagination;

/**
 * @author danielmoffat
 */
public class PageRequestImpl implements PageRequest {
    private Integer page    = 1;
    private Integer rows    = 10;

    public PageRequestImpl(Integer page) {
        this.page = page;
    }

    public PageRequestImpl(Integer page, Integer rows) {
        this.page = page;
        this.rows = rows;
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
        sb.append('}');
        return sb.toString();
    }
}
