package com.haveatry.climbworm.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lei on 2019/8/30.
 */
public class StringUtil {
    private static final String[] charsets = {"ISO8859-1", "UTF-8", "GBK", "GB18030", "ISO-8859-1", "UTF-16"};

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

    /**
     * 对页面进行编码处理
     *
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String doStringCharset(String content) throws UnsupportedEncodingException {
        String returnContent = "";
        if (StringUtil.isContainChinese(content)) {
            returnContent = content;
        } else {
            String newContent;
            for (String charset : charsets) {
                newContent = new String(content.getBytes(charset), "utf-8");
                if (StringUtil.isContainChinese(newContent)) {
                    returnContent = newContent;
                    break;
                }
            }
        }
        return returnContent;
    }
}
