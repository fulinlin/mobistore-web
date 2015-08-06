package com.wolai.platform.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.SysAPIKey;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.CommonService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.Encodes;
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
			if (packageName.startsWith(Constant.API_OUT_PACKAGE_CLIENT)) {
				String token = request.getParameter("token");
				if (StringUtils.isNotBlank(token)) {
					/* 对外接口安全性认证 */
					if (packageName.equals(Constant.API_OUT_PACKAGE_CLIENT)) {
						Map<String, Object> result = new HashMap<String, Object>();
						CommonService commonservice = SpringContextHolder.getBean(CommonService.class);
						// 获取消息加密key
						DetachedCriteria dc = DetachedCriteria.forClass(SysAPIKey.class);
						dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
						dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
						dc.add(Restrictions.eq("token", token.trim()));
						SysAPIKey key = (SysAPIKey) commonservice.getObjectByCriteria(dc);
						
						if(key!=null){
							request.setAttribute(Constant.REQUEST_PARINGLOTID, key.getParkingLotId());
							RequestWrapper waper = new RequestWrapper(request);
							String body = waper.getBody();
							JSONObject json = JSONObject.parseObject(body);
							String sign = json.getString("sign");
							if(StringUtils.isNotBlank(sign)){
								String MD5key = sign.substring(0, 31);
								String aesKey  = MD5key.substring(0,2)+MD5key.substring(8,10)+MD5key.substring(19, 21);// 1-3,9-11,20-22
								String jsonString=new String(Encodes.decodeAES(sign.substring(31),aesKey));
								Class<?> voClass = null;
								for(Class<?> classz:((HandlerMethod) handler).getMethod().getParameterTypes()){
									if(classz.getSimpleName().toLowerCase().endsWith("vo")){
										voClass=classz;
										break;
									}
								}
								if(voClass!=null){
									request.setAttribute("vo", JSON.parseObject(jsonString,voClass));
									return true;
								}else{
									throw new RuntimeException(((HandlerMethod) handler).getMethod().getName()+"参数不正确！请使用Vo作为requestBody参数！");
								}
							}
							result.put("code", -100);
							result.put("msg", "参数不正确，未找到sign字符串");
						}
						result.put("code", -100);
						result.put("msg", "授权token认证失败");
						
						WebUtils.renderJson(response, JSON.toJSONString(result));
						return false;
					}
					
					// 安全认证
					// TODO

					// Token 验证逻辑（用户登录）
					// 登录验证
					UserService userService = SpringContextHolder.getBean(UserService.class);
					SysUser user = userService.getUserByToken(token.trim());
					if (user != null) {
						request.setAttribute(Constant.REQUEST_USER, user);
						return true;
					}
				}
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("code", -100);
				result.put("msg", "not login");
				WebUtils.renderJson(response, JSON.toJSONString(result));
				return false;
			} else {
				HttpSession sesion = request.getSession(false);
				if (sesion != null) {
					LoginInfo info = (LoginInfo) sesion.getAttribute(Constant.SESSION_LOGINFO);
					if (info != null) {
						return true;
					}
				} else {
					String basePath = request.getScheme() + "://" + request.getServerName() + ":"
							+ request.getServerPort() + request.getContextPath();
					String redirection = basePath;
					response.sendRedirect(redirection);
				}
			}
		} else {
			String uriPrefix = request.getContextPath();
			String requestRri = request.getRequestURI();
			if (requestRri.endsWith("/")) {
				requestRri = requestRri.substring(0, requestRri.length() - 1);
			}
			if (requestRri.equals(uriPrefix)) {
				return true;
			}

		}
		return false;
	}

}
