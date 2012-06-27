package com.jje.las.task;

import java.io.File;
import java.io.IOException;
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
import com.jje.las.config.LasConfiguration;
import com.jje.las.domain.MonitFile;
import com.jje.las.service.AdminService;

public class LogScanner extends TimerTask{

    private final class DeleteFileAction implements IAction {
		public void perform(MonitFile mfile) {
		    boolean deleteQuietly = FileUtils.deleteQuietly(mfile.getRealFile());
		    if(logger.isDebugEnabled())
		        logger.debug("Delete file "+mfile.getFileName() + " " + deleteQuietly);
		}
	}
    
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Lock lock = new ReentrantLock();
    AdminService handler;
    FileParserHandler parser;
    LasConfiguration lasConf;
    
    public LogScanner(AdminService svc, FileParserHandler hand, LasConfiguration conf){
        handler = svc;
        parser = hand;
        lasConf = conf;
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
            	List<MonitFile> listMonitorFiles = handler.listMonitorFiles();
				backup(listMonitorFiles);
                List<MonitFile> convertList = convertList(listMonitorFiles);
                final CountDownLatch latch = new CountDownLatch(convertList.size());
                ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
                for (final MonitFile log : convertList) {
                    threadPool.submit(new Runnable() {
                        public void run() {
                            if(logger.isDebugEnabled())
                                logger.debug("perform log file name:" + log.getPath());
                            
                            try {
								parser.handleLogFile(log, new DeleteFileAction());
							} catch (Exception e) {
								logger.error("parser handler log file error", e);
							}finally{
								latch.countDown();
							}
                        }
                    });
                }
                try {
                    latch.await(lasConf.getScanInterval(), TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    logger.error("Exceed time limit in scanner log file");
                }
                logger.debug("end scanner ");
            } catch(Throwable t){
            	logger.error("error in logScanner.", t);
            } finally {
                lock.unlock();
            }
        } else {
            logger.debug("scanner task is running.");
        }
    }

	private void backup(List<MonitFile> list) {
 
		for (MonitFile log : list) {
            File f = new File(log.getPath());
            if (f.exists()){
                try {
					FileUtils.copyDirectory(f, lasConf.getBackupDirectory());
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }		
	}
}
