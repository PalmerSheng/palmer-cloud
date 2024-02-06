package com.palmer.module.infra.enums;

import com.palmer.framework.common.exception.ErrorCode;

/**
 * Infra 错误码枚举类
 * infra 系统，使用 1-001-000-000 段
 *
 * @auther palmer
 * @date 2024-02-06
 */
public interface ErrorOrderConstants {

    // ========== 代码生成器 1-001-004-000 ==========
    ErrorCode CODEGEN_TABLE_EXISTS = new ErrorCode(1_003_001_000, "表定义已经存在");
    ErrorCode CODEGEN_IMPORT_TABLE_NULL = new ErrorCode(1_003_001_001, "导入的表不存在");
    ErrorCode CODEGEN_IMPORT_COLUMNS_NULL = new ErrorCode(1_003_001_002, "导入的字段不存在");
    ErrorCode CODEGEN_TABLE_NOT_EXISTS = new ErrorCode(1_003_001_004, "表定义不存在");
    ErrorCode CODEGEN_COLUMN_NOT_EXISTS = new ErrorCode(1_003_001_005, "字段义不存在");
}
