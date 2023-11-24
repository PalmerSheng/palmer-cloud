package com.palmer.module.system.service.user;

import com.palmer.module.system.dal.dataobject.user.AdminUserDO;
import com.palmer.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author palmer
 * @date 2023-11-24
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        userMapper.updateById(new AdminUserDO().setId(id).setLoginIp(loginIp).setLoginDate(LocalDateTime.now()));
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
