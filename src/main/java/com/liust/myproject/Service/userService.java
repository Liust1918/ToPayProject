package com.liust.myproject.Service;

import com.liust.myproject.Dao.userRepository;
import com.liust.myproject.Pojo.userPojo;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class userService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private userRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public String userTransactional(userPojo userPojo) {
        logger.info("插入新用户:"+userPojo.toString());
        int insert = userRepository.insert(userPojo);
        return String.valueOf(userPojo.getUserId());
    }

    public userPojo getUserById(Integer user_id){
        userPojo userPojo = userRepository.selectById(user_id);
        return userPojo;
    }
}
