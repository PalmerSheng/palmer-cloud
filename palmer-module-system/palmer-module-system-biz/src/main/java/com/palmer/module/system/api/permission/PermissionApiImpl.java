package com.palmer.module.system.api.permission;

import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.module.system.service.permission.PermissionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.palmer.framework.common.pojo.CommonResult.success;
import static com.palmer.module.system.enums.ApiConstants.VERSION;

/**
 * @author palmer
 * @date 2024-01-22
 */
@RestController
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class PermissionApiImpl implements PermissionApi {

    @Resource
    private PermissionService permissionService;


    @Override
    public CommonResult<Boolean> hasAnyRoles(Long userId, String... roles) {
        return success(permissionService.hasAnyRoles(userId, roles));
    }

    @Override
    public CommonResult<Boolean> hasAnyPermissions(Long userId, String... permissions) {
        return success(permissionService.hasAnyPermissions(userId, permissions));
    }
}
