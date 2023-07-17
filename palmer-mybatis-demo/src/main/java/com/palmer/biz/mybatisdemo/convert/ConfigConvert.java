package com.palmer.biz.mybatisdemo.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * 实体转换类
 * @author palmer
 * @date 2023-04-25
 */
@Mapper
public interface ConfigConvert {
    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);


}


