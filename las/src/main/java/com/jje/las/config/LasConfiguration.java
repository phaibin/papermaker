package com.jje.las.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LasConfiguration {

    @Value("${las.schema.prefix}")
    private String schema;

    @Value("${las.error.table}")
    private String errorTable;

    @Value("${las.info.table}")
    private String infoTable;

    @Value("${las.debug.table}")
    private String debugTable;

    @Value("${las.other.table}")
    private String otherTable;

    @Value("${las.config.table}")
    private String configTable;
    
    @Value("${las.associate.interval}")
    private int interval;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getConfigTable() {
        return configTable;
    }

    public void setConfigTable(String configTable) {
        this.configTable = configTable;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getErrorTable() {
        return errorTable;
    }

    public void setErrorTable(String errorTable) {
        this.errorTable = errorTable;
    }

    public String getInfoTable() {
        return infoTable;
    }

    public void setInfoTable(String infoTable) {
        this.infoTable = infoTable;
    }

    public String getDebugTable() {
        return debugTable;
    }

    public void setDebugTable(String debugTable) {
        this.debugTable = debugTable;
    }

    public String getOtherTable() {
        return otherTable;
    }

    public void setOtherTable(String otherTable) {
        this.otherTable = otherTable;
    }

}
