package com.example.demo.service.impl;

import com.example.demo.dao.UserMapper;
import com.example.demo.entry.ov.UserOV;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create on 2022/6/11
 *
 * @author 周志文
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override

    public UserOV getUserByName(String name) {
        return userMapper.selectByName(name);
    }
}
