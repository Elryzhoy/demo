package com.example.demo.service;

import com.example.demo.entry.ov.UserOV;

/**
 * Create on 2022/6/11
 *
 * @author 周志文
 */
public interface UserService {

     UserOV getUserByName(String name);
}
