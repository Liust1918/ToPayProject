package com.liust.myproject.MyInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.PayPojo;
import com.liust.myproject.Pojo.rulePojo;
import com.liust.myproject.Service.payService;
import com.liust.myproject.Service.ruleService;
import com.liust.myproject.Utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liuyulong
 * @create 2022-02-17 1:22
 * @create 2022-二月  星期四
 * @project MyProject
 */
@Component
public class timesLimitInterceptor implements HandlerInterceptor {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ruleService ruleService;

    @Resource
    private payService payService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PayPojo payPojo = (PayPojo) JsonUtil.StreamToBean(request, PayPojo.class);

        rulePojo rulePojo = ruleService.getRulePojo("交易次数");
        if(rulePojo.getIsOpen()==0){
            logger.info("交易次数已经关闭");
            return true;
        }

        Integer times = payService.getPayTimes(payPojo.getUserId()).intValue();
        Integer limitTimes = rulePojo.getTimes();
        if(times<limitTimes){
            return true;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(new R().valueIsNull("限制交易次数("+times+"<"+limitTimes+"):"+times));
        logger.info("用户:" + payPojo.getUserId() + "限制交易次数("+times+"<"+limitTimes+")");
        response.getWriter().println(s);
        return false;
    }
}
