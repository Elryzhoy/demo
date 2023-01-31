package com.example.demo.test;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

/**
 * Create on 2022/5/23
 *
 * @author 周志文
 */
public class TestRedis {

    @Value("${spring.redis.host}")
    public String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    public static void main(String[] args) {

        //创建redis对象
        Jedis jedis=new Jedis("114.116.254.98",6379);
        jedis.auth("zhou123");
        //测试
        String  value=jedis.ping();
        System.out.println(value);
        jedis.close();
    }
}
