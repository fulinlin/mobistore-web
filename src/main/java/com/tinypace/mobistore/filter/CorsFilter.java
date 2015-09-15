package com.tinypace.mobistore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.tinypace.mobistore.util.WebUtils;

public class CorsFilter implements Filter {
    public CorsFilter() { }

    public void init(FilterConfig fConfig) throws ServletException { }

    public void destroy() {    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	WebUtils.AddCorsSupport(response);
        chain.doFilter(request, response);
    }
}