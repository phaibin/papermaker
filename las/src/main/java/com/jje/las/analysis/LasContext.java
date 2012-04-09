package com.jje.las.analysis;

import java.util.HashMap;

import org.apache.commons.chain.Context;

import com.jje.las.action.log.Log;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class LasContext extends HashMap implements Context {
    
    public void removeNewLog(){
        this.remove("newLog");
    }
    
    public String getCurrentLine() {
        return (String)this.get("currentLine");
    }
    public LasContext setCurrentLine(String currentLine) {
        this.put("currentLine", currentLine);
        return this;
    }
    public Log getLastLog() {
        return (Log)this.get("lastLog");
    }
    public LasContext setLastLog(Log lastLog) {
        this.put("lastLog", lastLog);
        return this;
    }
    public Log getNewLog() {
        return (Log)this.get("newLog");
    }
    public LasContext setNewLog(Log newLog) {
        this.put("newLog", newLog);
        return this;
    }
    public String getFileFrom() {
        return (String)this.get("fileFrom");
    }
    public LasContext setFileFrom(String fileFrom) {
        this.put("fileFrom", fileFrom);
        return this;
    }
    
    
    

}
