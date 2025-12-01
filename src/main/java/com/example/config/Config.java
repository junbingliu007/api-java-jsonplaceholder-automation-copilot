
package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("未找到配置文件 config.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("配置文件加载失败", e);
        }
    }

    public static String get(String key) {
        String v = props.getProperty(key);
        if (v == null) {
            throw new IllegalArgumentException("配置项未定义: " + key);
        }
        return v.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
