package com.palmer.module.infra.service.codegen.inner;

import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成的引擎，用于具体生成代码
 *
 * @author palmer
 * @date 2024-01-16
 */
@Component
public class CodegenEngine {

    public static void main(String[] args) {
        //自动根据用户引入的模板引擎库的jar来自动选择使用的引擎
//TemplateConfig为模板引擎的选项，可选内容有字符编码、模板路径、模板加载方式等，默认通过模板字符串渲染
        TemplateConfig config = new TemplateConfig();
        config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
        TemplateEngine templateEngine = new VelocityEngine(config);
        Map<String, Object> bindingMap = new HashMap<>();
        bindingMap.put("name", "sf");
        String content = templateEngine.getTemplate("codegen/controller1.vm").render(bindingMap);
        System.out.println(content);
    }
}
