package com.github.mybatis.sql.config;

import java.util.Objects;

/**
 * 工具类用于读取配置
 *
 * @author liuxin
 * 2021/6/5 6:30 下午
 */
public final class MybatisLogStore {

    private PrintColor printColor;

    private PrintModel printModel;

    private String text;

    private static MybatisLogStore mybatisLogStore;

    private MybatisLogStore(PrintColor printColor, PrintModel printModel,String text) {
        this.printColor = printColor;
        this.printModel = printModel;
        this.text = text;
    }

    private MybatisLogStore() {
    }

    public static MybatisLogStore getInstance() {
        if (Objects.isNull(mybatisLogStore)) {
            MybatisLogStore.mybatisLogStore = new MybatisLogStore(PrintColor.RED,
                    PrintModel.NONE,"\uD83D\uDE80 格式化SQL:");
        }
        return mybatisLogStore;
    }

    public static void initializeTheMybatisLogStore(PrintColor printColor, PrintModel printModel,String text) {
        MybatisLogStore.mybatisLogStore = new MybatisLogStore(printColor, printModel,text);
    }

    public PrintColor getPrintColor() {
        return printColor;
    }

    public PrintModel getPrintModel() {
        return printModel;
    }

    public String getText() {
        return text;
    }
}
