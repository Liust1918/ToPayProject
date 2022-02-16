package com.liust.myproject.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liust.myproject.MyFiter.RequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JsonUtil {

    public static Object StreamToBean(HttpServletRequest request, Class bean) throws IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        Object payPojo = objectMapper.readValue(body,bean);
        return payPojo;
    }
}
