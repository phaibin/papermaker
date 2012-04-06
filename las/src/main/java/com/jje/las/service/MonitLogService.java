package com.jje.las.service;

import com.jje.las.action.admin.MonitFile;
import com.jje.las.config.LasConfiguration;
import com.jje.las.handler.MongoHandler;
import com.jje.las.util.Convert;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitLogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LasConfiguration conf;
    @Autowired
    MongoHandler handler;

    public List<MonitFile> list() {
        List<MonitFile> list = new ArrayList<MonitFile>();
        DBCursor cursor = getMonitorCollection().find();
        while (cursor.hasNext()) {
            BasicDBObject b = (BasicDBObject) cursor.next();

            MonitFile file = null;
            try {
                file = (MonitFile) Convert.parseObject(b, MonitFile.class);
                list.add(file);
            } catch (Exception ex) {
                logger.error("error in list monit file.", ex);
            }
        }
        return list;
    }

    public void delete(String id) {
        BasicDBObject deleteObject = new BasicDBObject();
        deleteObject.put("_id", new ObjectId(id));
        getMonitorCollection().remove(deleteObject);
    }

    public void save(MonitFile log) {
        getMonitorCollection().insert(Convert.parseDBObject(log));
    }

    private DBCollection getMonitorCollection() {
        DB db = handler.getConnection().getDB(conf.getSchema());
        DBCollection conn = db.getCollection(conf.getConfigTable());
        return conn;
    }
}
