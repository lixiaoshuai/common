package com.common.dto.page;

/**
 * Created by lixiaoshuai on 2017/9/28.
 *
 * @mail sxlshuai@foxmail.com
 */
public class PageParameter {
    private int totalCount;
    private int pageSize = 10;
    private int totalPage;
    private int currentPage;

    public PageParameter() {
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
