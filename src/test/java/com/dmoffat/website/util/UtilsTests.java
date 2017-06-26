package com.dmoffat.website.util;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Testing the various utility classes
 *
 * @author dan
 */
public class UtilsTests {
    private static final Cookie TEST_COOKIE = new Cookie("test", "value");

    @Test
    public void findCookieByName() throws Exception {
        HttpServletRequest requestWithCookies = Mockito.mock(HttpServletRequest.class);
        when(requestWithCookies.getCookies()).thenReturn(new Cookie[]{TEST_COOKIE});

        HttpServletRequest requestWithoutCookies = Mockito.mock(HttpServletRequest.class);
        when(requestWithoutCookies.getCookies()).thenReturn(null);

        assertTrue(WebUtils.findCookieByName(requestWithCookies, "test") != null);
        assertTrue(WebUtils.findCookieByName(requestWithoutCookies, "test")  == null);

        // Test with null request, and a null and blank value
        // todo: find a nicer way to do this
        try {
            WebUtils.findCookieByName(null, "foo");
            fail();
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            WebUtils.findCookieByName(requestWithCookies, null);
            fail();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            WebUtils.findCookieByName(requestWithCookies, "");
            fail();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
