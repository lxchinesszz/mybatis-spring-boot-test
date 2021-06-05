package com.github.mybatis.sql.log;

import com.alibaba.druid.sql.SQLUtils;
import com.github.mybatis.sql.config.MybatisLogStore;
import com.github.mybatis.sql.config.PrintColor;
import com.github.mybatis.sql.config.PrintModel;
import com.github.mybatis.sql.console.TestConsole;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.boot.ansi.AnsiColor;

import java.util.List;
import java.util.Objects;

/**
 * @author liuxin 2021/6/3 3:51 下午
 */
public class SqlStore {

    Configuration configuration;

    BoundSql boundSql;

    public SqlStore(Configuration configuration, BoundSql boundSql) {
        this.configuration = configuration;
        this.boundSql = boundSql;
    }

    public void printSql(String mappedStatementId) {
        String sqlLog = this.sqlLog();
        MybatisLogStore mybatisLogStore = MybatisLogStore.getInstance();
        PrintColor printColor = mybatisLogStore.getPrintColor();
        String text = mybatisLogStore.getText();
        TestConsole.colorPrintln(text);
        TestConsole.colorPrintln(PrintColor.BRIGHT_GREEN, "方法签名:{}", mappedStatementId);
        System.out.println();
        TestConsole.colorPrintln(printColor, "{}", sqlLog);
    }

    public String sqlLog() {
        String targetSql = boundSql.getSql();
        StringBuffer stringBuffer = new StringBuffer(targetSql);
        resetSql(stringBuffer, 0);
        String cleanSql = stringBuffer.toString();
        Object parameterObject = boundSql.getParameterObject();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); ++i) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    String propertyName = parameterMapping.getProperty();
                    Object value;
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    Class<?> javaType = parameterMapping.getJavaType();
                    if (Objects.nonNull(value)) {
                        if (javaType.equals(String.class)) {
                            cleanSql = cleanSql.replaceAll("#\\{" + i + "\\}", "'" + value + "'");
                        } else {
                            cleanSql = cleanSql.replaceAll("#\\{" + i + "\\}", value.toString());
                        }
                    }
                }
            }
        }
        MybatisLogStore mybatisLogStore = MybatisLogStore.getInstance();
        String formatMySql = SQLUtils.formatMySql(cleanSql);
        if (mybatisLogStore.getPrintModel() == PrintModel.LOWER) {
            return formatMySql.toLowerCase();
        } else if (mybatisLogStore.getPrintModel() == PrintModel.UPPER) {
            return formatMySql.toUpperCase();
        } else {
            return formatMySql;
        }
    }

    /**
     * 将mybatis中sql占位符还原
     *
     * @param sb    sql信息
     * @param index 占位索引(会自增)
     */
    public void resetSql(StringBuffer sb, int index) {
        char[] chars = sb.toString().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar == '?') {
                sb.replace(i, i + 1, "#{" + index + "}");
                resetSql(sb, ++index);
                return;
            }
        }
    }
}
