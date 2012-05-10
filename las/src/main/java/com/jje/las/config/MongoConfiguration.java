package com.jje.las.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MongoConfiguration {

    @Value("${mongo.host}")
    private String host;
    @Value("${mongo.port}")
    private int port;
    @Value("${las.schema.prefix}")
    private String schema;

    @Value("${las.config.table}")
    private String configTable;

    public String getDataTable(String priority) {
        if(!StringUtils.hasLength(priority)){
            priority = "other";
        }
        return "t_" + priority.toLowerCase();
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "mongo configuration with host:" + host + " port : " + port + " schema : " + schema;
    }

}
