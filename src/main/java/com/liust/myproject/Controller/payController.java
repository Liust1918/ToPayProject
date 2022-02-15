package com.liust.myproject.Controller;

import com.liust.myproject.Dao.payRepository;
import com.liust.myproject.MyStatusCode.MyHttpState;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.PayPojo;
import com.liust.myproject.Service.payService;
import com.mysql.cj.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author liuyulong
 * @create 2022-02-15 9:04
 * @create 2022-二月  星期二
 * @project MyProject
 */
@RestController
@RequestMapping("/pay")
public class payController {

    @Resource
    private payService payService;

    @PostMapping("/save")
    public R toSavePay(@RequestBody PayPojo payPojo){
        payPojo.setPayTime(new Date());
        Integer isPay = payPojo.getIsPay();
        String msg;
//        1为扣钱
        if(isPay.equals(0)){
            msg=payService.toPayTransactional(payPojo);
//        0为存钱
        }else if(isPay.equals(1)){
            msg=payService.toSaveTransactional(payPojo);
        }else{
            msg="别乱发数字";
        }
        R result = R.setResult(msg, MyHttpState.Successful_Run);
        return result;
    }
}
