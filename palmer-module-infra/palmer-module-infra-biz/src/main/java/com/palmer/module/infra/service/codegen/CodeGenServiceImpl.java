package com.palmer.module.infra.service.codegen;

import com.palmer.framework.common.util.check.CheckUtil;
import com.palmer.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import com.palmer.module.infra.dal.dataobject.codegen.CodegenTableDO;
import com.palmer.module.infra.dal.mysql.codegen.CodegenColumnMapper;
import com.palmer.module.infra.dal.mysql.codegen.CodegenTableMapper;
import com.palmer.module.infra.service.codegen.inner.CodegenEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.palmer.module.infra.enums.ErrorOrderConstants.CODEGEN_COLUMN_NOT_EXISTS;
import static com.palmer.module.infra.enums.ErrorOrderConstants.CODEGEN_TABLE_NOT_EXISTS;

/**
 * @auther palmer
 * @date 2024-02-06
 */
@Slf4j
@Service
public class CodeGenServiceImpl implements CodeGenService {
    @Resource
    private CodegenTableMapper codegenTableMapper;

    @Resource
    private CodegenColumnMapper codegenColumnMapper;

    @Resource
    private CodegenEngine codegenEngine;

    @Override
    public Map<String, String> generationCodes(Long tableId) {
        // 校验表存在
        CodegenTableDO table = codegenTableMapper.selectById(tableId);
        CheckUtil.notNull(table, CODEGEN_TABLE_NOT_EXISTS);

        List<CodegenColumnDO> columns = codegenColumnMapper.selectListByTableId(tableId);
        CheckUtil.notEmpty(columns, CODEGEN_COLUMN_NOT_EXISTS);
        // 生成代码
        return codegenEngine.genCode(table, columns);
    }
}
