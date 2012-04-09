package com.jje.las.analysis;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.las.action.admin.MonitFile;
import com.jje.las.action.log.Log;
import com.jje.las.service.LasLogService;

@Component
public class Log4JHandler {

    private LasLogService handle;
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    AnalysisChain analysisChain = new AnalysisChain();

    @Autowired
    public void setHandle(LasLogService handle) {
        this.handle = handle;
    }

    public void handleLogFile(MonitFile mfile) throws Exception {
        File file = mfile.getRealFile();
        try {
            parser(file, mfile.getFileName());
        } catch (Exception ex) {
            logger.error("Parse error."+file, ex);
        }
        file.delete();
    }

    protected void parser(File file, String from) throws Exception {
        Log last = null;
        LasContext context = new LasContext();
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        try {
            while (it.hasNext()) {
                context.setCurrentLine(it.nextLine()).setLastLog(last).setFileFrom(from);
                if (!analysisChain.execute(context)) {
                    continue;
                }
                Log newLog = context.getNewLog();
                if (newLog != null) {
                    handle.insert(last);
                    last = newLog;
                }
            }
            handle.insert(last);
        } finally {
            it.close();
        }
    }

}
