package com.liust.myproject.MyException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
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

    @ExceptionHandler(MismatchedInputException.class)
    Object MyMismatchedInputException(Exception e){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", 9000);
        map.put("msg", "你没有输入json内容");
        map.put("exception", e.getClass().getSimpleName());
        map.put("msgException", e.getMessage());
        logger.error("没有输入json内容", e);
        return map;
    }
}
