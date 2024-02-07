package com.palmer.module.system.service.permission;

import com.palmer.module.system.dal.dataobject.permission.MenuDO;
import com.palmer.module.system.dal.mysql.permission.MenuMapper;
import com.palmer.module.system.dal.redis.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.palmer.framework.common.util.collection.CollectionUtils.convertList;

/**
 * @auther palmer
 * @date 2024-02-06
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Override
    @Cacheable(value = RedisKeyConstants.PERMISSION_MENU_ID_LIST, key = "#permission")
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<MenuDO> menus = menuMapper.selectListByPermission(permission);
        return convertList(menus, MenuDO::getId);
    }
}
