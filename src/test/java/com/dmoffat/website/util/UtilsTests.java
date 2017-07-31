package com.dmoffat.website.util;

import com.dmoffat.website.model.Tag;
import com.dmoffat.website.test.UnitTest;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Testing the various utility classes
 *
 * @author dan
 */
public class UtilsTests extends UnitTest {
    private static final Cookie TEST_COOKIE = new Cookie("test", "value");

    private Tag tag;
    private Tag tag2;

    @Before
    public void setUp() throws Exception {
        this.tag = new Tag.Builder().id(1L).value("My tag value").build();
        this.tag2 = new Tag.Builder().value("Copy me").build();
    }

    @Test
    public void copyPropertiesIgnoringNull() throws Exception {
        BeanUtils.copyPropertiesIgnoringNull(tag2, tag);

        // Check that null properties haven't been copied, but non-null properties have
        assertNull(tag2.getId());
        assertEquals("Copy me", tag.getValue());
    }

    @Test
    public void getNullProperties() throws Exception {
        Set<String> expected = Sets.newHashSet("created", "updated");

        assertEquals(expected, ReflectionUtils.getNullProperties(tag));
    }

    @Test
    public void getNullPropertiesAsString() throws Exception {
        tag.setCreated(LocalDateTime.MAX);
        String[] expected = new String[]{"updated"};

        assertEquals(expected, ReflectionUtils.getNullPropertiesString(tag));
    }

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
