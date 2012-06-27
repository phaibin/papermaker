package com.jje.las.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jje.las.config.LasConfiguration;
import com.jje.las.domain.MongoLasAdminObject;
import com.jje.las.domain.MonitFile;
import com.jje.las.handler.MongoHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

@Service
public class AdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MongoHandler handler;

    public List<MonitFile> listMonitorFiles() {
        List<MonitFile> list = new ArrayList<MonitFile>();
        DBCursor cursor = handler.getMonitorCollection().find();
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
    	logger.info("remove monitor file");
        handler.getMonitorCollection().remove(new BasicDBObject("_id", id));
    }

    public void addFileToMonitor(MonitFile log) {
    	logger.info("add monitor file");
    	handler.getMonitorCollection().insert(MongoLasAdminObject.get(log));
    }

    public void save(LasConfiguration las){
        
    }
    
    public void loadTo(LasConfiguration las){
        
    }
}
