package com.jje.las.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jje.las.config.LasConfiguration;
import com.jje.las.domain.MongoLasAdminObject;
import com.jje.las.domain.MonitFile;
import com.jje.las.handler.MongoHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Service
public class AdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LasConfiguration conf;
    @Autowired
    MongoHandler handler;

    public List<MonitFile> listMonitorFiles() {
        List<MonitFile> list = new ArrayList<MonitFile>();
        DBCursor cursor = getMonitorCollection().find();
        try {
            while (cursor.hasNext()) {
                try {
                    list.add(MongoLasAdminObject.get(cursor.next()));
                } catch (Exception ex) {
                    logger.error("error in list monit file.", ex);
                }
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void removeFileMonitor(String id) {
        BasicDBObject deleteObject = new BasicDBObject();
        deleteObject.put("_id", new ObjectId(id));
        getMonitorCollection().remove(deleteObject);
    }

    public void addFileToMonitor(MonitFile log) {
        getMonitorCollection().insert(MongoLasAdminObject.get(log));
    }

    private DBCollection getMonitorCollection() {
        DB db = handler.getConnection().getDB(conf.getSchema()+"_config");
        DBCollection conn = db.getCollection(conf.getConfigTable());
        return conn;
    }
}