package com.jje.las.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoConfiguration {

    @Value("${mongo.host}")
    private String host;
    @Value("${mongo.port}")
    private int port;

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
        return "mongo configuration with host:" + host + " port : " + port;
    }

}
