package com.jje.las.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
                List<MonitFile> convertList = convertList(handler.listMonitorFiles());
                final CountDownLatch latch = new CountDownLatch(convertList.size());
                ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
                for (final MonitFile log : convertList) {
                    threadPool.submit(new Runnable() {
                        public void run() {
                            if(logger.isDebugEnabled())
                                logger.debug("perform log file name:" + log.getPath());
                            parser.handleLogFile(log, new IAction() {
                                public void perform(MonitFile mfile) {
                                    boolean deleteQuietly = FileUtils.deleteQuietly(mfile.getRealFile());
                                    if(logger.isDebugEnabled())
                                        logger.debug("Delete file "+mfile.getFileName() + " " + deleteQuietly);
                                }
                            });
                            latch.countDown();
                        }
                    });
                }
                try {
                    latch.await(30, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    logger.error("Exceed time limit in scanner log file");
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
