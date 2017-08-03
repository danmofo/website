package com.dmoffat.website.util;

/**
 * @author danielmoffat
 */
public class BeanUtils {
    /**
     * Copy properties from the source to the target, skipping over null properties.
     * @param source
     * @param target
     */
    public static void copyPropertiesIgnoringNull(Object source, Object target) {
        String[] propertiesToIgnore = ReflectionUtils.getNullPropertiesString(source);

        org.springframework.beans.BeanUtils.copyProperties(source, target, propertiesToIgnore);
    }
}
