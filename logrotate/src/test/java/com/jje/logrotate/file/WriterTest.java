package com.jje.logrotate.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class WriterTest {

    @Test
    public void writeAnotherWriter() throws IOException, InterruptedException {
        CountDownLatch writeLatch = new CountDownLatch(1);
        CountDownLatch clearLatch = new CountDownLatch(1);

        File outFile = new File("./abc.log");
        Thread writeThread = new Thread(new LogWriter(outFile, writeLatch));
        writeThread.start();
        LogClear logClear = new LogClear(outFile, clearLatch);
        Thread clearThread = new Thread(logClear);
        clearThread.start();
        writeLatch.await();
        logClear.setToBeContinue(false);
        clearLatch.await();
        FileUtils.deleteQuietly(outFile);
    }
}

class LogClear implements Runnable {
    File file;
    CountDownLatch latch;
    volatile boolean toBeContinue = true;

    public LogClear(File f, CountDownLatch writeLatch) {
        file = f;
        latch = writeLatch;
    }

    public void run() {
        while (toBeContinue) {
            try {
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                System.out.println(raf.length());
                int l = (int)raf.length();
                byte[] bufs = new byte[l];
                raf.read(bufs);
                new Thread(new PrintLog(bufs)).start();
                raf.setLength(0);
                raf.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(new Double(Math.random() * 1000).longValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        latch.countDown();
    }

    public boolean isToBeContinue() {
        return toBeContinue;
    }

    public void setToBeContinue(boolean toBeContinue) {
        this.toBeContinue = toBeContinue;
    }

}

class PrintLog implements Runnable{
    private String log;
    public PrintLog(byte[] bufs){
        log = new String(bufs);
    }
    public void run() {
        System.out.println(log);
    }
    
}

class LogWriter implements Runnable {
    File file;
    CountDownLatch latch;
    int count = 10;
    volatile boolean toBeContinue = true;

    public LogWriter(File f, CountDownLatch writeLatch) {
        file = f;
        latch = writeLatch;
    }

    public void run() {
        while (count-- > 0) {
            if (!toBeContinue) {
                break;
            }
            try {
                String line = new Date().toString() + " === \n";
                FileUtils.write(file, line, true);
                Thread.sleep(new Double(Math.random() * 1000).longValue());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        latch.countDown();
    }

    public boolean isToBeContinue() {
        return toBeContinue;
    }

    public void setToBeContinue(boolean toBeContinue) {
        this.toBeContinue = toBeContinue;
    }

}
