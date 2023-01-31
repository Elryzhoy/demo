package com.example.demo.entry;

import lombok.Data;

import java.util.List;

/**
 * Create on 2022/6/15
 *
 * @author 周志文
 */
@Data
public class Teacher {

    private int fdId;

    private String fdName;

    private int fdSex;

    private String fdSubject;

    private List<Students> fdStudentsId;
}
