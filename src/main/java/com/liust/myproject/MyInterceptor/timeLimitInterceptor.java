package com.liust.myproject.MyInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liust.myproject.Dao.ruleRepository;
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
 * 交易时间限制拦截(2)
 */
@Component
public class timeLimitInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ruleService ruleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PayPojo payPojo = (PayPojo) JsonUtil.StreamToBean(request, PayPojo.class);

        rulePojo rulePojo = ruleService.getRulePojo("交易时间");
        if(rulePojo.getIsOpen()==0){
            logger.info("限制交易时间已经关闭");
            return true;
        }
        Date limitTimeStart = rulePojo.getLimitTimeStart();
        Date limitTimeEnd = rulePojo.getLimitTimeEnd();
        Date date = new Date();
        boolean before = date.after(limitTimeStart);
        boolean after = date.before(limitTimeEnd);
        if(before&after){
            return true;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(new R().valueIsNull("不在交易时间"));
        logger.info("用户:" + payPojo.getUserId() + " 不在交易时间");
        response.getWriter().println(s);
        return false;
    }
}
