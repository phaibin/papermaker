package com.jje.las.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.las.LasBaseSpringTest;
import com.jje.las.config.LasConfiguration;
import com.jje.las.config.MongoConfiguration;
import com.jje.las.domain.MonitFile;
import com.jje.las.handler.MongoHandler;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@Ignore //TODO need mongod start
public class ConfigServiceTest extends LasBaseSpringTest{

    @Autowired
    LasConfiguration conf;
    
    @Autowired
    MongoHandler testHandler;
    
    @Autowired
    MongoConfiguration mc;
    
    @Before
    public void befor(){
        DB db = testHandler.getConnection().getDB(mc.getSchema()+"_config");
        DBCollection conn = db.getCollection(mc.getConfigTable());
        conn.drop();
        conf.getScanPaths().clear();
    }
    
    @Test
    public void EmptyPath(){
        List<MonitFile> scanPaths = conf.getScanPaths();
        Assert.assertTrue(scanPaths.isEmpty());
    }
    
    @Test
    public void addScanPath(){
        MonitFile mf = new MonitFile();
        mf.setFileName("abc.log");mf.setPath("/home/abc/");mf.setType("log4j");
        conf.addScanPath(mf);
        Assert.assertEquals(1, conf.getScanPaths().size());
    }

    @Test
    public void removeScanPath(){
        MonitFile mf = new MonitFile();
        mf.setFileName("abc.log");mf.setPath("/home/abc/");mf.setType("log4j");
        String id  = conf.addScanPath(mf);
        Assert.assertEquals(1, conf.getScanPaths().size());
        conf.removeScanPath(id);
        Assert.assertTrue(conf.getScanPaths().isEmpty());
    }

}
