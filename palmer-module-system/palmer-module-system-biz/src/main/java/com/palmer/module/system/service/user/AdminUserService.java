package com.palmer.module.system.service.user;

import com.palmer.module.system.dal.dataobject.user.AdminUserDO;

/**
 * @author palmer
 * @date 2023-11-24
 */
public interface AdminUserService {
    AdminUserDO getUserByUsername(String username);

    void updateUserLogin(Long userId, String clientIP);

    boolean isPasswordMatch(String rawPassword, String encodedPassword);
}
