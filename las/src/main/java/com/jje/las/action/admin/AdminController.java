package com.jje.las.action.admin;

import java.util.Date;
import java.util.List;

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
import com.jje.las.service.MonitLogService;
import com.jje.las.task.LogScanner;
import com.jje.las.util.DateTimeEditor;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    MonitLogService ms;

    @Autowired
    LogScanner scanner;
    
    
    @Autowired
    LasLogService ls;
    
    
    @InitBinder  
    protected void initBinder(HttpServletRequest request,  
            ServletRequestDataBinder binder)  {  
           binder.registerCustomEditor(Date.class,new DateTimeEditor());     
    } 

    @RequestMapping(method = RequestMethod.GET, value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("logs", ms.list());
        return "admin/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/create"})
    public String create(Model model) {
        model.addAttribute("monitLog", new MonitFile());
        return "admin/create";
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/create"})
    public String save(MonitFile log, Model model) {
        ms.save(log);
        model.addAttribute("logs", ms.list());
        return "admin/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
    public String delete(MonitFile log, @PathVariable String id, Model model) {
        ms.delete(id);
        List<MonitFile> list = ms.list();
        model.addAttribute("logs", list);
        return "admin/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/start"})
    public String start(Model model) {
        model.addAttribute("logs", ms.list());
        scanner.perform();
        return "admin/index";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = {"/deletelog"})
    public String goDelete(Model model) {
    	 model.addAttribute("logDelForm", new LogDelForm());
        return "admin/deletelog";
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = {"/deletelog"})
    public String goDelete(LogDelForm logDelForm,Model model) {
    	ls.delete(logDelForm);
    	model.addAttribute("logs", ms.list());
        return "admin/index";
    }

}
