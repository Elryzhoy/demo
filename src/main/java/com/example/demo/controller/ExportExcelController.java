package com.example.demo.controller;

import org.springframework.stereotype.Controller;

/**
 * Create on 2022/12/26
 *  测试导出excel文件工具类
 * @author 周志文
 */
@Controller
public class ExportExcelController {

    public static void main(String[] args) {
//        System.out.println(System.getSecurityManager().toString());
        System.out.println("周2".codePointAt(0));
        String s = "Aa";
        for(int i = 0; i< s.length();i++){
            System.out.println((int)s.charAt(i));
            System.out.println(Integer.toHexString( s.charAt(i)));
            System.out.println(s.codePointAt(i));
        }
    }
}
