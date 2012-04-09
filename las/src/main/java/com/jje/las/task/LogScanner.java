package com.jje.las.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jje.las.action.admin.MonitFile;
import com.jje.las.analysis.FileParserHandler;
import com.jje.las.service.MonitLogService;

@Component
public class LogScanner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Lock lock = new ReentrantLock();
    @Autowired
    MonitLogService handler;
    @Autowired
    FileParserHandler parser;

    @Async
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void perform() {
        if (lock.tryLock()) {
            logger.debug("start scanner");
            try {
                for (MonitFile log : convertList(handler.list())) {
                    logger.info("perform log file name:" + log.getPath());
                    try {
                        parser.handleLogFile(log);
                    } catch (Exception e) {
                        logger.error("error in parse file " + log.getFileName(), e);
                    }
                }
                logger.debug("end scanner ");
            } finally {
                lock.unlock();
            }
        } else {
            logger.debug("scanner task is running.");
        }
    }

    private List<MonitFile> convertList(List<MonitFile> list) {
        List<MonitFile> result = new ArrayList<MonitFile>();
        for (MonitFile log : list) {
            File f = new File(log.getPath());
            if (!f.exists())
                continue;
            Collection<File> fc1 = FileUtils.listFiles(f, null, true);
            for (File f1 : fc1) {
                MonitFile mf = new MonitFile(f1.getAbsolutePath(), log.getType());
                mf.setFileName(f1.getName());
                result.add(mf);
            }
        }
        return result;
    }
}
