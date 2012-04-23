package com.jje.las.config;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jje.las.domain.MonitFile;

@Component
public class LasConfiguration {

    List<MonitFile> scanPaths;

    public List<MonitFile> getScanPaths() {
        return scanPaths;
    }

    public void setScanPaths(List<MonitFile> scanPaths) {
        this.scanPaths = scanPaths;
    }
    
    public void addScanPath(MonitFile mf){
        scanPaths.add(mf);
    }
    
    public void removeScanPath(MonitFile mf){
        scanPaths.remove(mf);
    }

}
