package com.palmer.biz.mybatisdemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.palmer.biz.mybatisdemo.entity.ConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * config mapper
 * @author palmer
 * @date 2023-04-25
 */
@Mapper
public interface ConfigMapper extends BaseMapper<ConfigDO> {

}

