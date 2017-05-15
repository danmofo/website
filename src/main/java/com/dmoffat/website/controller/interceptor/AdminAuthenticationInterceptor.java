package com.dmoffat.website.controller.interceptor;

import com.dmoffat.website.service.AuthenticationService;
import com.dmoffat.website.util.StringUtils;
import com.dmoffat.website.util.WebUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dan
 */
@Component
public class AdminAuthenticationInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LogManager.getLogger(AdminAuthenticationInterceptor.class);
    private AuthenticationService authenticationService;

    @Autowired
    public AdminAuthenticationInterceptor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Determine the request type, is it from a web browser, or an API consumer?

        // API consumer
        String authorisationHeader = request.getHeader("Authorization");

        if(!StringUtils.isBlank(authorisationHeader)) {
            if(authenticationService.isValidToken(authorisationHeader)) {
                logger.info("User authenticated with header.");
                return true;
            }

            logger.info("Header is invalid");
            response.getWriter().write("{\"error\": \"Unauthorised.\"}");
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // Web browser with cookies
        Cookie cookie = WebUtils.findCookieByName(request, "auth");

        if(cookie == null || !authenticationService.isValidToken(cookie.getValue())) {
            logger.info("User NOT authenticated.");
            response.sendRedirect("/management/auth");
            return true;
        }

        logger.info("User authenticated with cookie.");
        return true;
    }
}
