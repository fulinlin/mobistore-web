package com.tinypace.mobistore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tinypace.mobistore.config.SystemConfig;
import com.tinypace.mobistore.entity.License;
import com.tinypace.mobistore.entity.SysUser.UserType;
import com.tinypace.mobistore.service.LicenseService;

/**
 * 车牌Controller
 * @author sevenshi
 * @version 2015-07-16
 */
@Controller("webLicenseController")
@RequestMapping(value = "${adminPath}/license")
public class LicenseController extends BaseController {

	@Autowired
	private LicenseService licenseService;
	
	@ModelAttribute
	public License get(@RequestParam(required=false) String id) {
		License entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (License) licenseService.get(License.class, id);
		}
		if (entity == null){
			entity = new License();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(License license,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(License.class);
	    dc.createAlias("user", "user", JoinType.LEFT_OUTER_JOIN);
	    dc.add(Restrictions.ne("user.customerType",UserType.TEMP));
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        page = licenseService.findPage(dc,(pageNo-1)*pageSize, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("license", license);
		return "sys/license/licenseList";
	}

	@RequestMapping(value = "form")
	public String form(License license, Model model) {
		model.addAttribute("license", license);
		return "sys/license/licenseForm";
	}

	@RequestMapping(value = "save")
	public String save(License license, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, license)){
			return form(license, model);
		}
		licenseService.saveOrUpdate(license);
		addMessage(redirectAttributes, "保存车牌成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/license/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(License license, RedirectAttributes redirectAttributes) {
		licenseService.delete(license);
		addMessage(redirectAttributes, "删除车牌成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/license/?repage";
	}

}