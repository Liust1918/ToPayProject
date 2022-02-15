package com.liust.myproject.Pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liuyulong
 * @create 2022-02-15 0:59
 * @create 2022-二月  星期二
 * @project MyProject
 */
//用户表
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class userPojo {
    //用户id
    @TableId
    private Integer userId;
    //用户创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", locale = "zh", timezone = "GMT+8")
    private Date createAt;
    //用户密码
    @JsonIgnore
    private String password;
    //用户名
    private String username;
    //用户描述
    private String description;
    //角色名
    private String roleName;
    //黑白名单(默认为0)
    private Integer isOpen;

}
