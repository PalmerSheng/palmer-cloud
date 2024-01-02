package com.palmer.soucecode.springmybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author palmer
 * @date 2023-12-13
 */
@Mapper
public interface UserMapper {
    @Select("select 'user'")
    String getUser();

}
