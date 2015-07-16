/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wolai.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.License;
import com.wolai.platform.service.LicenseService;

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
	public String list(License license, HttpServletRequest request, HttpServletResponse response, Model model) {
	    DetachedCriteria dc = DetachedCriteria.forClass(License.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        page = licenseService.findPage(dc, start, limit);
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