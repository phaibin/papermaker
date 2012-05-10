package com.jje.las.action.log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jje.las.config.LasConfiguration;
import com.jje.las.domain.Log;
import com.jje.las.service.LasService;
import com.jje.las.util.DateTimeEditor;

@Controller
public class IndexController {

    @Autowired
    LasService l;
    
    @Autowired
    LasConfiguration conf;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateTimeEditor());
    }

    @RequestMapping(value = { "", "/index" })
    public String index(LogQueryForm form, Model model) {
        String[] prioritys = conf.getPrioritys();
        form.setPriority(prioritys[0]);
        Calendar calendar = Calendar.getInstance();
        form.setEnd(calendar.getTime());
        calendar.add(Calendar.HOUR, -1);
        form.setBegin(calendar.getTime());
        form.setModule("");
        LogQueryResult r = l.query(form.getBegin(), form.getEnd(), form.getPriority(), form.getModule(), form.getPage(), form.getPageSize());
        model.addAttribute("logs", r.getList());
        model.addAttribute("totalPage", r.getTotalPage());
        model.addAttribute("priorityList", Arrays.asList(prioritys));
        return "/log/index";
    }

    @RequestMapping(value = "/query")
    public String query(LogQueryForm form, Model model) {
        String[] prioritys = conf.getPrioritys();
        LogQueryResult r = l.query(form.getBegin(), form.getEnd(), form.getPriority(), form.getModule(), form.getPage(), form.getPageSize());
        model.addAttribute("logs", r.getList());
        model.addAttribute("totalPage", r.getTotalPage());
        model.addAttribute("priorityList", Arrays.asList(prioritys));
        return "/log/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/log/{id}")
    public String detail(@PathVariable String id, @RequestParam String date, @RequestParam String priority, Model model) throws Exception {
        //date=Thu Feb 09 18:32:42 CST 2012&priority=ERROR
        SimpleDateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        model.addAttribute("log", l.detail(id, df.parse(date), priority));
        return "/log/detail";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associate/{id}")
    public String associateLog(@PathVariable String id, @RequestParam String date, @RequestParam String priority, Model model) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Log log = l.detail(id, df.parse(date), priority);
        model.addAttribute("logs", l.associateQuery(log));
        return "/log/associatelog";
    }

}
