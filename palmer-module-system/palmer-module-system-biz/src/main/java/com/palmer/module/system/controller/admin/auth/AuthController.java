package com.palmer.module.system.controller.admin.auth;

import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import com.palmer.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import java.time.LocalDateTime;

import static com.palmer.framework.common.pojo.CommonResult.success;

/**
 * @author palmer
 * @date 2023-11-15
 */
@RestController
@RequestMapping("/system/auth")
public class AuthController {
    @PostMapping("/login")
    @PermitAll
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        AuthLoginRespVO o = new AuthLoginRespVO(1L, "accessToken", "refreshToken", LocalDateTime.now());
        return success(o);
    }

    @GetMapping("/test")
    public CommonResult<String> testAuth() {
        return success("testAuth");
    }

}
