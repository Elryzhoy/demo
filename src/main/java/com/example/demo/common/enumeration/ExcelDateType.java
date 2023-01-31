package com.example.demo.common.enumeration;

/**
 * Create on 2022/12/19
 *
 * @author 周志文
 */
public enum ExcelDateType {
    STRING("string"),
    AMOUNT("amount"),
    DATE("date");
    private String type;

    //构造方法
    ExcelDateType(String type) {
        this.type = type;
    }
}
