package com.palmer.module.system.controller.admin.auth;

import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import com.palmer.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.palmer.module.system.service.oauth.AdminAuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static com.palmer.framework.common.pojo.CommonResult.success;

/**
 * @author palmer
 * @date 2023-11-15
 */
@RestController
@RequestMapping("/system/auth")
public class AuthController {

    @Resource
    private AdminAuthService authService;

    @PostMapping("/login")
    @PermitAll
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        AuthLoginRespVO o =   authService.login(reqVO);
        return success(o);
    }

    @GetMapping("/test")
    public CommonResult<String> testAuth() {
        return success("testAuth");
    }

}
