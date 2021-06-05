package com.github.mybatis.sql.interceptor;

import com.github.mybatis.sql.config.MybatisLogStore;
import com.github.mybatis.sql.console.TestConsole;
import com.github.mybatis.sql.log.SqlLog;
import com.github.mybatis.sql.log.SqlStore;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对查询类接口进行拦截
 *
 * @author liuxin
 * 2021/6/5 5:44 下午
 */
@Intercepts(@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class TestMapperQueryLogInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(TestMapperQueryLogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Configuration configuration = mappedStatement.getConfiguration();
        Object arg = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(arg);
        try {
            SqlStore sqlStore = new SqlStore(configuration, boundSql);
            sqlStore.printSql(mappedStatement.getId());
        } catch (Throwable t) {
            logger.error("TestMapperQueryLogInterceptor invoker error");
        }
        return invocation.proceed();
    }
}