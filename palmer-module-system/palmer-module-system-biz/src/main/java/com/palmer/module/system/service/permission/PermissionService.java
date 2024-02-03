package com.palmer.module.system.service.permission;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Set;

/**
 * 权限 Service 接口
 *
 * 提供用户-角色、角色-菜单、角色-部门的关联权限处理
 *
 * @author 芋道源码
 */
public interface PermissionService {

    /**
     * 初始化权限的本地缓存
     */
    void initLocalCache();

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles 角色数组
     * @return 是否
     */
    boolean hasAnyRoles(Long userId, String... roles);


    /**
     * 获得用户拥有的角色编号集合，从缓存中获取
     *
     * @param userId 用户编号
     * @param roleStatuses 角色状态集合. 允许为空，为空时不过滤
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIdsFromCache(Long userId, @Nullable Collection<Integer> roleStatuses);



}
