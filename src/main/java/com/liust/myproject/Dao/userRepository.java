package com.liust.myproject.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liust.myproject.Pojo.userPojo;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author liuyulong
 * @create 2022-02-15 9:01
 * @create 2022-二月  星期二
 * @project MyProject
 */
@Mapper
public interface userRepository extends BaseMapper<userPojo> {
}
