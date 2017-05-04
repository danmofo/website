package com.dmoffat.website.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author dan
 */
public class WebUtils {
    public static Optional<Cookie> findCookieByName(HttpServletRequest request, String name) {
        Objects.requireNonNull(request, "Request cannot be null.");

        if(StringUtils.isBlank(name))
            throw new IllegalArgumentException("Cookie name cannot be null / empty.");

        Cookie[] cookies = request.getCookies();

        if(cookies == null || cookies.length == 0) {
            return Optional.empty();
        }

        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(name)).findAny();
    }
}
