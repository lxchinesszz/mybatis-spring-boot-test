package com.github.mybatis.sql.config;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 建议只在测试环境使用,用于在开发过程中检查sql是否符合业务
 *
 * @author liuxin
 * 2021/6/5 5:55 下午
 * @see MybatisLogConfig
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MybatisLogImportBeanDefinitionRegistrar.class)
public @interface MybatisTestLogEnable {

    /**
     * 大小写设置
     *
     * @return PrintModel
     */
    PrintModel mode() default PrintModel.NONE;

    /**
     * 打印颜色
     *
     * @return PrintColor
     */
    @AliasFor("value")
    PrintColor color() default PrintColor.BRIGHT_MAGENTA;

    /**
     * 打印颜色
     *
     * @return PrintColor
     */
    @AliasFor("color")
    PrintColor value() default PrintColor.BRIGHT_MAGENTA;

    /**
     * sql打印前缀
     *
     * @return String
     */
    String text() default "\uD83D\uDE80 格式化SQL:";
}
