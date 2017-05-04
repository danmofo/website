package com.dmoffat.website.util;

/**
 * @author dan
 */
public class StringUtils {
    public static boolean isBlank(String str) {
        return str == null || str.equals("");
    }
}
