package com.jje.las.domain;

import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class MongoLogObject {

    public static DBObject get(Log log) {
        BasicDBObjectBuilder access = BasicDBObjectBuilder.start().add("ip", log.getIp());
        access.add("thread", log.getThread()).add("result", log.getResult());

        BasicDBObjectBuilder detail = BasicDBObjectBuilder.start().add("raw", log.getRaw())
                .add("exception", log.getDetail());
        detail.add("message", log.getMessage()).add("method", log.getMethodName()).add("from", log.getLogFrom());

        BasicDBObjectBuilder base = BasicDBObjectBuilder.start();
        base.add("logTime", log.getLogTime()).add("priority", log.getPriority()).add("module", log.getModule());
        base.add("className", log.getClassName()).add("access", access.get()).add("detail", detail.get());
        return base.get();
    }

    public static Log get(DBObject dbo) {
        Log log = new Log();
        CastValue cv = new CastValue();
        log.setId(cv.getId(dbo));
        log.setClassName(cv.getString(dbo, "className"));
        log.setLogTime(cv.getDate(dbo, "logTime"));
        log.setModule(cv.getString(dbo, "module"));
        log.setPriority(cv.getString(dbo, "priority"));
        log.setDetail(cv.getString(dbo, "detail", "exception"));
        log.setLogFrom(cv.getString(dbo, "detail", "from"));
        log.setMessage(cv.getString(dbo, "detail", "message"));
        log.setMethodName(cv.getString(dbo, "detail", "message"));
        log.setRaw(cv.getString(dbo, "detail", "raw"));
        log.setIp(cv.getString(dbo, "access", "ip"));
        log.setResult(cv.getString(dbo, "access", "result"));
        log.setThread(cv.getString(dbo, "access", "thread"));
        return log;
    }

}

class CastValue {

    public String getId(DBObject dbo) {
        ObjectId oi = (ObjectId) dbo.get("_id");
        if (oi != null) {
            return oi.toString();
        }
        return (String) null;
    }

    public String getString(DBObject dbo, String prop) {
        return (String) dbo.get(prop);
    }

    public String getString(DBObject dbo, String inner, String prop) {
        DBObject embed = (DBObject) dbo.get(inner);
        if(embed != null)
            return (String) embed.get(prop);
        return (String)null;
    }

    public Date getDate(DBObject dbo, String prop) {
        return (Date) dbo.get(prop);
    }

}
