package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create on 2022/6/10
 *
 * @author 周志文
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMail {

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void test() {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("577839849@qq.com");
        simpleMailMessage.setTo("2862708845@qq.com");
        simpleMailMessage.setSubject("测试邮件2222");
        simpleMailMessage.setText("测试邮件内容2222");
        javaMailSender.send(simpleMailMessage);
    }
}
