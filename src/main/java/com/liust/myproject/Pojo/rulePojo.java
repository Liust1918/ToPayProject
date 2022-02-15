package com.liust.myproject.Pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liuyulong
 * @create 2022-02-15 1:00
 * @create 2022-二月  星期二
 * @project MyProject
 */
//拦截规则表
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rule")
public class rulePojo {
    //表id
    @TableId
    private Integer ruleId;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
    //    开始范围
    private Double startValue;
    //   结束范围
    private Double endValue;
    // 次数
    private Integer times;
    //限制时间(开始)
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", locale = "zh", timezone = "GMT+8")
    private Date limitTimeStart;
    //限制时间(结束)
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", locale = "zh", timezone = "GMT+8")
    private Date limitTimeEnd;
    //规则描述
    private String ruleName;
    //是否开启(1为开启)
    private Integer isOpen;

}
