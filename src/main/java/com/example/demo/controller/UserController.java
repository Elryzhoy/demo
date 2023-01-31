package com.example.demo.controller;

import com.example.demo.dao.StudentsMapper;
import com.example.demo.entry.ov.UserOV;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create on 2022/6/11
 *
 * @author 周志文
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    StudentsMapper studentsMapper;

    @GetMapping("/getUserByName")
    public UserOV getUserByName(String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/getStudents")
    public void getStudents() {
        System.out.println(studentsMapper.selectList(null));
    }



}
