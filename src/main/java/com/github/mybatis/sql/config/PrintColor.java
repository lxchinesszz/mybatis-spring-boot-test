package com.github.mybatis.sql.config;

/**
 * @author liuxin
 * 2021/6/5 6:28 下午
 */
public enum PrintColor {

    /**
     * 默认值
     */
    DEFAULT("39"),

    /**
     * 黑色
     */
    BLACK("30"),

    /**
     * 红色
     */
    RED("31"),

    /**
     * 绿色
     */
    GREEN("32"),

    /**
     * 黄色
     */
    YELLOW("33"),

    /**
     * 绿色
     */
    BLUE("34"),

    /**
     * 品红色
     */
    MAGENTA("35"),

    /**
     * 青色
     */
    CYAN("36"),

    /**
     * 白色
     */
    WHITE("37"),

    /**
     * 亮黑色
     */
    BRIGHT_BLACK("90"),

    /**
     * 亮红色
     */
    BRIGHT_RED("91"),

    /**
     * 亮绿
     */
    BRIGHT_GREEN("92"),

    /**
     * 亮黄
     */
    BRIGHT_YELLOW("93"),

    /**
     * 亮蓝
     */
    BRIGHT_BLUE("94"),

    /**
     * 亮品红
     */
    BRIGHT_MAGENTA("95"),

    /**
     * 亮青色
     */
    BRIGHT_CYAN("96"),

    /**
     * 亮白
     */
    BRIGHT_WHITE("97");

    /**
     * 颜色编码
     */
    private final String code;

    PrintColor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
