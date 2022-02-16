package com.liust.myproject.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liust.myproject.Dao.ruleRepository;
import com.liust.myproject.Pojo.rulePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;


@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class ruleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ruleRepository ruleRepository;

    @Transactional(rollbackFor = Exception.class)
    public Integer insertRule(rulePojo rulePojo){
        int insert = ruleRepository.insert(rulePojo);
        return rulePojo.getRuleId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateRule(rulePojo rulePojo){
        QueryWrapper<rulePojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_name", rulePojo.getRuleName());
        int update = ruleRepository.update(rulePojo, queryWrapper);
        return rulePojo.getRuleId();
    }


    public rulePojo getRulePojo(String rule_name) {
        QueryWrapper<rulePojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_name", rule_name);
        rulePojo rulePojo = ruleRepository.selectOne(queryWrapper);
        return rulePojo;
    }

}
