package com.liust.myproject.MyInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liust.myproject.Controller.payController;
import com.liust.myproject.Dao.ruleRepository;
import com.liust.myproject.Dao.userRepository;
import com.liust.myproject.MyFiter.RequestWrapper;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.PayPojo;
import com.liust.myproject.Pojo.userPojo;
import com.liust.myproject.Service.userService;
import com.liust.myproject.Utils.JsonUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;


/**
 * 白名单拦截(1)
 */

@Component
public class whiteListInterceptor implements HandlerInterceptor {

    private  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private userService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        PayPojo payPojo = (PayPojo)JsonUtil.StreamToBean(request, PayPojo.class);
        logger.info("用户:"+payPojo.getUserId()+" 白名单拦截");
        boolean present = Optional.ofNullable(payPojo).isPresent();


        if(!present){
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(new R().valueIsNull("payPojo不存在(白名单拦截)"));
            response.getWriter().println(s);
            return false;
        }

        userPojo userPojo = userService.getUserById(payPojo.getUserId());
        boolean present1 = Optional.ofNullable(userPojo).isPresent();
        if(!present1){
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            R r = new R().valueIsNull("用户不存在(白名单拦截)");
            String s = objectMapper.writeValueAsString(r);
            response.getWriter().println(s);
            return false;
        }

        if(userPojo.getIsOpen()==1){
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            R r = new R().valueIsNull("用户处于黑名单(白名单拦截)");
            String s = objectMapper.writeValueAsString(r);
            response.getWriter().println(s);
            return false;
        }

        return true;
    }


}
