package com.tinypace.mobistore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tinypace.mobistore.util.WebUtils;

public class CorsFilter implements Filter {
    public CorsFilter() { }

    public void init(FilterConfig fConfig) throws ServletException { }

    public void destroy() {    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
    	String url = ((HttpServletRequest)request).getRequestURL().toString();
//    	System.out.println(url);
    	
    	if (url.indexOf("localhost") > -1 || url.indexOf("127.0.0.1") > -1 
        		|| url.indexOf("192.168") > -1 || url.indexOf("172.16") > -1 || url.indexOf("10.0") > -1) { // development
    		WebUtils.AddCorsSupport(response, false);
    	} else {
    		WebUtils.AddCorsSupport(response, true);
    	}
    	
        chain.doFilter(request, response);
    }
}