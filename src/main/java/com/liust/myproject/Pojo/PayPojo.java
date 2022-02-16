package com.liust.myproject.Pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import sun.dc.pr.PRError;

import java.io.Serializable;
import java.util.Date;


//支付记录表
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("mypay")
public class PayPojo implements Serializable {
    //    支付记录id
    @TableId(type = IdType.AUTO)
    private Integer payId;
    //交易用户
    private Integer userId;
    //交易金额
    private Double payValue;
    //交易时间(数据库时间)
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", locale = "zh", timezone = "GMT+8")
    private Date payTime;
    //交易时间(客户支付时间)
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", locale = "zh", timezone = "GMT+8")
    private Date nowTime;
    //是否是支付(判断交易是扣钱还是加钱,1为扣钱,0为加钱)
    private Integer isPay;

}
