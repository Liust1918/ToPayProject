package com.liust.myproject.MyInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liust.myproject.MyStatusCode.MyHttpState;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.PayPojo;
import com.liust.myproject.Pojo.userPojo;
import com.liust.myproject.Service.userService;
import com.liust.myproject.Utils.JsonUtil;
import com.liust.myproject.Utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liuyulong
 * @create 2022-02-17 2:09
 * @create 2022-二月  星期四
 * @project MyProject
 */
@Component
public class tokenInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if(token==null){
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(R.setResult("缺少请求头中的token", MyHttpState.Value_Null));
            logger.info("token为空");
            response.getWriter().println(s);
        }


        PayPojo payPojo = (PayPojo) JsonUtil.StreamToBean(request, PayPojo.class);
        Boolean aBoolean = jwtTokenUtil.validateToken(token,String.valueOf(payPojo.getUserId()));
        if (aBoolean) {
            logger.info("用户:"+payPojo.getUserId()+"的token通过");
            return true;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(R.setResult("用户的token无效或者过期(请重新登录)", MyHttpState.token_dumn));
        logger.info("用户:" + payPojo.getUserId() + " token无效或者过期");
        response.getWriter().println(s);
        return false;
    }
}
