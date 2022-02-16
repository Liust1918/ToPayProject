package com.liust.myproject.MyInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.PayPojo;
import com.liust.myproject.Pojo.rulePojo;
import com.liust.myproject.Service.ruleService;
import com.liust.myproject.Utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 支付金额限制拦截(3)
 */
@Component
public class valueLimitInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ruleService ruleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PayPojo payPojo = (PayPojo) JsonUtil.StreamToBean(request, PayPojo.class);

        rulePojo rulePojo = ruleService.getRulePojo("金额");
        if(rulePojo.getIsOpen()==0){
            logger.info("限制金额已经关闭");
            return true;
        }

        Double startValue = rulePojo.getStartValue();
        Double endValue = rulePojo.getEndValue();
        Double payValue = payPojo.getPayValue();
        if(startValue<=payValue&payValue<endValue){
            return true;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(new R().valueIsNull("交易金额限制("+startValue+"<=value<"+endValue+"):"+payValue));
        logger.info("用户:" + payPojo.getUserId() + "交易金额限制("+startValue+"<=value<"+endValue+"):"+payValue);
        response.getWriter().println(s);
        return false;
    }
}
