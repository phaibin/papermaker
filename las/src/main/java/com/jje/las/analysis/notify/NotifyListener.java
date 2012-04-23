package com.jje.las.analysis.notify;

import org.springframework.beans.factory.annotation.Autowired;

import com.jje.las.analysis.command.CommandListener;
import com.jje.las.analysis.command.LasContext;
import com.jje.las.config.LasConfiguration;
import com.jje.las.domain.Log;

public class NotifyListener implements CommandListener {

    @Autowired
    MailNotify notify;
    
    @Autowired
    LasConfiguration conf;
    
    public void before(LasContext ctx) {
    }

    public void after(LasContext ctx) {
        Log newLog = ctx.getNewLog();
//        if(newLog.getPriority().equals("ERROR")){
//            notify.send("lasnotifyer@jinjiang.com", "listener@jinjiang.com", "error notify", ctx.getCurrentLine());
//        }
    }

}
