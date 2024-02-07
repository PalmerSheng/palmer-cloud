package com.palmer.module.infra.controller.admin.codegen;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.framework.common.util.collection.CollectionUtils;
import com.palmer.framework.common.util.servlet.ServletUtils;
import com.palmer.module.infra.service.codegen.CodeGenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.palmer.framework.common.pojo.CommonResult.success;

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

    @Operation(summary = "生成代码")
    @GetMapping("test")
    public CommonResult<Map<String, String>> genCode() {
        return success(codeGenService.generationCodes(156L));
    }

    @Operation(summary = "下载压缩包")
    @GetMapping("test1")
    public void genCode1(HttpServletResponse response) throws IOException {
        Map<String, String> codes = codeGenService.generationCodes(157L);
        // 构建 zip 包
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 输出
        ServletUtils.writeAttachment(response, "codegen.zip", outputStream.toByteArray());


    }

    @Operation(summary = "预览生成代码")
    @GetMapping("preview")
    public CommonResult<List<CodegenPreviewRespVO>> preview() {
        Map<String, String> codes = codeGenService.generationCodes(157L);
        // 转成对象
        return success(
                CollectionUtils.convertList(codes.entrySet(),
                        entry -> new CodegenPreviewRespVO().setFilePath(entry.getKey()).setCode(entry.getValue()))
        );
    }


    @Operation(summary = "生成文件到本地")
    @GetMapping("down/{tableId}")
    public CommonResult<Void> genLocalFile(@PathVariable("tableId") Long tableId) {
        Map<String, String> codes = codeGenService.generationCodes(tableId);
        codes.forEach((path, code) -> {
            FileUtil.writeUtf8String(code, "F:\\project\\palmer\\palmer-cloud\\" + path);
        });
        return success(null);
    }

}
