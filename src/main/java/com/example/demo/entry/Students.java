package com.example.demo.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Create on 2022/6/15
 *
 * @author 周志文
 */
@Data
@TableName("t_students")
public class Students {

    @TableId("fd_id")
    private int fdId;

    @TableField("fd_name")
    private String fdName;

    private String fdAge;

    private int fdSex;

    private String fdClass;

}
