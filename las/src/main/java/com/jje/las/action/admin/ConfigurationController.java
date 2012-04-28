package com.jje.las.action.admin;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jje.las.config.LasConfiguration;
import com.jje.las.task.LasTimer;

@Controller
public class ConfigurationController {
    
    @Autowired
    LasConfiguration conf;
    
    @Autowired
    LasTimer scannerTimer;

    @RequestMapping(method = RequestMethod.GET, value = "/conf/log/package" )
    public String info(@RequestParam String p, @RequestParam String l, Model model) {
        Level level = Level.toLevel(l);
        Logger logger = LogManager.getLogger(p);
        logger.setLevel(level);
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conf/log/root" )
    public String info(@RequestParam String l, Model model) {
        Level level = Level.toLevel(l);
        LogManager.getRootLogger().setLevel(level);
        return "success";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/conf/scanner" )
    public String adjustScannerSchedule(@RequestParam Long l, Model model){
        conf.setScanInterval(l);
        scannerTimer.restart();
        return "success";
    }

}
