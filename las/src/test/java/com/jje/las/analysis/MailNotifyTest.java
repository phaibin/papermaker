package com.jje.las.analysis;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.jje.las.analysis.notify.MailNotify;

public class MailNotifyTest {

    @Test
    public void sendMail(){
        MailNotify notify = new MailNotify();
        MailSender mailSender = mock(MailSender.class);
        notify.setMailSender(mailSender);
        String from = "a@jinjaing.com";
        String to = "to@jinjiang.com";
        String title = "test form mail sender";
        String content = "aaaaaaaaaaaaaaaaaaaa";
        notify.send(from, to, title, content);
        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(from);mailMsg.setTo(to);mailMsg.setSubject(title);mailMsg.setText(content);
        verify(mailSender).send(mailMsg);
    }
}
