package com.palmer.module.system.service.logger;

import com.palmer.module.system.api.logger.dto.LoginLogCreateReqDTO;

/**
 * @author palmer
 * @date 2023-11-24
 */
public interface LoginLogService {
    void createLoginLog(LoginLogCreateReqDTO reqDTO);

}
