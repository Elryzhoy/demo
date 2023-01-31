package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;

/**
 * Create on 2022/6/9
 *  测试欢迎页面
 * @author 周志文
 */
@Controller
public class TestWelcomePage {

    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("name", "变量");

        ArrayList<String> list = new ArrayList<>();
        list.add("配置文件属性注入");
        list.add("多环境配置");
        list.add("多数据源配置");
        list.add("SpringBoot图标配置");
        modelMap.addAttribute("sex", 1);
        modelMap.addAttribute("list", list);
        modelMap.addAttribute("date",new Date());
        modelMap.addAttribute("code","<span style='color:red'>转义字符</span>");

        return "index";
    }

    /**
     * 排序
     */
    public void sorting(){

        // 冒泡排序
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        
    }

}
