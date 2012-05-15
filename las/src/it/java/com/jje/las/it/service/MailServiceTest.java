package com.jje.las.it.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.las.LasBaseSpringTest;
import com.jje.las.analysis.notify.MailNotify;

@Ignore
public class MailServiceTest extends LasBaseSpringTest{

    @Autowired
    MailNotify notify;
    @Test
    public void sendMail() throws InterruptedException{
        String from = "sean.xu@jinjiang.com";
        String to = "sean.xu@jinjiang.com";
        String title = "test form mail sender";
        String content = "aaaaaaaaaaaaaaaaaaaa";
        notify.send(from, to, title, content);
    }
}
