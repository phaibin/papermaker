package com.jje.las.action.admin;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/logEnable")
public class LogLevelController {

    @RequestMapping(method = RequestMethod.GET, value = "/package" )
    public String info(@RequestParam String p, @RequestParam String l, Model model) {
        Level level = Level.toLevel(l);
        Logger logger = LogManager.getLogger(p);
        logger.setLevel(level);
        System.out.println(p+":"+level);
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/root" )
    public String info(@RequestParam String l, Model model) {
        Level level = Level.toLevel(l);
        LogManager.getRootLogger().setLevel(level);
        System.out.println(level);
        return "success";
    }

}
