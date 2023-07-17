package com.palmer.biz.mybatisdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.palmer.biz.mybatisdemo.entity.ConfigDO;
import com.palmer.biz.mybatisdemo.mapper.ConfigMapper;
import com.palmer.biz.mybatisdemo.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}

