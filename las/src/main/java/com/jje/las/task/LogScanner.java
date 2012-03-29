package com.jje.las.task;

import com.jje.las.action.admin.MonitFile;
import com.jje.las.analysis.Log4JHandler;
import com.jje.las.analysis.HandleWebLogicLogFile;
import com.jje.las.analysis.IHandleLogFile;
import com.jje.las.service.MonitLogService;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogScanner {

    private static Logger logger = Logger.getLogger(LogScanner.class.getName());
    private static Object lockObject =new Object();
    private static boolean isWorking =false;
    
    @Autowired
    MonitLogService handler;
    @Autowired
    HandleWebLogicLogFile webLogicHandle;
    @Autowired
    Log4JHandler log4jHandle;

    @Async
    @Scheduled(fixedRate = 5*60*1000)
    public void perform() {
    	synchronized (lockObject) {
    		if(!isWorking)
    		{
	    		isWorking=true;
		        logger.debug("start scanner:time"+System.currentTimeMillis());
		        List<MonitFile> list = handler.list();
		        logger.debug("list:"+list);
		        for (MonitFile log : convertList(list)) {
		            IHandleLogFile fileHandle =log4jHandle;
		            logger.debug("perform log file name:" + log.getPath());
		            try {
		                fileHandle.handleLogFile(log);
		            } catch (Exception e) {
		                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		            }
		        }
		        logger.debug(" scanner end:time"+System.currentTimeMillis());
		        isWorking=false;
    		}
    	}
    }

    private List<MonitFile> convertList(List<MonitFile> list) {
        List<MonitFile> result = new ArrayList<MonitFile>();
        for (MonitFile log : list) {
            File f = new File(log.getPath());
            if(!f.exists())
                continue;
            Collection<File> fc1 = FileUtils.listFiles(f, null, true);
            for(File f1:fc1){
                MonitFile mf = new MonitFile(f1.getAbsolutePath(), log.getType());
                mf.setFileName(f1.getName());
                result.add(mf);
            }
        }
        return result;
    }
}
