package com.palmer.module.infra.controller.admin.codegen;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.framework.common.util.servlet.ServletUtils;
import com.palmer.module.infra.service.codegen.CodeGenService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author palmer
 * @date 2023-07-20
 */
//@Tag(name =  "管理后台 - 代码生成器")
@RestController
@RequestMapping("/infra/codegen")
@Validated
public class CodeGenController {

    @Resource
    private CodeGenService codeGenService;

    @GetMapping("test")
    public CommonResult<Map<String, String>> genCode() {
        return CommonResult.success(codeGenService.generationCodes(156L));
    }


    @GetMapping("test1")
    public void genCode1(HttpServletResponse response) throws IOException {
        Map<String, String> codes = codeGenService.generationCodes(156L);
        // 构建 zip 包
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 输出
        ServletUtils.writeAttachment(response, "codegen.zip", outputStream.toByteArray());


    }

}
