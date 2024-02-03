package com.palmer.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Sets;
import com.palmer.framework.common.enums.CommonStatusEnum;
import com.palmer.framework.common.util.collection.CollectionUtils;
import com.palmer.module.system.dal.dataobject.permission.RoleDO;
import com.palmer.module.system.dal.dataobject.permission.UserRoleDO;
import com.palmer.module.system.dal.mysql.permission.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static com.palmer.framework.common.util.collection.CollectionUtils.convertSet;
import static java.util.Collections.singleton;

/**
 * @author palmer
 * @date 2024-01-22
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private volatile Map<Long, Set<Long>> userRoleCache;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleService roleService;

    @Override
    @PostConstruct
    public void initLocalCache() {
        initLocalCacheForUserRole();
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        Set<Long> roleIds = getUserRoleIdsFromCache(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }
        Set<String> userRoles = convertSet(roleService.getRoleListFromCache(roleIds),
                RoleDO::getCode);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
    }


    @Override
    public Set<Long> getUserRoleIdsFromCache(Long userId, Collection<Integer> roleStatuses) {
        Set<Long> cacheRoleIds = userRoleCache.get(userId);
        // 创建用户的时候没有分配角色，会存在空指针异常
        if (CollUtil.isEmpty(cacheRoleIds)) {
            return Collections.emptySet();
        }
        Set<Long> roleIds = new HashSet<>(cacheRoleIds);
        // 过滤角色状态
        if (CollectionUtil.isNotEmpty(roleStatuses)) {
            roleIds.removeIf(roleId -> {
                RoleDO role = roleService.getRoleFromCache(roleId);
                return role == null || !roleStatuses.contains(role.getStatus());
            });
        }
        return roleIds;
    }

    void initLocalCacheForUserRole() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        // 第一步：加载数据
        List<UserRoleDO> userRoles = userRoleMapper.selectList();
        log.info("[initLocalCacheForUserRole][缓存用户与角色，数量为:{}]", userRoles.size());

        // 第二步：构建缓存。
        ImmutableMultimap.Builder<Long, Long> userRoleCacheBuilder = ImmutableMultimap.builder();
        userRoles.forEach(userRoleDO -> userRoleCacheBuilder.put(userRoleDO.getUserId(), userRoleDO.getRoleId()));
        userRoleCache = CollectionUtils.convertMultiMap2(userRoles, UserRoleDO::getUserId, UserRoleDO::getRoleId);
    }



}
