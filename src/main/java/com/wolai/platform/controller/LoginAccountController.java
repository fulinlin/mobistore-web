package com.wolai.platform.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.SysLoginAccount;
import com.wolai.platform.service.LoginAccountService;
import com.wolai.platform.servlet.ValidateCodeServlet;
import com.wolai.platform.util.CacheUtils;

@Controller
public class LoginAccountController extends BaseController{

	@Autowired
	private LoginAccountService  loginAccountService;
	
	@AuthPassport(validate=false)
	@RequestMapping(value="${adminPath}/login",method = RequestMethod.POST)
	public String login( HttpServletRequest request,String userName,String password,@RequestParam(required=false) String validateCode,RedirectAttributes attr ){
		String code = (String)request.getSession(false).getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		String error="";
		if (StringUtils.isEmpty(validateCode) || !validateCode.toUpperCase().equals(code)){
			error= "验证码不正确！";
		}else{
			SysLoginAccount account=  loginAccountService.authLoginAccount(userName.trim(), password.trim());
			if(account!=null){
				LoginInfo loginInfo = new LoginInfo();
				loginInfo.setUser(account);
				setLoginInfoSession(request, loginInfo);
				return "redirect:"+SystemConfig.getAdminPath()+"/index/";
			}else{
				error= "用户名或密码不正确！";
			}
		}
		request.getSession(false).removeAttribute(ValidateCodeServlet.VALIDATE_CODE);
		attr.addFlashAttribute("error", error);
		return "redirect:"+SystemConfig.getAdminPath()+"/prepareLogin";
	}
	
	@AuthPassport(validate=false)
	@RequestMapping("${adminPath}/prepareLogin")
	public ModelAndView prepareLogin(RedirectAttributes attr){
		 @SuppressWarnings("unchecked")
		Map<String, String> map=(Map<String, String>) attr.getFlashAttributes();
		 String code = map.get("error");
		ModelAndView mv = new ModelAndView("login/prepareLogin");
		mv.addObject("isValidateCodeLogin", true);
		if(StringUtils.isNotEmpty(code)){
			mv.addObject("error",code);
		}
		
		return mv;
	}
	@RequestMapping("${adminPath}/logout")
	public String logout(HttpServletRequest request){
		request.removeAttribute(Constant.SESSION_LOGINFO);
		return "redirect:"+SystemConfig.getAdminPath()+"/prepareLogin";
	}
	
	@AuthPassport(validate=false)
	@RequestMapping("${adminPath}")
	public String welcom(){
		return "redirect:"+SystemConfig.getAdminPath()+"/prepareLogin";
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */	
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame,boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap =new HashMap<String, Integer>();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
}
