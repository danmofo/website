package com.dmoffat.website;

import com.dmoffat.website.dao.UserDao;
import com.dmoffat.website.model.User;
import com.dmoffat.website.service.AuthenticationService;
import com.dmoffat.website.service.exception.UsernameAlreadyExistsException;
import com.dmoffat.website.test.IntegrationTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.Cookie;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests all aspects of the website authentication, including:
 * - Access to protected resources
 * - Obtaining access tokens
 * - User log in
 * - JWT parsing
 *
 * @author dan
 */


public class AuthenticationTests extends IntegrationTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDao userDao;

    private User user;

    @Value("${auth.secret}")
    private String secret;

    @Before
    public void setUp() throws Exception {
        user = new User("test", "password");
    }

    @Test
    public void newUserCreation() throws Exception {
        authenticationService.create(user);

        assertTrue(userDao.findUserByUsername("test") != null);
    }

    // todo: check exception is actually thrown..
    @Test
    public void newUserCreationWithANonUniqueUsername() throws Exception {
        authenticationService.create(user);

        try {
            authenticationService.create(user);
            fail();
        } catch (UsernameAlreadyExistsException ex) {}
    }

    @Test
    public void userPasswordIsHashed() throws Exception {
        authenticationService.create(user);

        User foundUser = userDao.findUserByUsername(user.getUsername());

        assertTrue(BCrypt.checkpw("password", foundUser.getPassword()));
    }

    // Test the form login
    @Test
    public void loginWithValidUsernameAndPassword() throws Exception {
        authenticationService.create(user);
        this.mockMvc
                .perform(
                        post("/management/auth")
                            .param("username", "danmofo")
                            .param("password", "password")
                )
                .andExpect(cookie().exists("auth"))
                .andExpect(view().name("redirect:/management/"))
                .andReturn();
    }

    @Test
    public void loginWithInvalidUsernameAndPassword() throws Exception {

        // Test with both values present, with an invalid username
        this.mockMvc
                .perform(
                        post("/management/auth")
                                .param("username", "adsf")
                                .param("password", "password")
                )
                .andExpect(cookie().doesNotExist("auth"))
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(view().name("login"))
                .andReturn();

        // Test form validation - todo: do 1 request and check for the existence of specific errors
        this.mockMvc
                .perform(
                        post("/management/auth")
                                .param("username", "daniel")
                )
                .andExpect(cookie().doesNotExist("auth"))
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(view().name("login"))
                .andReturn();

        this.mockMvc
                .perform(
                        post("/management/auth")
                                .param("password", "daniel")
                )
                .andExpect(cookie().doesNotExist("auth"))
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(view().name("login"))
                .andReturn();
    }

    // Test accessing protected resources
    @Test
    public void accessProtectedResourceWithoutCookie() throws Exception {
        this.mockMvc
                .perform(get("/management/"))
                .andExpect(redirectedUrl("/management/auth"))
                .andReturn();
    }

    @Test
    public void accessProtectedResourceWithInvalidCookie() throws Exception {
        this.mockMvc
                .perform(get("/management/").cookie(new Cookie("auth", "invalid cookie")))
                .andExpect(redirectedUrl("/management/auth"))
                .andReturn();
    }

    @Test
    public void accessProtectedResourceWithCookie() throws Exception {
        this.mockMvc
                .perform(get("/management/").cookie(new Cookie("auth", authenticationService.createTokenForUser(user))))
                .andExpect(view().name("/admin/home"))
                .andReturn();
    }

    // Test the REST functionality
    @Test
    public void rest_obtainTokenWithValidCredentials() throws Exception {
        authenticationService.create(user);
        this.mockMvc
                .perform(post("/management/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\": \"test\",\"password\": \"password\"}"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("authToken").exists())
                .andExpect(jsonPath("authToken").value(authenticationService.createTokenForUser(user)))
                .andReturn();
    }

    @Test
    public void rest_obtainTokenWithInvalidCredentials() throws Exception {
        // Invalid credentials
        this.mockMvc
                .perform(post("/management/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"username\": \"asdff\",\"password\": \"password\"}"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
                .andExpect(jsonPath("errorCode").value("100"))
                .andReturn();

        // Missing credentials - todo: check for specific values
        // No username + password
        this.mockMvc
                .perform(post("/management/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("errorMessage").isString())
                .andExpect(jsonPath("errorCode").value("200"))
                .andReturn();
    }

    @Test
    public void rest_accessProtectedResourceWithValidToken() throws Exception {
        authenticationService.create(user);
        this.mockMvc
                .perform(get("/management/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", authenticationService.createTokenForUser(user)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
    }

    @Test
    public void rest_accessProtectedResourceWithInvalidToken() throws Exception {
        this.mockMvc
                .perform(get("/management/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Obviously wrong."))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
                .andExpect(jsonPath("error").exists())
                .andReturn();
    }

    // Lots of tests found in the library repository: https://github.com/jwtk/jjwt, this covers a lot of
    // cases. The library itself does also not allow you to sign a key without an algorithm, so this is not
    // a possiblity.
    @Test
    public void jwtParsing() throws Exception {
        // Test a JWT generated by our service
        String token = authenticationService.createTokenForUser(user);
        authenticationService.isValidToken(token);

        // Test a JWT signed with a different secret
        String badToken = Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, "fake")
                .compact();

        assertFalse(authenticationService.isValidToken(badToken));
    }
}
