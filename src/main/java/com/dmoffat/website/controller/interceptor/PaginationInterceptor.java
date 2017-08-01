package com.dmoffat.website.controller.interceptor;

import com.dmoffat.website.view.pagination.PageRequest;
import com.dmoffat.website.view.pagination.PageRequestImpl;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Intercepts all requests and looks for "page" and "rows" parameters, if they exist, will add a PageRequest attribute representing their values to the request.
 *
 * This allows code to not have to explicitly check for these params and instead just check for the request object.
 *
 * Not sure if this is the correct place to do this.
 *
 * todo: add support for rows
 *
 * @author danielmoffat
 */
@Component
public class PaginationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String page = request.getParameter("page");
        String rows = request.getParameter("rows");

        if(Strings.isNullOrEmpty(page)) {
            return false;
        }

        PageRequest pageRequest = new PageRequestImpl(Integer.valueOf(page));
        request.setAttribute("pageRequest", pageRequest);

        return true;
    }
}
