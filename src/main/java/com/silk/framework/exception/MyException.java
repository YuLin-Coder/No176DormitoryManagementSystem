package com.silk.framework.exception;

/**
 * @author LindaSilk
 * @date 2021年3月09日, 周二
 */
public class MyException extends RuntimeException{

    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }
}
