package com.example.demo;

import java.util.ArrayList;

/**
 * Create on 2023/1/13
 * arrayList框架测试类
 * @author 周志文
 */
public class ArrayListTest {

    public static void main(String[] args) {
        ArrayList<String > list = new ArrayList<>();
        list.add("周");
        list.add("志");
        list.add("文");
        //ArrayList中set方法会返回下标的值（旧的值），set的下标超出size会报错，因为set之前会校验rangeCheck(index);
        // get之前也会走这个方法校验
        System.out.println(list.set(0, "zhou"));
//        System.out.println(list.set(3, "wen"));
//        for (String l:list) {
//            System.out.println(list.remove(l));
//        }
        System.out.println(list);
    }
}
