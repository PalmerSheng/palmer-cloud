package com.palmer.dynamic.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author palmer
 * @date 2023-07-17
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("用户实体")
@TableName("user")
public class UserDO {
    @TableId
    Long id;

    String username;


}
