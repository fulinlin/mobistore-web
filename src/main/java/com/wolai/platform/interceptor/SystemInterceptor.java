package com.wolai.platform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wolai.platform.annotation.AuthPassport;
/**
 * 
 * 
 * 系统拦截器
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
public class SystemInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	  if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
    		  AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
    		//声明不验证权限
              if(authPassport.validate() == false){
            	  return true;
              }else{
            	  // TODO 验证
              }
    	  }
    	return true;
    }

}
