/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wolai.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.LicenseCategory;
import com.wolai.platform.service.LicenseCategoryService;

/**
 * 车牌分类Controller
 * @author sevenshi
 * @version 2015-07-20
 */
@Controller("webLicenseCategoryController")
@RequestMapping(value = "${adminPath}/licenseCategory")
public class LicenseCategoryController extends BaseController {

	@Autowired
	private LicenseCategoryService licenseCategoryService;
	
	@ModelAttribute
	public LicenseCategory get(@RequestParam(required=false) String id) {
		LicenseCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (LicenseCategory) licenseCategoryService.get(LicenseCategory.class, id);
		}
		if (entity == null){
			entity = new LicenseCategory();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(LicenseCategory licenseCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
	    DetachedCriteria dc = DetachedCriteria.forClass(LicenseCategory.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        LoginInfo loginInfo = getLoginInfoSession(request);
        dc.add(Restrictions.eq("loginAccountId",   loginInfo.getLoginAccount().getId()));
        page = licenseCategoryService.findPage(dc, start, limit);
		model.addAttribute("page", page);
		model.addAttribute("licenseCategory", licenseCategory);
		return "sys/licenseCategory/licenseCategoryList";
	}

	@RequestMapping(value = "form")
	public String form(LicenseCategory licenseCategory, Model model) {
		model.addAttribute("licenseCategory", licenseCategory);
		return "sys/licenseCategory/licenseCategoryForm";
	}

	@RequestMapping(value = "save")
	public String save(LicenseCategory licenseCategory, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, licenseCategory)){
			return form(licenseCategory, model);
		}
		LoginInfo loginInfo = getLoginInfoSession(request);
		licenseCategory.setLoginAccountId(loginInfo.getLoginAccount().getId());
		licenseCategoryService.saveOrUpdate(licenseCategory);
		addMessage(redirectAttributes, "保存车牌分类成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/licenseCategory/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(LicenseCategory licenseCategory, RedirectAttributes redirectAttributes) {
		licenseCategoryService.delete(licenseCategory);
		addMessage(redirectAttributes, "删除车牌分类成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/licenseCategory/?repage";
	}
}