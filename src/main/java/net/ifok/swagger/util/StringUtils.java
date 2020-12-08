package net.ifok.swagger.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:  字符工具
 * @Author: leftso
 * @Date: 2020/12/4 17:24
 **/
public class StringUtils {

    final static Pattern isContainChinesePattern=Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 是否包含中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Matcher m = isContainChinesePattern.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
