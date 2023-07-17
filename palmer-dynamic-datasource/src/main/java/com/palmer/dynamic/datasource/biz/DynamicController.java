package com.palmer.dynamic.datasource.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.palmer.dynamic.datasource.config.DataSourceTypeEnum;
import com.palmer.dynamic.datasource.config.DynamicSourceUtil;
import com.palmer.dynamic.datasource.entity.UserDO;
import com.palmer.dynamic.datasource.mapp.UserMapper;
import com.palmer.dynamic.datasource.service.DynamicService;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author palmer
 * @date 2023-07-17
 */
@RestController
@RequestMapping("dynamic")
@Slf4j
public class DynamicController {
    @Resource
    private DynamicService dynamicService ;

    @GetMapping("test")
    public  Object test() {
        dynamicService.test();

        return "success";
    }
}
