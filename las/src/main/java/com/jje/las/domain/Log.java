package com.jje.las.domain;

import java.util.Date;

public class Log {

    private String id;
    private Date logTime;
    private String priority;
    private String thread;
    private String module;
    private String className;
    private String methodName;
    private String message;
    private String detail;
    private String logFrom;
    private String raw;
    private String ip;
    private String result;
    

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getLogFrom() {
        return logFrom;
    }

    public void setLogFrom(String logFrom) {
        this.logFrom = logFrom;
    }

    public Log() {
    }

    public Log(Date d, String priority, String className) {
        setLogTime(d);
        setPriority(priority);
        setClassName(className);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public void appendDetail(String line) {
        if (detail == null)
            detail = "";
        StringBuffer buf = new StringBuffer(detail);
        buf.append(line);
        setDetail(buf.toString());
    }
    
}
