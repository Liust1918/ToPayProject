package com.liust.myproject.Controller;

import com.liust.myproject.MyStatusCode.MyHttpState;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.userPojo;
import com.liust.myproject.Service.userService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class userController {
    @Resource
    private userService userService;

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
        }
        userPojo.setCreateAt(new Date());
        userPojo.setIsOpen(0);
        userPojo.setRoleName("用户");

        String s = userService.userTransactional(userPojo);
        return R.setResult(s, MyHttpState.Successful_Run);
    }



}
