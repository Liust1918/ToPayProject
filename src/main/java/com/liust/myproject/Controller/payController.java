package com.liust.myproject.Controller;

import com.liust.myproject.MyFiter.RequestWrapper;
import com.liust.myproject.MyStatusCode.MyHttpState;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.PayPojo;
import com.liust.myproject.Service.payService;
import com.liust.myproject.Utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;


@RestController
@RequestMapping("/pay")
public class payController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private payService payService;

    @PostMapping("/save")
    public R toSavePay(HttpServletRequest request) throws IOException {
        PayPojo payPojo = (PayPojo)JsonUtil.StreamToBean(request, PayPojo.class);
        payPojo.setPayTime(new Date());
        Integer isPay = payPojo.getIsPay();
        String msg;
//        0为支付
        if(isPay.equals(0)){
            msg=payService.toPayTransactional(payPojo);
        }
        else{
            msg="isPay无效";
        }
        R result = R.setResult(msg, MyHttpState.Successful_Run);
        return result;
    }
}
