package com.example.demo.Enum;

/**
 * Create on 2022/6/8
 *
 * @author 周志文
 */
public enum CodeEnum {

    /**操作成功**/
    SUCCESS("200","操作成功"),
    /**操作失败**/
    ERROR("500","操作失败"),;

    /**
     * 自定义状态码
     **/
    private String code;
    /**自定义描述**/
    private String message;

    CodeEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
