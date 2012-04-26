package com.jje.las.analysis.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailNotify {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String from, String to, String title, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setSubject(title);
        msg.setTo(to);
        msg.setText(content);
        try {
            this.mailSender.send(msg);
        } catch (Exception ex) {
            logger.error("send email error.", ex);
        }
    }
}
