package com.wolai.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.bean.Page;
import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Enterprise;
import com.wolai.platform.entity.SysLoginAccount;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.LoginAccountService;
import com.wolai.platform.service.RoleService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.service.impl.TestService;
import com.wolai.platform.servlet.ValidateCodeServlet;
import com.wolai.platform.util.CacheUtils;

@Controller
public class LoginAccountController extends BaseController{

	@Autowired
	private LoginAccountService  loginAccountService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestService testservice;
	
	
	@ModelAttribute
	public SysLoginAccount get(@RequestParam(required=false) String id,String userId) {
		if (StringUtils.isNotBlank(id)){
			return (SysLoginAccount) loginAccountService.get(SysUser.class,id);
		}else  if(StringUtils.isNotBlank(userId)){
			SysLoginAccount condition = new SysLoginAccount();
			condition.setUserId(userId);
			Enterprise en = userService.getEnterpriceInfo(userId);
			if(en!=null){
				condition.setEnterpriseId(en.getId());
				Page<SysLoginAccount> pages = loginAccountService.findAllByPage(condition, 0, Integer.MAX_VALUE);
				if(pages.getTotal()>0){
					return pages.getItems().get(0);
				}else{
					return condition;
				}
			}
		}
		return null;
	}
	
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
				loginInfo.setLoginAccount(account);
				loginInfo.setUser(account.getUser());
				loginInfo.setEnterprice(userService.getEnterpriceInfo(account.getUserId()));
				loginInfo.setRoles(roleService.getTopRoleByUserId(account.getId()));
				
				setLoginInfoSession(request, loginInfo);
				return "redirect:"+SystemConfig.getAdminPath();
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
	public String wellcome(HttpServletRequest request){
		if(request.getSession(false)!=null){
			LoginInfo info = getLoginInfoSession(request);
			if(info!=null){
					return "admin/index";
			}
		}
		return "redirect:"+SystemConfig.getAdminPath()+"/prepareLogin";
	}
	
	@AuthPassport(validate=false)
	@RequestMapping("${adminPath}/test")
	public String test(HttpServletRequest request){
		testservice.saveTestData();
		return "redirect:"+SystemConfig.getAdminPath()+"/prepareLogin";
	}
	
	@AuthPassport(validate=false)
	@RequestMapping("${adminPath}/sys/loginaccount/active")
	private String active(String activeCode,RedirectAttributes redirectAttributes){
		DetachedCriteria dc = DetachedCriteria.forClass(SysLoginAccount.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
		dc.add(Restrictions.eq("activeCode", activeCode));
		SysLoginAccount account = (SysLoginAccount) loginAccountService.getObjectByCriteria(dc);
		if(account!=null){
			account.setStatus(SysLoginAccount.STATUS_NORMAL);
			loginAccountService.saveOrUpdate(account);
			redirectAttributes.addFlashAttribute("email", account.getEmail());
			addMessage(redirectAttributes, "激活成功,请登陆！");
		}else{
			addMessage(redirectAttributes, "激活码错误！");
		}
		return "";
	}
	
	@RequestMapping("${adminPath}/sys/loginaccount/form")
	public String edit(SysLoginAccount loginAccount, Model model,RedirectAttributes redirectAttributes){
		if(loginAccount==null){
			addMessage(redirectAttributes, "用户不存在！");
			return "redirect:" + SystemConfig.getAdminPath() + "/user/?repage";
		}
		
		if(loginAccount!=null && StringUtils.isNotEmpty(loginAccount.getId())){
			loginAccount = (SysLoginAccount) loginAccountService.get(SysLoginAccount.class, loginAccount.getId());
		}
		model.addAttribute("loginAccount", loginAccount);
		return "sys/loginaccount/loginaccountForm";
	}
	
	@RequestMapping({"${adminPath}/sys/loginaccount/list", "${adminPath}/sys/loginaccount"})
	public String list(HttpServletRequest request,SysLoginAccount loginAccount,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,Model model){
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		page = loginAccountService.findAllByPage(loginAccount,  (pageNo-1)*pageSize, pageSize);
		model.addAttribute("page", page);
		return "sys/loginaccount/loginaccountList";
	}
	
	@RequestMapping("${adminPath}/sys/loginaccount/modifyPwd")
	public String modifyPwd(Model model,HttpServletRequest request) {
		model.addAttribute("loginaccount",getLoginInfoSession(request).getLoginAccount());
		model.addAttribute("modifyPwd",true);
		return "sys/loginaccount/loginaccountModifyPwd";
	}
	@RequestMapping("${adminPath}/sys/loginaccount/modifySave")
	public String modifySave(SysLoginAccount loginAccount,String newPassword,String oldPassword,HttpServletRequest request,Model model) {
		if (StringUtils.isBlank(newPassword) || newPassword.length()<8) {
			addMessage(model, "密码至少为8位非空字符");
		}
		
		if (StringUtils.isBlank(oldPassword)) {
			addMessage(model, "密码至少为8位非空字符");
		}
		//if(loginAccount.getPassword().)
		
		loginAccount.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(newPassword)));
		return logout(request);
	}
	
	@RequestMapping("${adminPath}/sys/loginaccount/save")
	public String save(SysLoginAccount loginAccount, String oldLoginName, String newPassword, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			loginAccount.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(newPassword)));
		}
		// 参数校验
		if (!beanValidator(model, loginAccount)) {
			return edit(loginAccount, model,redirectAttributes);
		}
		
		// email唯一校验
		if (!"true".equals(checkEmail(request,loginAccount.getId(),loginAccount.getEmail()))) {
			addMessage(model, "保存登陆账户'" +loginAccount.getEmail() + "'失败，邮箱已存在");
			return edit(loginAccount, model,redirectAttributes);
		}
		
		userService.saveOrUpdate(loginAccount);
		
		String addMessage = "";
		addMessage(redirectAttributes, "保存用户'" + loginAccount.getEmail()+ "'信息成功");
		
		if(loginAccount.getId().equals(getLoginInfoSession(request).getLoginAccount().getId())){
			this.logout(request);
		}
		return "redirect:" + SystemConfig.getAdminPath() + "/user/?repage";
	}
	
	@ResponseBody
	@RequestMapping("${adminPath}/sys/loginaccount/checkEmail")
	public String checkEmail(HttpServletRequest request,String lId,String email){
		String uemail = request.getParameter("user.email");
		if(StringUtils.isNotBlank(uemail)){
			email = uemail.trim();
		}
		if(StringUtils.isBlank(email)){
			return "false";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysLoginAccount.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("email", email));
		if(StringUtils.isNotBlank(lId)){
			dc.add(Restrictions.ne("id", lId));
		}
		List<SysUser> users =loginAccountService.findAllByCriteria(dc);
		if(users==null || users.size()==0){
			return "true";
		}
		return "false";
	}
	
	/**
	 * 是否是验证码登录(暂时不用)
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */	
	private static boolean isValidateCodeLogin(String useruame,boolean isFail, boolean clean){
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
