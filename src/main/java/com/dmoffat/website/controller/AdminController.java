package com.dmoffat.website.controller;

import com.dmoffat.website.model.User;
import com.dmoffat.website.rest.ApiResponse;
import com.dmoffat.website.rest.impl.AuthenticationApiResponse;
import com.dmoffat.website.rest.impl.ErrorApiResponse;
import com.dmoffat.website.service.AuthenticationService;
import com.dmoffat.website.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

/**
 * @author dan
 */
@Controller
public class AdminController {
    private static final String LOGIN_FAILED_ERROR_CODE = "com.dmoffat.website.error_messages.login_failed";
    private static final String UNAUTHORISED_ERROR_CODE = "com.dmoffat.website.error_messages.unauthorised";

    private MessageSource messageSource;
    private AuthenticationService authenticationService;

    @Autowired
    public AdminController(MessageSource messageSource, AuthenticationService authenticationService) {
        this.messageSource = messageSource;
        this.authenticationService = authenticationService;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        Cookie cookie = WebUtils.findCookieByName(request, "auth");

        if(cookie == null) {
            return false;
        }

        return authenticationService.isValidToken(cookie.getValue());
    }

    @RequestMapping(value = "/management/auth", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        if(isAuthenticated(request)) {
            return "redirect:/management/";
        }

        model.addAttribute("user", new User());

        return "login";
    }

    @RequestMapping(value="/management/auth", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> restHandleLogin(@RequestBody @Valid User user, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity<ApiResponse>(ErrorApiResponse.fromBindingResult(result), HttpStatus.BAD_REQUEST);
        }

        if(authenticationService.login(user)) {
            return new ResponseEntity<>(new AuthenticationApiResponse(authenticationService.createTokenForUser(user)), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ErrorApiResponse("100", messageSource.getMessage(LOGIN_FAILED_ERROR_CODE, null, Locale.ENGLISH)), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value="/management/auth/error", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> restError() {
        return new ResponseEntity<>(new ErrorApiResponse("101", messageSource.getMessage(UNAUTHORISED_ERROR_CODE, null, Locale.ENGLISH)), HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/management/auth", method = RequestMethod.POST)
    public String handleLogin(@Valid User user, BindingResult result, HttpServletResponse response, Model model) {
        if(result.hasErrors()) {
            return "login";
        }

        if(!authenticationService.login(user)) {
            result.rejectValue("username", LOGIN_FAILED_ERROR_CODE);
            model.addAttribute("user", user);
            return "login";
        }

        Cookie authCookie = new Cookie("auth", authenticationService.createTokenForUser(user));
        authCookie.setMaxAge(Integer.MAX_VALUE);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/");
        response.addCookie(authCookie);

        return "redirect:/management/";
    }

    @RequestMapping(value = "/management/", method = RequestMethod.GET)
    public String home() {
        return "/admin/home";
    }
}
