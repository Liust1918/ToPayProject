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


@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class payService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private payRepository payRepository;

    /**
     * 退款功能
     * @param payPojo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String toSaveTransactional(PayPojo payPojo){
        Integer userId = payPojo.getUserId();
        Double allValue = getAllValue(userId);
        Double saveValue = payPojo.getPayValue();
        double all = allValue - saveValue;
        if(all<0){
            logger.info("用户:"+payPojo.getUserId()+"余额不足");
            throw new RuntimeException("用户:"+payPojo.getUserId()+"余额不足");
        }
        int insert = payRepository.insert(payPojo);
        return String.valueOf(payPojo.getPayId());
    }


    /**
     * 支付功能
     * @param payPojo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String toPayTransactional(PayPojo payPojo){
        Integer userId = payPojo.getUserId();
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
        Double value=PayOfValue-SaveOfValue;
        return value;
    }


    /**
     * 统计扣钱/加钱的统计
     * true为支付统计，false为退款统计
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

    /**
     * 获得用户交易次数
     *
     * @return
     */
    public Long getPayTimes(Integer userId) {
        QueryWrapper<PayPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Long aLong = payRepository.selectCount(queryWrapper);
        return aLong;
    }


}
