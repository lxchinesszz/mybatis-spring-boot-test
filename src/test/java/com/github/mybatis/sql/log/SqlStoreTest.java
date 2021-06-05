package com.github.mybatis.sql.log;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liuxin
 * 2021/6/5 6:24 下午
 */
class SqlStoreTest {

    @Test
    public void test(){
        System.out.println("SELECT * from 你好".toLowerCase());
        System.out.println("SELECT * from 你好".toUpperCase());

    }


}