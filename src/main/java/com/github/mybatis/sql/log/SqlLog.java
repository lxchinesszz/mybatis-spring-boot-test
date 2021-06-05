package com.github.mybatis.sql.log;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author liuxin 2021/6/3 3:52 下午
 */
public class SqlLog {

    public static String getMappedStatementId(Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        return declaringClass.getName() + "." + method.getName();
    }

    /**
     * 允许开发人员,直接根据xml资源并通过Mapper进行sql检查
     *
     * @param resource 资源信息
     * @param mapper   Mapper类型
     * @param <T>      Mapper类型
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapper(Resource resource, Class<T> mapper) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(mapper);
        enhancer.setCallback((MethodInterceptor) (o, method, arguments, methodProxy) -> {
            InputStream mapperInputStream = resource.getInputStream();
            Configuration configuration = new Configuration();
            Map<String, XNode> sqlFragments = configuration.getSqlFragments();
            String namespace = mapper.getName();
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperInputStream, configuration,
                    resource.getURI().getPath(), sqlFragments, namespace);
            xmlMapperBuilder.parse();
            MapperMethod.MethodSignature methodSignature =
                    new MapperMethod.MethodSignature(configuration, mapper, method);
            String mapperStatementId = getMappedStatementId(method);
            MappedStatement mappedStatement = configuration.getMappedStatement(mapperStatementId);
            Object sqlCommandParam = methodSignature.convertArgsToSqlCommandParam(arguments);
            BoundSql boundSql = mappedStatement.getBoundSql(sqlCommandParam);
            // 优先使用调用方生成的请求实体,如果没有走默认逻辑
            SqlStore sqlStore = new SqlStore(configuration, boundSql);
            sqlStore.printSql(mapperStatementId);
            return null;
        });
        return (T) enhancer.create();
    }

}
