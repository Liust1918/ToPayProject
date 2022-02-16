package com.liust.myproject.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liust.myproject.Dao.ruleRepository;
import com.liust.myproject.MyStatusCode.MyHttpState;
import com.liust.myproject.MyStatusCode.R;
import com.liust.myproject.Pojo.rulePojo;
import com.liust.myproject.Service.ruleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 过滤规则
 */
@RestController
@RequestMapping("/rule")
public class ruleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ruleService ruleService;

    @Resource
    private ruleRepository ruleRepository;

    /**
     * 插入拦截规则
     * @param rulePojo
     * @return
     */
    @PostMapping("/save")
    public R insertRule(@RequestBody rulePojo rulePojo) {
        rulePojo.setCreateTime(new Date());
        Integer integer = ruleService.insertRule(rulePojo);
        return R.setResult(integer, MyHttpState.Successful_Run);
    }

    /**
     * 修改拦截规则
     * @param rulePojo
     * @return
     */
    @PatchMapping("/update")
    public R updateRule(@RequestBody rulePojo rulePojo){
        boolean present = Optional.ofNullable(rulePojo).isPresent();
        if(!present){
            //对象为空
            return R.valueIsNull("过滤规则不能为空");
        }
        Integer integer = ruleService.updateRule(rulePojo);
        return R.setResult(integer,MyHttpState.Successful_Run);

    }

    /**
     * 获得特定规则
     * @param rule_name
     * @return
     */
    @GetMapping("/get")
    public R getRule(String rule_name){
        rulePojo rulePojo = ruleService.getRulePojo(rule_name);
        if(rule_name==""||rule_name==null){
           return R.setResult("数据不能为空", MyHttpState.Value_Null);
        }

        return R.setResult(rulePojo, MyHttpState.Successful_Run);
    }


    /**
     * 获得所有规则名
     * @return
     */
    @GetMapping("/getAllRuleName")
    public R getAllRuleName(){
        QueryWrapper<rulePojo> queryWrapper = new QueryWrapper<>();
        List<rulePojo> rulePojos = ruleRepository.selectList(queryWrapper);
        List<String> collect = rulePojos.stream().map(rulePojo::getRuleName).collect(Collectors.toList());
        return R.setResult(collect, MyHttpState.Successful_Run);
    }

}
