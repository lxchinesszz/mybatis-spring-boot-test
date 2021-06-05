package com.github.mybatis.sql.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author liuxin
 * 2021/6/5 6:46 下午
 */
public class MybatisLogImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String mybatisLogConfig = MybatisTestLogEnable.class.getName();
        Map<String, Object> mybatisLogConfigAnnotation = importingClassMetadata.getAnnotationAttributes(mybatisLogConfig);
        PrintModel mode = (PrintModel) mybatisLogConfigAnnotation.get("mode");
        PrintColor color = (PrintColor) mybatisLogConfigAnnotation.get("color");
        String text = (String) mybatisLogConfigAnnotation.get("text");
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(MybatisLogConfig.class);
        registry.registerBeanDefinition(mybatisLogConfig, rootBeanDefinition);
        MybatisLogStore.initializeTheMybatisLogStore(color, mode, text);
    }

}
