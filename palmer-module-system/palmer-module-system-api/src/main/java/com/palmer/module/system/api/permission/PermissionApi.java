package com.palmer.module.system.api.permission;

/**
 * @author palmer
 * @date 2024-01-22
 */

import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
public interface PermissionApi
{
    String PREFIX = ApiConstants.PREFIX + "/permission";


    @GetMapping(PREFIX + "/has-any-roles")
    @Operation(summary = "判断是否有角色，任一一个即可")
    @Parameters({
            @Parameter(name = "userId", description = "用户编号", example = "1", required = true),
            @Parameter(name = "roles", description = "角色数组", example = "2", required = true)
    })
    CommonResult<Boolean> hasAnyRoles(@RequestParam("userId") Long userId,
                                      @RequestParam("roles") String... roles);

}
