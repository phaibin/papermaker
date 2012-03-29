package com.jje.las.service;

import com.jje.las.action.admin.MonitFile;
import com.jje.las.util.Convert;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitLogService {

    private static Logger logger = Logger.getLogger(MonitLogService.class.getName());
    
    @Autowired
    MongoHandler handler;

    public List<MonitFile> list() {
        List<MonitFile> list = new ArrayList<MonitFile>();
        DBCursor cursor = getMonitorCollection().find();
        while (cursor.hasNext()) {
            BasicDBObject b = (BasicDBObject) cursor.next();
            
            MonitFile file =null;
            try
            {
               file =(MonitFile)Convert.parseObject(b, MonitFile.class);
               list.add(file);
            }catch(Exception ex)
            {
            	logger.log(Level.FINE, ex.getMessage());
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
        DB db = handler.getConnection().getDB("LogDB");
        DBCollection conn = db.getCollection("jje_log_monit");
        return conn;
    }
}
