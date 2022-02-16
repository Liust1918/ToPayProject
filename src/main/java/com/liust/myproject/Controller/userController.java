package com.liust.myproject.Controller;

import com.liust.myproject.MyRedis.RedisToken;
import com.liust.myproject.MyStatusCode.MyHttpState;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.userPojo;
import com.liust.myproject.Service.userService;
import com.liust.myproject.Utils.JwtTokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class userController {
    @Resource
    private userService userService;

    @Resource
    private JwtTokenUtil JwtTokenUtil;

    @Resource
    private RedisToken redisToken;

    /**
     * 用户注册
     * @param userPojo
     * @return
     */
    @PostMapping("/register")
    public R insertUser(@RequestBody userPojo userPojo){
        boolean present = Optional.ofNullable(userPojo).isPresent();
        if(!present){
            //bean为空时候
            R.setResult("userPojo为空", MyHttpState.Value_Null);
        }

        userPojo user = userService.getUserByUserName(userPojo.getUsername());
        if(user!=null){
            //注册名重复
            return R.setResult("用户名已存在", MyHttpState.Successful_Run);
        }


        userPojo.setCreateAt(new Date());
        userPojo.setIsOpen(0);
        userPojo.setRoleName("用户");

        //将userid添加到token
        String s = userService.userTransactional(userPojo);
        String token = JwtTokenUtil.generateToken(s);

        //将这个token保存到redis
        redisToken.setTokenToRedis(userPojo.getUsername(), token);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("token", token);
        return R.setResult(map, MyHttpState.Successful_Run);
    }

    @GetMapping("/login")
    public R login(String username,String password){

        userPojo user = userService.getUserByUserName(username);
        if(user==null){
          return R.setResult("用户不存在", MyHttpState.Value_Null);
        }

        if(!user.getPassword().equals(password)){
            //密码或者用户名错误
            return  R.setResult("密码或者用户名错误", MyHttpState.Login_fail);
        }
        String token="";
        String tokenFromRedis = redisToken.getTokenFromRedis(username);
        if(tokenFromRedis==""){
            token=tokenFromRedis;
        }else{
            token= JwtTokenUtil.generateToken(String.valueOf(user.getUserId()));
            redisToken.setTokenToRedis(username,token);
        }

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("token", token);
        return R.setResult(map, MyHttpState.Login_Successful);
    }

}
