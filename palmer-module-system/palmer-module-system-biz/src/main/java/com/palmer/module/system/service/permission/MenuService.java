package com.palmer.module.system.service.permission;

import java.util.List;

/**
 * @auther palmer
 * @date 2024-02-06
 */
public interface MenuService {
    List<Long> getMenuIdListByPermissionFromCache(String permission);
}
