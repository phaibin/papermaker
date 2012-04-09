package com.jje.las.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.las.action.admin.MonitFile;
import com.jje.las.action.log.Log;
import com.jje.las.service.LasLogService;

@Component
public class Log4JHandler implements IHandleLogFile {

    private LasLogService handle;

    AnalysisChain analysisChain = new AnalysisChain();
    
    @Autowired
    public void setHandle(LasLogService handle) {
        this.handle = handle;
    }

    public void handleLogFile(MonitFile mfile) throws Exception {
        String fileAddress = mfile.getPath();
        File file = new File(fileAddress);
        if (!file.exists()) {
            return;
        }
        FileInputStream is = new FileInputStream(file);
        try {
            parser(is, mfile.getFileName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        is.close();
        file.delete();
    }

    @SuppressWarnings("unchecked")
    protected void parser(InputStream is, String from) throws Exception {
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        Log last = null;
        Context context = new ContextBase();
        while ((line = br.readLine()) != null) {
            context.put("currentLine", line);
            context.put("lastLog", last);
            context.put("fileFrom", from);
            boolean success = analysisChain.execute(context);
            if(!success){
                continue;
            }
            Log newLog = (Log)context.get("newLog");
            if(newLog != null){
                handle.insert(last);
                last = newLog;
            }
        }
        handle.insert(last);
        br.close();
        isr.close();
    }

}
