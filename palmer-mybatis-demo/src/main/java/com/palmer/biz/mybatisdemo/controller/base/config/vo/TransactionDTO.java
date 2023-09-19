package com.palmer.biz.mybatisdemo.controller.base.config.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author palmer
 * @date 2023-09-04
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("测试事务转换实体")
public class TransactionDTO {
    @NotNull(message = "姓名不能为空")
    String name;
}
