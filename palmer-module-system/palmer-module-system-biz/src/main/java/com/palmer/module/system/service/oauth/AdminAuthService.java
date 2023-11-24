package com.palmer.module.system.service.oauth;

import com.palmer.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import com.palmer.module.system.controller.admin.auth.vo.AuthLoginRespVO;

/**
 * @author palmer
 * @date 2023-11-24
 */
public interface AdminAuthService {
    AuthLoginRespVO login( AuthLoginReqVO reqVO);
}
