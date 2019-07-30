package com.haveatry.climbworm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceUtil {

    private static final InputStream in = ResourceUtil.class.getClass().getResourceAsStream("/sys-config.properties");

    public static String getPropertyValue(String propertyName) throws IOException {
        Properties properties = new Properties();
        properties.load(in);
        return properties.getProperty(propertyName);
    }

}
