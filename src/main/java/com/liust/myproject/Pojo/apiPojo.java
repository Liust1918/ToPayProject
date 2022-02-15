package com.liust.myproject.Pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyulong
 * @create 2022-02-15 1:00
 * @create 2022-二月  星期二
 * @project MyProject
 */
//api表
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("api")
public class apiPojo {
    //表id
    @TableId
    private Integer apiId;
    //访问方式(Post,GET,)
//    @EnumValue
    private MyMethod apiMethod;
    //访问路径
    private String apiUrl;
    //api描述
    private String description;
    //api名
    private String apiName;
    //角色名
    private String roleName;

    public  enum MyMethod{
        GET,POST,PUT,PATCH,DELETE
    }
}
