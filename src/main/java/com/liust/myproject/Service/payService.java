package com.liust.myproject.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liust.myproject.Dao.payRepository;
import com.liust.myproject.Pojo.PayPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuyulong
 * @create 2022-02-15 10:33
 * @create 2022-二月  星期二
 * @project MyProject
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class payService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private payRepository payRepository;

    /**
     * 支付
     * @param payPojo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String toPayTransactional(PayPojo payPojo){
        Integer userId = payPojo.getUserId();
        Double allValue = getAllValue(userId);
        Double payValue = payPojo.getPayValue();


        double all = allValue - payValue;
        if(all<0){
            logger.info("用户:"+payPojo.getUserId()+"余额不足");
            throw new RuntimeException("用户:"+payPojo.getUserId()+"余额不足");
        }

        int insert = payRepository.insert(payPojo);
        return String.valueOf(payPojo.getPayId());
    }


    /**
     * 存钱
     * @param payPojo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String toSaveTransactional(PayPojo payPojo){
        int insert = payRepository.insert(payPojo);
        return String.valueOf(payPojo.getPayId());
    }


    /**
     * 统计用户余额
     * @param user_id
     * @return
     */
    public Double getAllValue(Integer user_id) {
        Double PayOfValue = getALLSaveOrPayOfValue(user_id, true);
        Double SaveOfValue = getALLSaveOrPayOfValue(user_id, false);
        Double value=SaveOfValue-PayOfValue;
        return value;
    }


    /**
     * 统计扣钱/加钱的统计
     * true为扣钱统计，false为加钱统计
     * @param user_id
     * @param isPay
     * @return
     */
    public Double getALLSaveOrPayOfValue(Integer user_id,Boolean isPay){
        QueryWrapper<PayPojo> queryWrapper= new QueryWrapper<PayPojo>();
        queryWrapper.eq("user_id", user_id);
        queryWrapper.eq("is_pay", isPay==true?0:1);
        List<PayPojo> payPojos = payRepository.selectList(queryWrapper);
        Double collect = payPojos.stream().collect(Collectors.summingDouble(value -> value.getPayValue()));
        return collect;
    }


}
