package com.example.demo.entry;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Create on 2022/5/24
 *
 * @author 周志文
 */
@Data
@ConfigurationProperties(prefix = "person")
@Component
@Validated
public class Person {

    @NotNull(message = "用户名不能为空")
    private String name;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "邮件不能为空")
    @Email(message = "邮件格式不正确")
    private String email;

    @NotNull(message = "手机号不能为空")
    @Length(max = 11,min = 11 , message = "手机号长度不正确")
    private String phone;

    @NotNull(message = "地址不能为空")
    private String address;


}
