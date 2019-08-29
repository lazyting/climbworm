package com.haveatry.climbworm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lei on 2019/8/30.
 */
public class StringUtil {
    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
