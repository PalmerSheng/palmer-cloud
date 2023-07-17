package com.palmer.biz.mybatisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * config entity
 * @author palmer
 * @date 2023-04-25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(" 实体")
@TableName(value="config", autoResultMap = true)

public class ConfigDO {

    private static final long serialVersionUID = -1L;
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value="",example="")
    Long id;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value="",example="")
    MyConfigInterface config;
    @ApiModelProperty(value="",example="")
    String remark;

}

