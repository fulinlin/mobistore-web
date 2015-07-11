package com.wolai.platform.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.SpringContextHolder;
import com.wolai.platform.util.WebUtils;

/**
 * 
 * 
 * 系统拦截器
 * 
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
public class SystemInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
			throws Exception {
		// TODO Auto-generated method stub

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
			if (packageName.startsWith(Constant.API_CLIENT_PACKAGE)) {
				// Token 验证逻辑（用户登录）

				// 安全认证
				// TODO

				// 登录验证
				String token = request.getParameter("token");
				if (StringUtils.isNotEmpty(token)) {
					UserService userService = SpringContextHolder.getBean(UserService.class);
					SysUser user = userService.getUserByToken(token.trim());
					if (user != null) {
						request.setAttribute(Constant.REQUEST_USER, user);
						return true;
					}
				}
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("code", -100);
				result.put("msg", "not login!");
				WebUtils.renderJson(response, JSON.toJSONString(result));
				return false;
			} else if (packageName.startsWith(Constant.API_OUT_PACKAGE)) {
				// 对外接口安全验证逻辑
				// TODO
			} else {
				HttpSession sesion = request.getSession(false);
				if (sesion != null) {
					LoginInfo info = (LoginInfo) sesion.getAttribute(Constant.SESSION_LOGINFO);
					if (info != null) {
						return true;
					} else {
						String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
						String redirection = basePath + Constant.LOGIN_URL;
						response.sendRedirect(redirection);
					}
				}
			}	
		}
		return false;
	}

}
