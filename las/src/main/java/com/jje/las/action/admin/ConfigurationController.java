package com.jje.las.action.admin;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jje.las.config.LasConfiguration;
import com.jje.las.task.LasTimer;

@Controller
public class ConfigurationController {

    @Autowired
    LasConfiguration conf;

    @Autowired
    LasTimer scannerTimer;

    @RequestMapping(method = RequestMethod.GET, value = "/conf/log/package/{p}/{l}")
    public String info(@PathVariable String p, @PathVariable String l, Model model) {
        Level level = Level.toLevel(l);
        Logger logger = LogManager.getLogger(p);
        logger.setLevel(level);
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conf/log/root/{l}")
    public String info(@PathVariable String l, Model model) {
        Level level = Level.toLevel(l);
        LogManager.getRootLogger().setLevel(level);
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conf/scanner/{interval}")
    public String adjustScannerSchedule(@PathVariable Long interval, Model model) {
        conf.setScanInterval(interval);
        scannerTimer.restart();
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conf/associate/{interval}")
    public String adjustAssociateInterval(@PathVariable Integer interval, Model model) {
        conf.setInterval(interval);
        return "success";
    }

}
