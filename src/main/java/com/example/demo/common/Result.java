package com.example.demo.common;

import com.example.demo.Enum.CodeEnum;
import lombok.Data;

/**
 * Create on 2022/6/8
 *  返回结果封装
 * @author 周志文
 */
@Data
public class Result<T> {

    /**
     * 响应状态码（0000表示成功，9999表示失败
     */
    private String code;

    /**
     * 响应结果描述
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 成功返回
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result<T> result= new Result<>();
        result.setCode(CodeEnum.SUCCESS.getCode());
        result.setMessage(CodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     *  失败返回
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(String code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(CodeEnum.ERROR.getCode());
        result.setMessage(CodeEnum.ERROR.getMessage());
        return result;
    }

}
