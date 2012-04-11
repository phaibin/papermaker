package com.jje.las.action.log;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LogQueryForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begin;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;
    private String priority;
    private String logFrom;
    private String module;

    private int page = 1;
    private int pageSize = 50;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getLogFrom() {
        return logFrom;
    }

    public void setLogFrom(String logFrom) {
        this.logFrom = logFrom;
    }

}
