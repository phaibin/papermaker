package com.jje.las.service;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoHandler {

    private static Logger logger = Logger.getLogger(MongoHandler.class.getName());
    @Value("${mongo.host}")
    private String mongoHost;
    @Value("${mongo.port}")
    private int mongoPort;
    private Mongo mongoConn;

    public Mongo getConnection() {
        return mongoConn;
    }

    @PostConstruct
    public void init() {
        logger.info("init mongo handler.");
        try {
            mongoConn = new Mongo(mongoHost, mongoPort);
        } catch (UnknownHostException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    public void destory() {
        System.out.println("destory mongo handler.");
        mongoConn.close();
    }
}
