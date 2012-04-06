package com.jje.las.action.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jje.las.service.LasLogService;
import com.jje.las.util.DateTimeEditor;

@Controller
public class IndexController {

    @Autowired
    LasLogService l;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateTimeEditor());
    }

    @RequestMapping(value = { "", "/index" })
    public String index(LogQueryForm form, Model model) {
        form.setPriority("ERROR");
        LogQueryResult r = l.query(form);
        model.addAttribute("logs", r.getList());
        model.addAttribute("totalPage", r.getTotalPage());
        return "/log/index";
    }

    @RequestMapping(value = "/query")
    public String query(LogQueryForm form, Model model) {
        LogQueryResult r = l.query(form);
        model.addAttribute("logs", r.getList());
        model.addAttribute("totalPage", r.getTotalPage());
        return "/log/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/log/{id}")
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("log", l.get(id));
        return "/log/detail";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/associate/{id}")
    public String associateLog(@PathVariable String id, Model model) {
        Log log = l.get(id);
        model.addAttribute("logs", l.associateQuery(log));
        return "/log/associatelog";
    }

}
