package com.palmer.biz.mybatisdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.palmer.biz.mybatisdemo.controller.base.config.vo.TransactionDTO;
import com.palmer.biz.mybatisdemo.entity.ConfigDO;
import com.palmer.biz.mybatisdemo.mapper.ConfigMapper;
import com.palmer.biz.mybatisdemo.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author palmer
 * @date 2023-04-25
 */
@Slf4j
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, ConfigDO> implements ConfigService {

    public static void main(String[] args) {
        System.out.println("0.12%".substring(0, "0.12%".length() - 1));
    }


    @Override
    public void testTransaction(TransactionDTO transactionDTO) {
        ConfigDO configDO = new ConfigDO();
        configDO.setConfig(null);
        configDO.setRemark("1");
        this.save(configDO);
        childTest();
    }

    @Transactional(rollbackFor = Exception.class)
    public void childTest()  {
        boolean flag = true;
        // 存入b1
        ConfigDO configDO = new ConfigDO();
        configDO.setConfig(null);
        configDO.setRemark("2");
        this.save(configDO);
        if (flag) {
            throw new RuntimeException("1235");
        }
        // 存入 b2
        ConfigDO configDO2 = new ConfigDO();
        configDO2.setConfig(null);
        configDO2.setRemark("3");
        this.save(configDO2);
    }

}

