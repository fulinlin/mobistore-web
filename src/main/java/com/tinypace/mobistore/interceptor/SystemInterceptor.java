package com.tinypace.mobistore.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.util.SpringContextHolder;
import com.tinypace.mobistore.util.WebUtils;

public class SystemInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
			throws Exception {
		 
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			// 是否需要进行身份验证注解获取
			AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);

			if (authPassport != null && authPassport.validate() == false) { // 声明不验证权限
				return true;
			}

			// 根据不同package处理不同身份认证逻辑
			String packageName = ((HandlerMethod) handler).getBeanType().getPackage().getName();
			
			// app鉴权管理
			if (packageName.startsWith(Constant.API_PACKAGE_FOR_CLIENT)) {
				String token = request.getHeader("token");
				if (StringUtils.isNotBlank(token)) {
					// 登录验证
					ClientService userService = SpringContextHolder.getBean(ClientService.class);
					StrClient client = userService.getClientByToken(token.trim());
					if (client != null) {
						request.setAttribute(Constant.REQUEST_USER, client);
						return true;
					}
				}
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("code", -100);
				result.put("msg", "not login");
				WebUtils.renderJson(response, JSON.toJSONString(result));
				return false;
			}
		} else {
			return true;
		}
		return false;
	}

}
