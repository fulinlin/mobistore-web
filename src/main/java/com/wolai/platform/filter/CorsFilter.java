package com.wolai.platform.filter;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.wolai.platform.util.WebUtils;

public class CorsFilter implements Filter {
    public CorsFilter() { }

    public void init(FilterConfig fConfig) throws ServletException { }

    public void destroy() {    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	WebUtils.AddCorsSupport(response);
        chain.doFilter(request, response);
    }
}