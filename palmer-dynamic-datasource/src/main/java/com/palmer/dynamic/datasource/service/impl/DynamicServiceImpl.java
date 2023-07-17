package com.palmer.dynamic.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.palmer.dynamic.datasource.config.DataSourceTypeEnum;
import com.palmer.dynamic.datasource.config.DynamicSourceUtil;
import com.palmer.dynamic.datasource.entity.UserDO;
import com.palmer.dynamic.datasource.mapp.UserMapper;
import com.palmer.dynamic.datasource.service.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author palmer
 * @date 2023-07-17
 */
@Service
@Slf4j
public class DynamicServiceImpl implements DynamicService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test() {
        DynamicSourceUtil.switchDataSource(DataSourceTypeEnum.db1);
        List<UserDO> userDOS = userMapper.selectList(new QueryWrapper<>());
        log.info("{}", userDOS);
        DynamicSourceUtil.switchDataSource(DataSourceTypeEnum.db2);
        List<UserDO> userDOS2 = userMapper.selectList(new QueryWrapper<>());
        log.info("{}", userDOS2);
        DynamicSourceUtil.switchDataSource(DataSourceTypeEnum.db3);
        List<UserDO> userDOS3 = userMapper.selectList(new QueryWrapper<>());
        log.info("{}", userDOS3);
    }
}
