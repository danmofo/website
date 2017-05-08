package com.dmoffat.website;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;

/**
 * Tests all aspects of the website authentication, including:
 * - Access to protected resources
 * - Obtaining access tokens
 * - User log in
 * - JWT creation and parsing
 *
 * @author dan
 */

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest({"auth.secret=test_secret"})
public class AuthenticationTests {

    @Test
    public void test_PasswordIsHashed() throws Exception {
        fail();
    }

    // Test the form login
    @Test
    public void test_loginWithValidUsernameAndPassword() throws Exception {
        fail();
    }

    @Test
    public void test_loginWithInvalidUsernameAndPassword() throws Exception {
        fail();
    }

    @Test
    public void test_accessProtectedResourceWithoutCookie() throws Exception {
        fail();
    }

    @Test
    public void test_accessProtectedResourceWithInvalidCookie() throws Exception {
        fail();
    }

    @Test
    public void test_accessProtectedResourceWithCookie() throws Exception {
        fail();
    }

    // Test the REST functionality
    @Test
    public void testRest_obtainTokenWithValidCredentials() throws Exception {
        fail();
    }

    @Test
    public void testRest_obtainTokenWithInvalidCredentials() throws Exception {
        fail();
    }

    @Test
    public void testRest_accessProtectedResourceWithValidToken() throws Exception {
        fail();
    }

    @Test
    public void testRest_accessProtectedResourceWithInvalidToken() throws Exception {
        fail();
    }

    // Tests for JwtsUtils
    @Test
    public void testJwtCreation() throws Exception {
        fail();
    }

    @Test
    public void testJwtParsing() throws Exception {
        fail();
    }
}
