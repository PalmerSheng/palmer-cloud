package com.palmer.module.infra.service.codegen;

import java.util.Map;

/**
 * 代码生成 Service 接口
 *
 * @auther palmer
 * @date 2024-02-06
 */
public interface CodeGenService {
    /**
     * 生成制定表代码
     *
     * @param tableId
     * @return
     */
    Map<String, String> generationCodes(Long tableId);
}
