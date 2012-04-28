package com.jje.las.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jje.las.analysis.FileParserHandler;
import com.jje.las.analysis.IAction;
import com.jje.las.domain.MonitFile;
import com.jje.las.service.AdminService;

public class LogScanner extends TimerTask{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Lock lock = new ReentrantLock();
    AdminService handler;
    FileParserHandler parser;
    
    public LogScanner(AdminService svc, FileParserHandler hand){
        handler = svc;
        parser = hand;
    }

    public void perform() {
        run();
    }

    private List<MonitFile> convertList(List<MonitFile> list) {
        List<MonitFile> result = new ArrayList<MonitFile>();
        for (MonitFile log : list) {
            File f = new File(log.getPath());
            if (f.exists()){
                Collection<File> fc1 = FileUtils.listFiles(f, null, true);
                for (File f1 : fc1) {
                    MonitFile mf = new MonitFile(f1.getAbsolutePath(), log.getType());
                    mf.setFileName(f1.getName());
                    result.add(mf);
                }
            }
        }
        return result;
    }

    @Override
    public void run() {
        if (lock.tryLock()) {
            logger.debug("start scanner");
            try {
                for (MonitFile log : convertList(handler.listMonitorFiles())) {
                    logger.debug("perform log file name:" + log.getPath());
                    parser.handleLogFile(log, new IAction() {
                        public void perform(MonitFile mfile) {
                            mfile.getRealFile().delete();
                        }
                    });
                }
                logger.debug("end scanner ");
            } finally {
                lock.unlock();
            }
        } else {
            logger.debug("scanner task is running.");
        }
    }
}
