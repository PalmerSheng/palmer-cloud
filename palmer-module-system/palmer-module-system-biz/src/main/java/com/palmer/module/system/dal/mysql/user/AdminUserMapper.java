package com.palmer.module.system.dal.mysql.user;

import com.palmer.framework.mybatis.mybatis.core.mapper.BaseMapperX;
import com.palmer.module.system.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author palmer
 * @date 2023-11-24
 */
@Mapper
public interface AdminUserMapper extends BaseMapperX<AdminUserDO> {
     default  AdminUserDO selectByUsername(String username){
         return selectOne(AdminUserDO::getUsername, username);
    }
}
