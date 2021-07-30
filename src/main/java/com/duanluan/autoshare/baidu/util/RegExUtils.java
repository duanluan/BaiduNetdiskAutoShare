package com.duanluan.autoshare.baidu.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 *
 * @author duanluan
 */
@Slf4j
public class RegExUtils extends org.apache.commons.lang3.RegExUtils {

    /**
     * 带完整!!!
     * NOTAM电报D项分段类型正则匹配方式
     */
    public static final String D_ITEM_REG = "[0-9]{4}-[0-9]{4} DLY|[0-9]{4}-[0-9]{4} EVERY";

    private RegExUtils() {
    }

    /**
     * 替换特殊字符
     *
     * @param source 需要替换的内容
     * @return 替换后的内容
     */
    public static String replaceAllSpecialChar(String source) {
        return source.replaceAll("/", "\\/")
                .replaceAll("\\\\", "\\\\\\")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)")
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\?", "\\\\?")
                .replaceAll("\\*", "\\\\*")
                .replaceAll("\\+", "\\\\+")
                .replaceAll("\\|", "\\\\|")
                .replaceAll("\\^", "\\\\^")
                .replaceAll("\\$", "\\\\$");
    }

    /**
     * 获取下标
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @return 匹配的下标
     */
    public static int indexOf(String regex, String source) {
        try {
            Matcher matcher = getMatcher(regex, source);
            if (matcher.find()) {
                return matcher.start();
            }
            return -1;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    /**
     * 是否匹配（第一个捕获组的值是否为空）
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @return 是否匹配
     */
    public static boolean isMatchedByGroup0(String regex, String source) {
        return StringUtils.isNotBlank(matchGroup0(regex, source));
    }

    /**
     * 是否匹配（第一个捕获组的值是否为 null）
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @return 是否匹配
     */
    public static boolean isMatchedByGroup0CanBeEmpty(String regex, String source) {
        return matchGroup0CanBeEmpty(regex, source) != null;
    }

    /**
     * 匹配第一个捕获组的值
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @return 匹配值
     */
    public static String matchGroup0(String regex, String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return matchGroup0(regex, source, 0);
    }

    /**
     * 匹配第一个捕获组的值，可以为空
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @return 匹配值
     */
    public static String matchGroup0CanBeEmpty(String regex, String source) {
        return matchGroup0CanBeEmpty(regex, source, 0);
    }

    /**
     * 匹配第一个捕获组的值
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @param flags  匹配模式
     * @return 匹配值
     */
    public static String matchGroup0(String regex, String source, int flags) {
        return matchByGroup(regex, source, flags, 0);
    }

    /**
     * 匹配第一个捕获组的值
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @param flags  匹配模式
     * @return 匹配值
     */
    public static String matchGroup0CanBeEmpty(String regex, String source, int flags) {
        return matchByGroupCanBeEmpty(regex, source, flags, 0);
    }

    /**
     * 根据捕获组获取匹配值
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @param group  捕获组
     * @return 匹配值
     */
    public static String matchByGroup(String regex, String source, int group) {
        return matchByGroup(regex, source, 0, group);
    }

    /**
     * 根据捕获组获取匹配值
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @param flags  匹配模式
     * @param group  捕获组
     * @return 匹配值
     */
    public static String matchByGroup(String regex, String source, int flags, int group) {
        String result = null;
        try {
            Matcher matcher = getMatcher(regex, source, flags);
            while (matcher.find()) {
                String subsequence = matcher.group(group);
                if (StringUtils.isNotBlank(subsequence)) {
                    return subsequence;
                }
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据捕获组获取匹配值
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @param flags  匹配模式
     * @param group  捕获组
     * @return 匹配值
     */
    public static String matchByGroupCanBeEmpty(String regex, String source, int flags, int group) {
        String result = null;
        try {
            Matcher matcher = getMatcher(regex, source, flags);
            while (matcher.find()) {
                String subsequence = matcher.group(group);
                if (subsequence != null) {
                    return subsequence;
                }
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取 Matcher 对象
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @param flags  匹配模式
     * @return Matcher 对象
     */
    public static Matcher getMatcher(String regex, String source, int flags) {
        Pattern pattern = Pattern.compile(regex, flags);
        return pattern.matcher(source);
    }

    /**
     * 获取 Matcher 对象
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @return Matcher 对象
     */
    public static Matcher getMatcher(String regex, String source) {
        Pattern pattern = Pattern.compile(regex, 0);
        return pattern.matcher(source);
    }

    /**
     * 获取匹配集合
     *
     * @param regex  正则
     * @param source 需要匹配的内容
     * @return 匹配集合
     */
    public static List<String> matches(String regex, String source) {
        List<String> resultList = new ArrayList<>();
        if (StringUtils.isBlank(source)) {
            return resultList;
        }
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source);
            while (matcher.find()) {
                resultList.add(matcher.group());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    /**
     * 是否包含汉字
     *
     * @param source 需要匹配的内容
     * @return 是否包含汉字
     */
    public static boolean containsHanZi(String source) {
        return isMatchedByGroup0("[\u4e00-\u9fa5]", source);
    }

    public static void main(String[] args) {
        // List<String> list = matches("(E|W)(\\d*\\.\\d*|\\d*)", "W361618.00S1404318.00");
        // list.forEach(System.out::print);
    }
}
