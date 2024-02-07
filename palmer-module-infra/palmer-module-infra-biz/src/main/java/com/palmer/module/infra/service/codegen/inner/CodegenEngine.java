package com.palmer.module.infra.service.codegen.inner;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
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
import com.palmer.framework.common.util.object.BeanUtils;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.map.MapUtil.getStr;
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


    /**
     * 后端的模板配置
     * <p>
     * key：模板在 resources 的地址
     * value：生成的路径
     */
    private static final Map<String, String> SERVER_TEMPLATES = MapUtil.<String, String>builder(new LinkedHashMap<>())
            .put(javaTemplatePath("controller/vo/pageReqVO"), javaModuleImplVOFilePath("PageReqVO"))
            .put(javaTemplatePath("controller/vo/listReqVO"), javaModuleImplVOFilePath("ListReqVO"))
            .put(javaTemplatePath("controller/vo/respVO"), javaModuleImplVOFilePath("RespVO"))
            .put(javaTemplatePath("controller/vo/saveReqVO"), javaModuleImplVOFilePath("SaveReqVO"))
            .put(javaTemplatePath("controller/controller"), javaModuleImplControllerFilePath())
            .put(javaTemplatePath("dal/do"), javaModuleImplMainFilePath("dal/dataobject/${table.businessName}/${table.className}DO"))
            .put(javaTemplatePath("dal/mapper"), javaModuleImplMainFilePath("dal/mysql/${table.businessName}/${table.className}Mapper"))
            .put(javaTemplatePath("dal/mapper.xml"), mapperXmlFilePath())
            .put(javaTemplatePath("service/service"), javaModuleImplMainFilePath("service/${table.businessName}/${table.className}Service"))
            .put(javaTemplatePath("service/serviceImpl"), javaModuleImplMainFilePath("service/${table.businessName}/${table.className}ServiceImpl"))
            .put(javaTemplatePath("service/serviceImpl"), javaModuleImplMainFilePath("service/${table.businessName}/${table.className}ServiceImpl"))
            .put("codegen/sql/sql.vm", "sql/sql.sql")
            .build();

    private static String mapperXmlFilePath() {
        return "palmer-module-${table.moduleName}/" + // 顶级模块
                "palmer-module-${table.moduleName}-biz/" + // 子模块
                "src/main/resources/mapper/${table.businessName}/${table.className}Mapper.xml";
    }

    private static String javaModuleImplMainFilePath(String path) {
        return javaModuleFilePath(path, "biz", "main");
    }

    private static String javaModuleImplControllerFilePath() {
        return javaModuleFilePath("controller/${sceneEnum.basePackage}/${table.businessName}/" +
                "${sceneEnum.prefixClass}${table.className}Controller", "biz", "main");
    }

    private static String javaModuleImplVOFilePath(String path) {
        return javaModuleFilePath("controller/${sceneEnum.basePackage}/${table.businessName}/" +
                "vo/${sceneEnum.prefixClass}${table.className}" + path, "biz", "main");
    }

    private static String javaModuleFilePath(String path, String module, String src) {
        return "palmer-module-${table.moduleName}/" + // 顶级模块
                "palmer-module-${table.moduleName}-" + module + "/" + // 子模块
                "src/" + src + "/java/${basePackage}/module/${table.moduleName}/" + path + ".java";
    }

    private static String javaTemplatePath(String path) {
        return "codegen/java/" + path + ".vm";
    }

    @PostConstruct
    void initGlobalBindingMap() {
        // 全局配置
        baseVariables.put("basePackage", "com.palmer");

        baseVariables.put("OperateLogClassName", OperateLog.class.getName());
        baseVariables.put("OperateTypeEnumClassName", OperateTypeEnum.class.getName());


        baseVariables.put("jakartaPackage", "javax");
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
        baseVariables.put("BeanUtils", BeanUtils.class.getName());


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


        // 1.2 获得模版
        Map<String, String> templates = getTemplates(bindingMap);

        templates.forEach((vmPath, filePath) -> {
            // 2.3 默认生成
            map.put(formatFilePath(filePath, bindingMap)
                    , doGenCode(vmPath, bindingMap));
        });
        return map;
    }

    private Map<String, String> getTemplates(Map<String, Object> bindingMap) {
        Map<String, String> templates = new LinkedHashMap<>();
        templates.putAll(SERVER_TEMPLATES);


        return templates;
    }

    @SuppressWarnings("unchecked")
    private String formatFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        filePath = StrUtil.replace(filePath, "${classNameVar}",
                getStr(bindingMap, "classNameVar"));
        filePath = StrUtil.replace(filePath, "${simpleClassName}",
                getStr(bindingMap, "simpleClassName"));
        // sceneEnum 包含的字段
        CodegenSceneEnum sceneEnum = (CodegenSceneEnum) bindingMap.get("sceneEnum");
        filePath = StrUtil.replace(filePath, "${sceneEnum.prefixClass}", sceneEnum.getPrefixClass());
        filePath = StrUtil.replace(filePath, "${sceneEnum.basePackage}", sceneEnum.getBasePackage());
        // table 包含的字段
        CodegenTableDO table = (CodegenTableDO) bindingMap.get("table");
        filePath = StrUtil.replace(filePath, "${table.moduleName}", table.getModuleName());
        filePath = StrUtil.replace(filePath, "${table.businessName}", table.getBusinessName());
        filePath = StrUtil.replace(filePath, "${table.className}", table.getClassName());
        // 特殊：主子表专属逻辑
        Integer subIndex = (Integer) bindingMap.get("subIndex");
        if (subIndex != null) {
            CodegenTableDO subTable = ((List<CodegenTableDO>) bindingMap.get("subTables")).get(subIndex);
            filePath = StrUtil.replace(filePath, "${subTable.moduleName}", subTable.getModuleName());
            filePath = StrUtil.replace(filePath, "${subTable.businessName}", subTable.getBusinessName());
            filePath = StrUtil.replace(filePath, "${subTable.className}", subTable.getClassName());
            filePath = StrUtil.replace(filePath, "${subSimpleClassName}",
                    ((List<String>) bindingMap.get("subSimpleClassNames")).get(subIndex));
        }
        return filePath;
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
