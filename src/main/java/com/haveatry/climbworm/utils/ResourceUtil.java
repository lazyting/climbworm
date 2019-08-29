package com.haveatry.climbworm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ResourceUtil {
    private static Properties props = new Properties();

    static {
        load();
    }

    private static void load() {
        InputStream in = ResourceUtil.class.getClass().getResourceAsStream("/sys-config.properties");
        try {
            if (in != null) {
                props.load(new InputStreamReader(in, "UTF-8"));
            }
        } catch (Exception e) {
            System.out.println("加载资源失败");
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(final String name) {
        return props.getProperty(name);
    }

    public static int getIntProperty(final String name) {
        String value = getProperty(name);
        return EmptyUtil.isEmpty(value) ? -1 : Integer.parseInt(props.getProperty(name));
    }

}
