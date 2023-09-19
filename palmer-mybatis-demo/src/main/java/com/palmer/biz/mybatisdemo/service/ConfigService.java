package com.palmer.biz.mybatisdemo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.palmer.biz.mybatisdemo.controller.base.config.vo.TransactionDTO;
import com.palmer.biz.mybatisdemo.entity.ConfigDO;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * @author palmer
 * @date 2023-04-25
 */
public interface ConfigService extends IService<ConfigDO> {


    void testTransaction(@Valid TransactionDTO transactionDTO);



}

