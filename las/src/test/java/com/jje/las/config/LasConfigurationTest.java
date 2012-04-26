package com.jje.las.config;

import java.util.Stack;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.jje.las.domain.MonitFile;
import com.jje.las.service.AdminService;

public class LasConfigurationTest {

    @Test
    public void addScanPath(){
        LasConfiguration lc = new LasConfiguration();
        lc.setAdminService(Mockito.mock(AdminService.class));
        addMF(lc, "0", "abc.log");
        Assert.assertEquals(1, lc.getScanPaths().size());
    }
    
    @Test
    public void removeScanPath(){
        LasConfiguration lc = new LasConfiguration();
        lc.setAdminService(Mockito.mock(AdminService.class));
        Stack<String> idStack = new Stack<String>();
        for(int i=0; i<3; i++){
            idStack.push(addMF(lc, ""+i, "name"+i));
        }
        Assert.assertEquals(3, lc.getScanPaths().size());
        lc.removeScanPath(idStack.pop());
        Assert.assertEquals(2, lc.getScanPaths().size());
        lc.removeScanPath("44");
        Assert.assertEquals(2, lc.getScanPaths().size());
        lc.removeScanPath(idStack.pop());
        Assert.assertEquals(1, lc.getScanPaths().size());
        lc.removeScanPath(idStack.pop());
        Assert.assertEquals(0, lc.getScanPaths().size());
    }

    private String addMF(LasConfiguration lc, String id, String name) {
        MonitFile mf = new MonitFile();
        mf.setFileName(name);mf.setId(id);mf.setPath("/home/abc/abc.log");mf.setType("log4j");
        lc.addScanPath(mf);
        return mf.getId();
    }
    
}
