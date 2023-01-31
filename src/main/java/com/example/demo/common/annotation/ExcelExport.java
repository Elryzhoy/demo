package com.example.demo.common.annotation;

import com.example.demo.common.enumeration.ExcelDateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create on 2022/12/19
 *
 * @author 周志文
 */
//用于描述Annotation所修饰的对象返范围
@Target(ElementType.FIELD)
//表明ExcelExport是一个注解
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelExport {
    /**
     * 列
     **/
    int cell() default 0;

    String title() default "";

    String dateFormat() default  "yyyy-MM-dd";
    ExcelDateType dataType() default ExcelDateType.STRING;
}
