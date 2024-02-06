package com.palmer.module.infra.service.codegen.inner;

import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import com.palmer.framework.common.exception.util.ServiceExceptionUtil;
import com.palmer.framework.common.pojo.CommonResult;
import com.palmer.framework.common.pojo.PageParam;
import com.palmer.framework.common.pojo.PageResult;
import com.palmer.framework.common.util.collection.CollectionUtils;
import com.palmer.framework.common.util.date.DateUtils;
import com.palmer.framework.common.util.date.LocalDateTimeUtils;
import com.palmer.framework.excel.core.utils.ExcelUtils;
import com.palmer.framework.mybatis.mybatis.core.dataobject.BaseDO;
import com.palmer.framework.mybatis.mybatis.core.mapper.BaseMapperX;
import com.palmer.framework.mybatis.mybatis.core.query.LambdaQueryWrapperX;
import com.palmer.framework.operatorlog.core.annotations.OperateLog;
import com.palmer.framework.operatorlog.core.enums.OperateTypeEnum;
import com.palmer.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import com.palmer.module.infra.dal.dataobject.codegen.CodegenTableDO;
import com.palmer.module.infra.enums.CodegenSceneEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.text.CharSequenceUtil.*;

/**
 * 代码生成的引擎，用于具体生成代码
 *
 * @author palmer
 * @date 2024-01-16
 */
@Component
public class CodegenEngine {
    private final Map<String, Object> baseVariables = new HashMap<>();


    @PostConstruct
    void initGlobalBindingMap() {
        // 全局配置
        baseVariables.put("basePackage", "com.palmer");

        baseVariables.put("OperateLogClassName", OperateLog.class.getName());
        baseVariables.put("OperateTypeEnumClassName", OperateTypeEnum.class.getName());

        // 全局 Java Bean
        baseVariables.put("CommonResultClassName", CommonResult.class.getName());
        baseVariables.put("PageResultClassName", PageResult.class.getName());
        // VO 类，独有字段
        baseVariables.put("PageParamClassName", PageParam.class.getName());
        // DO 类，独有字段
        baseVariables.put("BaseDOClassName", BaseDO.class.getName());
        baseVariables.put("QueryWrapperClassName", LambdaQueryWrapperX.class.getName());
        baseVariables.put("BaseMapperClassName", BaseMapperX.class.getName());
        // Util 工具类
        baseVariables.put("ServiceExceptionUtilClassName", ServiceExceptionUtil.class.getName());
        baseVariables.put("LocalDateTimeUtilsClassName", LocalDateTimeUtils.class.getName());
        baseVariables.put("ExcelUtilsClassName", ExcelUtils.class.getName());
        baseVariables.put("DateUtilsClassName", DateUtils.class.getName());


    }


    public Map<String, String> genCode(CodegenTableDO table, List<CodegenColumnDO> columns) {
        Map<String, String> map = new HashMap<>();
        Map<String, Object> bindingMap = new HashMap<>(baseVariables);
        bindingMap.put("table", table);
        bindingMap.put("columns", columns);
        bindingMap.put("sceneEnum", CodegenSceneEnum.valueOf(table.getScene()));
        bindingMap.put("primaryColumn", CollectionUtils.findFirst(columns, CodegenColumnDO::getPrimaryKey));

        String simpleClassName = removePrefix(table.getClassName(), upperFirst(table.getModuleName()));
        bindingMap.put("simpleClassName", simpleClassName);
        // 将 DictType 转换成 dict_type
        bindingMap.put("simpleClassName_underlineCase", toUnderlineCase(simpleClassName));
        // 将 DictType 转换成 dictType，用于变量
        bindingMap.put("classNameVar", lowerFirst(simpleClassName));
        // 将 DictType 转换成 dict-type
        String simpleClassNameStrikeCase = toSymbolCase(simpleClassName, '-');
        bindingMap.put("simpleClassName_strikeCase", simpleClassNameStrikeCase);
        bindingMap.put("permissionPrefix", table.getModuleName() + ":" + simpleClassNameStrikeCase);

        map.put("controller.txt", doGenCode("codegen/controller/controller.vm", bindingMap));
        map.put("listReqVO.txt", doGenCode("codegen/controller/vo/listReqVO.vm", bindingMap));
        map.put("pageReqVO.txt", doGenCode("codegen/controller/vo/pageReqVO.vm", bindingMap));
        map.put("respVO.txt", doGenCode("codegen/controller/vo/respVO.vm", bindingMap));
        map.put("saveReqVO.txt", doGenCode("codegen/controller/vo/saveReqVO.vm", bindingMap));

        map.put("service.txt", doGenCode("codegen/service/service.vm", bindingMap));
        map.put("serviceImpl.txt", doGenCode("codegen/service/serviceImpl.vm", bindingMap));
        map.put("mapper.txt", doGenCode("codegen/dal/mysql/mapper.vm", bindingMap));
        map.put("do.txt", doGenCode("codegen/dal/dataobject/do.vm", bindingMap));
        return map;
    }


    /**
     * '
     * 填充代码生成模板
     */
    public String doGenCode(String vmPath, Map<String, Object> variables) {
        //自动根据用户引入的模板引擎库的jar来自动选择使用的引擎
        //TemplateConfig为模板引擎的选项，可选内容有字符编码、模板路径、模板加载方式等，默认通过模板字符串渲染
        TemplateConfig config = new TemplateConfig();
        config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
        TemplateEngine templateEngine = new VelocityEngine(config);
        String content = templateEngine.getTemplate(vmPath).render(variables);
        return content;
    }
}
