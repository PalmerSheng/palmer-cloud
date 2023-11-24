package com.palmer.module.system.service.oauth;

import com.palmer.framework.common.enums.CommonStatusEnum;
import com.palmer.framework.common.enums.UserTypeEnum;
import com.palmer.framework.common.util.monitor.TracerUtils;
import com.palmer.framework.common.util.servlet.ServletUtils;
import com.palmer.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.palmer.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import com.palmer.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.palmer.module.system.convert.auth.AuthConvert;
import com.palmer.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.palmer.module.system.dal.dataobject.user.AdminUserDO;
import com.palmer.module.system.enums.logger.LoginLogTypeEnum;
import com.palmer.module.system.enums.logger.LoginResultEnum;
import com.palmer.module.system.enums.oauth2.OAuth2ClientConstants;
import com.palmer.module.system.service.logger.LoginLogService;
import com.palmer.module.system.service.oauth2.OAuth2TokenService;
import com.palmer.module.system.service.user.AdminUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.palmer.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.palmer.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static com.palmer.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;

/**
 * @author palmer
 * @date 2023-11-24
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {
    @Resource
    private AdminUserService userService;

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private OAuth2TokenService oauth2TokenService;


    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 校验验证码
        validateCaptcha();

        AdminUserDO user =  authenticate(reqVO.getUsername(), reqVO.getPassword());


        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);


//        AuthLoginRespVO o = new AuthLoginRespVO(1L, "accessToken", "refreshToken", LocalDateTime.now());
//        return o;
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }

        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private void validateCaptcha() {
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }


    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder(4).encode("123456"));
    }
}
