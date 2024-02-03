package com.palmer.module.system.service.permission;

import cn.hutool.core.collection.CollectionUtil;
import com.palmer.module.system.dal.dataobject.permission.RoleDO;
import com.palmer.module.system.dal.mysql.permission.RoleMapper;
import com.palmer.module.system.enums.permission.RoleCodeEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.palmer.framework.common.util.collection.CollectionUtils.convertMap;


/**
 * 角色 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    /**
     * 角色缓存
     * key：角色编号 {@link RoleDO#getId()}
     *
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, RoleDO> roleCache;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleMapper roleMapper;


    /**
     * 初始化 {@link #roleCache} 缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {
        // 注意：忽略自动多租户，因为要全局初始化缓存
            // 第一步：查询数据
            List<RoleDO> roleList = roleMapper.selectList();
            log.info("[initLocalCache][缓存角色，数量为:{}]", roleList.size());

            // 第二步：构建缓存
            roleCache = convertMap(roleList, RoleDO::getId);
    }

    @Override
    public RoleDO getRoleFromCache(Long id) {
        return roleCache.get(id);
    }


    @Override
    public boolean hasAnySuperAdmin(Collection<RoleDO> roleList) {
        if (CollectionUtil.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> RoleCodeEnum.isSuperAdmin(role.getCode()));
    }
}
