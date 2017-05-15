package com.dmoffat.website.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author dan
 */
public class WebUtils {
    public static Cookie findCookieByName(HttpServletRequest request, String name) {
        Objects.requireNonNull(request, "Request cannot be null.");

        if(StringUtils.isBlank(name))
            throw new IllegalArgumentException("Cookie name cannot be null / empty.");

        Cookie[] cookies = request.getCookies();

        if(cookies == null || cookies.length == 0) {
            return null;
        }

        for(Cookie c : cookies) {
            if(c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }
}
