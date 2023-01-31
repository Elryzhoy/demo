package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.entry.Person;
import com.example.demo.util.SendMail;
import com.example.demo.util.ValidParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Arrays;

/**
 * Create on 2022/5/30
 *优雅的使用接口
 * @author 周志文
 */
@RestController
@RequestMapping("/test")
public class TestApi {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SendMail sendMail;

    public static void main(String[] args) {


        String a = "abc";
        System.out.println();
        a="abca";
        System.out.println();

        int[] arr = new int[100000];
        for(int i=0;i<arr.length;i++){
            arr[i] = (int)Math.round(Math.random()*100000);
        }
        long start = System.currentTimeMillis();
        //快排
        Arrays.sort(arr);
        long end = System.currentTimeMillis();
        System.out.println("排序耗时："+(end-start));
    }


    @PostMapping("/valid")
    public Result testApi(@RequestBody @Validated Person person, BindingResult bindingResult){

        //参数校验
        Result result = ValidParamUtil.validParam(bindingResult);

        if(ValidParamUtil.flag(result)){
            //业务逻辑

            return Result.success(person);
        }else{
            return result;
        }
    }

    @GetMapping("/send")
    public void testMail(){
        //发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("577839849@qq.com");
        simpleMailMessage.setTo("2862708845@qq.com");
        simpleMailMessage.setSubject("测试邮件");
        simpleMailMessage.setText("测试邮件内容");
        javaMailSender.send(simpleMailMessage);
    }

    @GetMapping("/send2")
    public void testMail2() throws MessagingException {
        //发送邮件
        sendMail.sendMail(javaMailSender,"2862708845@qq.com", "发送邮件", "发送邮件不带附件");
        sendMail.sendMail(javaMailSender,"2862708845@qq.com", "发送邮件", "发送邮件带附件", new File("/Users/macbook/Downloads/周志文 Java软件工程师.pdf"));
    }
}
