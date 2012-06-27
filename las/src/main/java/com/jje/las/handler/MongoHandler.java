package com.jje.las.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.las.config.MongoConfiguration;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoConfiguration conf;

    private Mongo conn;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public Mongo getConnection() {
        return conn;
    }

    public DBCollection getCollection(String schema, String table) {
        DB db = getConnection().getDB(schema);
        DBCollection conn = db.getCollection(table);
        return conn;
    }
    
    public DBCollection getMonitorCollection() {
        return getConnection().getDB(conf.getSchema()+"_config").getCollection(conf.getConfigTable());
    }

    public DBCollection getDayPriorityDBTable(Date d, String priority) {
        DBCollection db=getCollection(conf.getSchema() + dateFormat.format(d), conf.getDataTable(priority));
        if(logger.isDebugEnabled()){
            logger.debug("get day priority db table:"+db.getFullName());
        }
        BasicDBObjectBuilder idx = BasicDBObjectBuilder.start();
        DBObject idxObj = idx.add("logTime", -1).add("priority", 1).add("module", 1).get();
        db.ensureIndex(idxObj);
        return db;
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
        logger.info("refresh mongo handler.");
        destory();
        init();
    }
}
