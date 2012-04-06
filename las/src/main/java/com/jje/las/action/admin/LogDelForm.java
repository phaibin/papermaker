package com.jje.las.action.admin;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LogDelForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dt;
    private String level;
    private String module;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
