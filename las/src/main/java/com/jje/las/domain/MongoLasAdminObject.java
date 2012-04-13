package com.jje.las.domain;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class MongoLasAdminObject {
    public static DBObject get(MonitFile m) {
        BasicDBObjectBuilder base = BasicDBObjectBuilder.start();
        base.add("fileName", m.getFileName()).add("path", m.getPath()).add("type", m.getType());
        return base.get();
    }

    public static MonitFile get(DBObject dbo) {
        MonitFile m = new MonitFile();
        CastValue cv = new CastValue();
        m.setId(cv.getId(dbo));
        m.setFileName(cv.getString(dbo, "fileName"));
        m.setPath(cv.getString(dbo, "path"));
        m.setType(cv.getString(dbo, "type"));
        return m;
    }

}
