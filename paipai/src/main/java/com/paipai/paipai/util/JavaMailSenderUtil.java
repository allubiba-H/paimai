package com.paipai.paipai.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class JavaMailSenderUtil {
    @Resource
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private  String from;

    //发送邮箱
    public void send(String to,String subject,String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text,true);
        javaMailSender.send(mimeMessage);
    }

}
