package com.wolai.platform.controller;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.IdGen;

@Controller("webUserController")
@RequestMapping("${adminPath}/user")
public class UserController extends BaseController {

	@Autowired
	public UserService userService;
	
	@ModelAttribute
	public SysUser get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return (SysUser) userService.get(SysUser.class,id);
		}else{
			SysUser use = new SysUser();
			use.setAuthToken(IdGen.uuid());
			return use;
		}
	}
	
	@RequestMapping({"list", ""})
	public String list(HttpServletRequest request,SysUser user,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,Model model){
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		
		user.setCustomerType(UserType.INDIVIDUAL);
		
		page = userService.findAllByPage(user, (pageNo-1)*pageSize, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("user", user);
		return "user/userList";
	}
	
	@RequestMapping("edit")
	public String edit(SysUser user, Model model){
		if(user!=null && StringUtils.isNotEmpty(user.getId())){
			user = (SysUser) userService.get(SysUser.class, user.getId());
		}
		model.addAttribute("user", user);
		List<UserType> usertypes = Arrays.asList(UserType.values());
		model.addAttribute("usertype",usertypes );
		
		return "user/userForm";
	}
	
	@RequestMapping("save")
	public String save(SysUser user, String oldLoginName, String newPassword, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(newPassword)));
		}
		// 参数校验
		if (!beanValidator(model, user)) {
			return edit(user, model);
		}
		
		// 手机唯一校验
		if (userService.validateMobile(user.getMobile(),user.getId())) {
			addMessage(model, "保存用户'" + user.getMobile() + "'失败，手机已存在");
			return edit(user, model);
		}
		userService.saveOrUpdate(user);
		addMessage(redirectAttributes, "保存用户'" + user.getMobile() + "'成功");
		return "redirect:" + SystemConfig.getAdminPath() + "/user/?repage";
	}
	
	@RequestMapping("view")
	public String view(SysUser user, Model model) {
		if(user!=null && user.getId()!=null){
			user = (SysUser) userService.get(SysUser.class, user.getId());
			model.addAttribute("user", user);
			return "user/viewUser";
		}
		return "";
	}
	
	@RequestMapping("disable")
	public String disable(SysUser user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		if(user!=null){
			user.setIsDisable(true);
			userService.saveOrUpdate(user);
			addMessage(redirectAttributes, "用户'" + user.getMobile() + "'已禁用！");
		}else{
			addMessage(redirectAttributes, "用户不存在！");
		}
		
		return "redirect:" + SystemConfig.getAdminPath() + "/user/?repage";
	}
	
	@RequestMapping("enable")
	public String enable(SysUser user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		if(user!=null){
			user.setIsDisable(false);
			userService.saveOrUpdate(user);
			addMessage(redirectAttributes, "用户'" + user.getMobile() + "'已启用！");
		}else{
			addMessage(redirectAttributes, "用户不存在！");
		}
		
		return "redirect:" + SystemConfig.getAdminPath() + "/user/?repage";
	}
	
	
	@ResponseBody
	@RequestMapping("checkMobile")
	public String checkMobile(String userId,String mobile){
		if(StringUtils.isBlank(mobile)){
			return "false";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.add(Restrictions.eq("mobile", mobile));
		if(StringUtils.isNotBlank(userId)){
			dc.add(Restrictions.ne("id", userId));
		}
		List<SysUser> users =userService.findAllByCriteria(dc);
		if(users==null || users.size()==0){
			return "true";
		}
		return "false";
	}
}
