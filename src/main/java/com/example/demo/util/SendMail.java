package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Create on 2022/6/11
 *  发送邮件工具类
 * @author 周志文
 */
@Component
public class SendMail {


    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @return
     */
    public void sendMail(JavaMailSender javaMailSender, String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);

    }

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param cc 抄送
     * @return
     */
    public void sendMail(JavaMailSender javaMailSender, String to, String subject, String content,String cc) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setCc(cc);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);

    }

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param file 附件
     * @return
     */
    public void sendMail(JavaMailSender javaMailSender, String to, String subject, String content, File file) throws MessagingException {
        MimeMessage mimeMailMessage =  javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"utf-8");
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content,true);
        mimeMessageHelper.addAttachment(file.getName(),file);
        javaMailSender.send(mimeMailMessage);

    }

    /**
     * 发送邮件给多人
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param file 附件
     * @param cc 抄送人
     * @return
     */
    public void sendMailMany(JavaMailSender javaMailSender, String to, String subject, String content, File file, String cc) throws MessagingException {
        MimeMessage mimeMailMessage =  javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"utf-8");
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content,true);
        mimeMessageHelper.addAttachment(file.getName(),file);
        mimeMessageHelper.setCc(cc);
        javaMailSender.send(mimeMailMessage);

    }

    /**
     * 发送邮件给多人带附件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param file 附件
     * @return
     */
    public void sendMailMany(JavaMailSender javaMailSender, String[] to, String subject, String content, File file) throws MessagingException {
        MimeMessage mimeMailMessage =  javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"utf-8");
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content,true);
        mimeMessageHelper.addAttachment(file.getName(),file);
        javaMailSender.send(mimeMailMessage);

    }


}
