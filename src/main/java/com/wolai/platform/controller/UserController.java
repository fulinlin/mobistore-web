package com.wolai.platform.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.UserService;

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
			return new SysUser();
		}
	}
	
	@RequestMapping({"list", ""})
	public String list(HttpServletRequest request,SysUser user,Model model){
		page = userService.findAllByPage(user, start, limit);
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
	
}
