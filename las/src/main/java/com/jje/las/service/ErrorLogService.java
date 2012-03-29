package com.jje.las.service;

import com.jje.las.analysis.ErrorLog;
import com.jje.las.util.Convert;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorLogService {
    @Autowired
    MongoHandler handler;

    public void handleErrorLog(ErrorLog log) {
        DB db = handler.getConnection().getDB("LogDB");
        DBCollection c = db.getCollection("jje_log_error");
        c.insert(Convert.parseDBObject(log));
    }
}
