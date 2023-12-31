package com.palmer.biz.mybatisdemo.controller.base.config;


import cn.hutool.json.JSONUtil;
import com.palmer.biz.mybatisdemo.controller.base.config.vo.TransactionDTO;
import com.palmer.biz.mybatisdemo.controller.base.config.vo.req.TestDateReqDate;
import com.palmer.biz.mybatisdemo.entity.ConfigDO;
import com.palmer.biz.mybatisdemo.entity.MyConfig;
import com.palmer.biz.mybatisdemo.entity.MyConfig2;
import com.palmer.biz.mybatisdemo.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * (Config)表控制层
 *
 * @author palmer
 * @since 2023-07-07 13:08:47
 */

@Slf4j
@RestController
@RequestMapping("/config")
@Api(tags = "config")
public class ConfigController {
    @Resource
    private ConfigService configService;
    @GetMapping("/list")
    public List<ConfigDO> list() {
        List<ConfigDO> list = configService.lambdaQuery().eq(ConfigDO::getId,1L).list();

        return list;
    }
    @GetMapping("/add")
    public String  add() {
        ConfigDO configDO = new ConfigDO();
        configDO.setRemark("remark");
        MyConfig myConfig = JSONUtil.toBean(JSONUtil.toJsonStr(new MyConfig()), MyConfig.class);
        configDO.setConfig(myConfig);
        configService.save(configDO);


        ConfigDO configDO2 = new ConfigDO();
        configDO2.setRemark("remark");
        MyConfig2 myConfig2 = JSONUtil.toBean(JSONUtil.toJsonStr(new MyConfig2()), MyConfig2.class);
        configDO2.setConfig(myConfig2);
        configService.save(configDO2);
        return "success";
    }

    @GetMapping("/testTransaction")
    public String  testTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        configService.testTransaction(transactionDTO);
        return "success";
    }

    @GetMapping("/testDate2")
    @ApiOperation("测试日期")
    public String  testDate(TestDateReqDate date) {
        System.out.println(date);
        return "success";
    }
}


