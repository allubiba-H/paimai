package com.paipai.handler;

import com.paipai.paipai.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R handlerException(HttpServletRequest request, Exception exception){
        return R.error(exception.getMessage());
    }


}
