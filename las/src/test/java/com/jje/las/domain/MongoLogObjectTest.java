package com.jje.las.domain;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import com.mongodb.DBObject;

public class MongoLogObjectTest {

    @Test
    public void getDBObjectFromLog(){
        Log log = mockLog();
        DBObject dbObject = MongoLogObject.get(log);
        Log logVerify = MongoLogObject.get(dbObject);
        Assert.assertEquals(log.getIp(), logVerify.getIp());
    }

    @Test
    public void getDBObjectFromLog2(){
        Log log = mockLogWithNullValue();
        DBObject dbObject = MongoLogObject.get(log);
        Log logVerify = MongoLogObject.get(dbObject);
        Assert.assertEquals(log.getIp(), logVerify.getIp());
        Assert.assertNull(logVerify.getModule());
    }

    private Log mockLog() {
        Log l = new Log();
        l.setClassName(this.getClass().getName());
        l.setDetail("detail abc");
        l.setIp("192.168.2.111");
        l.setLogFrom("abc.log");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 1, 1, 0, 0, 0);
        l.setLogTime(calendar.getTime());
        l.setMessage("message abc");
        l.setMethodName("method");
        l.setModule("abc");
        l.setPriority("ERROR");
        l.setRaw("raw 192. mesa");
        l.setResult("200");
        l.setThread("thread x1");
        return l;
    }
    
    private Log mockLogWithNullValue() {
        Log l = new Log();
        l.setClassName(this.getClass().getName());
        l.setDetail("detail abc");
        l.setIp("192.168.2.111");
        l.setLogFrom("abc.log");
        l.setPriority("ERROR");
        l.setRaw("raw 192. mesa");
        l.setResult("200");
        l.setThread("thread x1");
        return l;
    }

}
