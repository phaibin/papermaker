package com.jje.las.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.las.config.MongoConfiguration;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoConfiguration conf;

    private Mongo conn;

    public Mongo getConnection() {
        return conn;
    }

    public DBCollection getCollection(String schema, String table) {
        DB db = getConnection().getDB(schema);
        DBCollection conn = db.getCollection(table);
        return conn;
    }
    
    public void init() {
        logger.info("init mongo handler." + conf);
        try {
            conn = new Mongo(conf.getHost(), conf.getPort());
        } catch (Exception e) {
            logger.error("error init mongo handler.", e);
        }
    }

    public void destory() {
        logger.info("destory mongo handler.");
        conn.close();
    }

    public void refresh() {
        destory();
        init();
    }
}
