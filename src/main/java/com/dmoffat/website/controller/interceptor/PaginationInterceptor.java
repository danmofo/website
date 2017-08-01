package com.dmoffat.website.controller.interceptor;

import com.dmoffat.website.view.pagination.PageRequest;
import com.dmoffat.website.view.pagination.PageRequestImpl;
import com.dmoffat.website.view.pagination.Sort;
import com.google.common.primitives.Ints;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Intercepts all requests and looks for "page", "rows" and "sort" parameters, if they exist, will add a PageRequest attribute representing their values to the request.
 *
 * This allows request handlers to not have to explicitly check for these params and instead just inject the request attribute
 *
 * Not sure if this is the correct place to do this...could probably define a HttpMessageConverter that would allow you to
 * use PageRequest as a request handler param and have Spring auto-magically create it.
 *
 * @author danielmoffat
 */
@Component
public class PaginationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LogManager.getLogger(PaginationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer page = tryParse(request.getParameter("page"));
        Integer rows = tryParse(request.getParameter("rows"));
        Sort sort = Sort.fromString(request.getParameter("sort"));

        // The builder returns null if the required params are null, so no need to check for that inside this method.
        PageRequest pageRequest =
                new PageRequestImpl.Builder()
                        .page(page)
                        .rows(rows)
                        .sort(sort)
                        .build();

        request.setAttribute("pageRequest", pageRequest);

        logger.info("PageRequest determined from this request was: " + pageRequest);

        return true;
    }

    // Wrapper around Ints.tryParse that ignores null values, since request parameters may be missing (and therefore be null).
    private Integer tryParse(String value) {
        try {
            return Ints.tryParse(value);
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
