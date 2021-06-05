package com.github.mybatis.sql.console;

import com.github.mybatis.sql.config.PrintColor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuxin
 * 2021/6/5 5:43 下午
 */
public class TestConsole {

    private final static Map<String, AnsiColor> COLOR_AS_MAP = new ConcurrentHashMap<>();

    static {
        for (AnsiColor ansiColor : AnsiColor.values()) {
            COLOR_AS_MAP.put(ansiColor.toString(), ansiColor);
        }
    }


    public static String color(String text, AnsiColor color) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        return AnsiOutput.toString(color, text, AnsiColor.DEFAULT, AnsiStyle.BOLD);
    }

    public static String color(String text) {
        return color(text, AnsiColor.BRIGHT_GREEN);
    }

    public static void colorPrintln(String descFormat, Object... args) {
        System.out.println(color(MessageFormatter.arrayFormat(descFormat, args).getMessage()));
    }

    public static void colorPrintln(PrintColor color, String descFormat, Object... args) {
        AnsiColor ansiColor = COLOR_AS_MAP.get(color.getCode());
        System.out.println(color(MessageFormatter.arrayFormat(descFormat, args).getMessage(), ansiColor));
    }
}
