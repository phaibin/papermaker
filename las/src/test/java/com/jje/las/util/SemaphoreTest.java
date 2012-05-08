package com.jje.las.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class SemaphoreTest {

    @Test
    public void testAbc() throws InterruptedException, IOException {
        int maxBarr = 10;
        final CountDownLatch sp = new CountDownLatch(maxBarr);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        for (int i = 0; i < maxBarr; i++) {
            threadPool.submit(new Callable<String>() {
                public String call() {
                    long r = (long) (Math.random() * 1000);
                    try {
                        Thread.sleep(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sp.countDown();
                    try {
                        baos.write(Thread.currentThread().getName().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            });
        }
        sp.await();
        baos.write("abcde".getBytes());
        byte[] byteArray = baos.toByteArray();
        baos.close();
        String result = new String(byteArray);
        Assert.assertTrue(result.endsWith("abcde"));
        Assert.assertTrue(result.length()>20);
    }
}
