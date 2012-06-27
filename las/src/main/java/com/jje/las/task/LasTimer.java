package com.jje.las.task;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.las.analysis.FileParserHandler;
import com.jje.las.config.LasConfiguration;
import com.jje.las.service.AdminService;

public class LasTimer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LasConfiguration lasConf;

    @Autowired
    AdminService as;

    @Autowired
    FileParserHandler parser;

    Timer scannerTimer;

    public void init(){
        logger.info("Start Las Scanner Timer");
        scannerTimer = new Timer("Las Scanner Timer for auto");
        scannerTimer.schedule(new LogScanner(as, parser, lasConf), 1000, lasConf.getScanInterval());
    }

    public void restart() {
        destory();
        init();
    }
    
    public void manualStart(){
        Timer manualTimer = new Timer("Las Scanner Timer for manual");
        manualTimer.schedule(new LogScanner(as, parser, lasConf), 1000);
    }

    public void destory() {
        logger.info("Destory Las Scanner Timer");
        scannerTimer.cancel();
    }
}
