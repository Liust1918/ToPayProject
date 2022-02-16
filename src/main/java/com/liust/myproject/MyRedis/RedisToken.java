package com.liust.myproject.MyRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;



/**
 * 把token保持在redis
 */
@Component
public class RedisToken {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

//    @Value("${redis.timeout}")
//    private String RedisTimeOut;
    private Long RedisTimeOut=1L*60*60*60*7;

    private static final String TOKEN_KEY = "token_pay_";

    /**
     * 把token保存在redis里面中
     * @param username
     * @param token
     */
    public  void setTokenToRedis(String username,String token) {
        redisTemplate.opsForValue().set(TOKEN_KEY+username, token);
    }


    /**
     * 从redis获得token
     * @param username
     * @return
     */
    public  String getTokenFromRedis(String username){
     return  redisTemplate.opsForValue().get(TOKEN_KEY+username);
    }

}
