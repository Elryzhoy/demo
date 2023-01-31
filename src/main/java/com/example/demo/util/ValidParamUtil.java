package com.example.demo.util;

import com.example.demo.common.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Create on 2022/6/9
 *  用于校验参数的工具类
 * @author 周志文
 */
public class ValidParamUtil {

    /**
     * 参数校验
     * @param bindingResult
     * @return
     */
    public static Result<Object> validParam(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage()).append("\n");
            }
            return Result.fail("400", sb.toString());
        }else{
            return null;
        }
    }

    /**
     * 判断结果是否为空
     * @param result
     * @return
     */
    public static boolean flag(Result result){
        if(result==null){
            return true;
        }else{
            return false;
        }
    }


}
