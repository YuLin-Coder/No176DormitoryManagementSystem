package com.silk.framework.mvc;

import com.silk.framework.exception.MyException;
import com.silk.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LindaSilk
 * @date 2021年3月10日, 周三
 * @description 全局异常处理
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result handle(RuntimeException exception){
        exception.printStackTrace();
        return Result.ok(exception.getMessage());
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result handle(MyException exception){
        exception.printStackTrace();
        return Result.ok(exception.getMessage());
    }

}
