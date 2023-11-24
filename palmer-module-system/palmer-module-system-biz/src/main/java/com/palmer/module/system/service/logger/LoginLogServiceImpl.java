package com.palmer.module.system.service.logger;

import com.palmer.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.palmer.module.system.convert.logger.LoginLogConvert;
import com.palmer.module.system.dal.dataobject.logger.LoginLogDO;
import com.palmer.module.system.dal.mysql.logger.LoginLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author palmer
 * @date 2023-11-24
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Resource
    private LoginLogMapper loginLogMapper;
    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO loginLog = LoginLogConvert.INSTANCE.convert(reqDTO);
        loginLogMapper.insert(loginLog);
    }
}
