package com.jje.las.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.las.domain.MonitFile;
import com.jje.las.service.AdminService;

@Component
public class LasConfiguration {

    @Autowired
    AdminService ms;

    List<MonitFile> scanPaths = new ArrayList<MonitFile>();
    
    public void setAdminService(AdminService admin){
        ms = admin;
    }

    public List<MonitFile> getScanPaths() {
        if(scanPaths.isEmpty()){
            scanPaths = ms.listMonitorFiles();
        }
        return scanPaths;
    }

    public void setScanPaths(List<MonitFile> scanPaths) {
        this.scanPaths = scanPaths;
    }
    
    public String addScanPath(MonitFile mf){
        mf.setId(UUID.randomUUID().toString());
        scanPaths.add(mf);
        ms.addFileToMonitor(mf);
        return mf.getId();
    }
    
    public void removeScanPath(String id){
        MonitFile mf = null;
        for(MonitFile f : scanPaths){
            if(f.getId().equals(id)){
                mf = f;
            }
        }
        if(mf != null ){
            scanPaths.remove(mf);
            ms.removeFileMonitor(id);
        }
    }
    
}
