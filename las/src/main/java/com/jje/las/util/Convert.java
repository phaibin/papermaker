package com.jje.las.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;

public class Convert {

    private static Logger logger = LoggerFactory.getLogger(Convert.class);

    public static BasicDBObject parseDBObject(Object o) {
        if (null == o)
            throw new NullPointerException();
        BasicDBObject b = new BasicDBObject();
        try {
            for (Field f : o.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object value = f.get(o);
                if (null != value) {
                    b.put(f.getName(), value);
                }
            }
        } catch (Exception e) {
            logger.error("error in convert dbobject.", e);
        }
        return b;
    }

    public static Object parseObject(BasicDBObject b, @SuppressWarnings("rawtypes") Class c)
            throws InstantiationException, IllegalAccessException {
        if (null == b)
            throw new NullPointerException();
        Object newO = c.newInstance();
        for (Field f : c.getDeclaredFields()) {
            f.setAccessible(true);
            String name = f.getName();
            if ("id".equals(name)) {
                f.set(newO, b.get("_id").toString());
                continue;
            }
            Object value = b.get(name);
            if (null != value) {
                f.set(newO, value);
            }
        }
        return newO;

    }

}
