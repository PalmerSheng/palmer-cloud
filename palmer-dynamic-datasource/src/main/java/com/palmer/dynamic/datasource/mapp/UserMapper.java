package com.palmer.dynamic.datasource.mapp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.palmer.dynamic.datasource.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author palmer
 * @date 2023-07-17
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
