package com.example.demo.dao;

import com.example.demo.entry.ov.UserOV;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create on 2022/6/11
 *
 * @author 周志文
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    UserOV selectByName(String name);


}
