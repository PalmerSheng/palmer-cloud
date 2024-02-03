package com.palmer.module.system.service.permission;

import com.palmer.module.system.dal.dataobject.permission.RoleDO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service 接口
 *
 * @author 芋道源码
 */
public interface RoleService {

    /**
     * 初始化角色的本地缓存
     */
    void initLocalCache();


    /**
     * 获得角色，从缓存中
     *
     * @param id 角色编号
     * @return 角色
     */
    RoleDO getRoleFromCache(Long id);
    /**
     * 判断角色数组中，是否有超级管理员
     *
     * @param roleList 角色数组
     * @return 是否有管理员
     */
    boolean hasAnySuperAdmin(Collection<RoleDO> roleList);


    /**
     * 判断角色编号数组中，是否有管理员
     *
     * @param ids 角色编号数组
     * @return 是否有管理员
     */
    default boolean hasAnySuperAdmin(Set<Long> ids) {
        return hasAnySuperAdmin(getRoleListFromCache(ids));
    }
    /**
     * 获得角色数组，从缓存中
     *
     * @param ids 角色编号数组
     * @return 角色数组
     */
    List<RoleDO> getRoleListFromCache(Collection<Long> ids);

}
