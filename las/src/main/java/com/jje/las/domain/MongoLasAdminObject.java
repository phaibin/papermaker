package com.jje.las.domain;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class MongoLasAdminObject {
    public static DBObject get(MonitFile m) {
        BasicDBObjectBuilder base = BasicDBObjectBuilder.start().add("_id", m.getId());
        base.add("fileName", m.getFileName()).add("path", m.getPath()).add("type", m.getType());
        return base.get();
    }

    public static MonitFile get(DBObject dbo) {
        MonitFile m = new MonitFile();
        m.setId(getId(dbo));
        m.setFileName(getString(dbo, "fileName"));
        m.setPath(getString(dbo, "path"));
        m.setType(getString(dbo, "type"));
        return m;
    }

    private static String getId(DBObject dbo) {
        return (String) dbo.get("_id");
    }

    private static String getString(DBObject dbo, String prop) {
        return (String) dbo.get(prop);
    }


}

