package com.liust.myproject.MyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author liuyulong
 * @create 2022-02-15 12:01
 * @create 2022-二月  星期二
 * @project MyProject
 */
@RestControllerAdvice
public class ControllerHandlerException {

   private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    Object handlerException(Exception e) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if(e instanceof NoHandlerFoundException){
            map.put("code", 404);
        }else{
            map.put("code", 500);
        }
        map.put("exception", e.getClass().getSimpleName());
        map.put("msg", e.getMessage());
        logger.error("捕捉Web应用抛出错误", e);
        return map;
    }

}
