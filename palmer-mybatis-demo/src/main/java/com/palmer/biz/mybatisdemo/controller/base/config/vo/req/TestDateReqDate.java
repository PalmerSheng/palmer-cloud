package com.palmer.biz.mybatisdemo.controller.base.config.vo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author palmer
 * @date 2023-09-05
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("测试日期")
public class TestDateReqDate {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("日期")
    Date date;


}
