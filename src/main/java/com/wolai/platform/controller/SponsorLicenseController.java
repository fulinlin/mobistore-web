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
import com.wolai.platform.entity.SponsorLicense;
import com.wolai.platform.service.SponsorLicenseService;

/**
 * 赞助车牌Controller
 * @author sevenshi
 * @version 2015-07-17
 */
@Controller("webSponsorLicenseController")
@RequestMapping(value = "${adminPath}/sponsorLicense")
public class SponsorLicenseController extends BaseController {

	@Autowired
	private SponsorLicenseService sponsorLicenseService;
	
	@ModelAttribute
	public SponsorLicense get(@RequestParam(required=false) String id) {
		SponsorLicense entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (SponsorLicense) sponsorLicenseService.get(SponsorLicense.class, id);
		}
		if (entity == null){
			entity = new SponsorLicense();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SponsorLicense sponsorLicense, HttpServletRequest request, HttpServletResponse response, Model model) {
	    DetachedCriteria dc = DetachedCriteria.forClass(SponsorLicense.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        page = sponsorLicenseService.findPage(dc, start, limit);
		model.addAttribute("page", page);
		model.addAttribute("sponsorLicense", sponsorLicense);
		return "sys/sponsorLicense/sponsorLicenseList";
	}

	@RequestMapping(value = "form")
	public String form(SponsorLicense sponsorLicense, Model model) {
		model.addAttribute("sponsorLicense", sponsorLicense);
		return "sys/sponsorLicense/sponsorLicenseForm";
	}

	@RequestMapping(value = "save")
	public String save(SponsorLicense sponsorLicense, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sponsorLicense)){
			return form(sponsorLicense, model);
		}
		sponsorLicenseService.saveOrUpdate(sponsorLicense);
		addMessage(redirectAttributes, "保存赞助车牌成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sponsorLicense/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(SponsorLicense sponsorLicense, RedirectAttributes redirectAttributes) {
		sponsorLicenseService.delete(sponsorLicense);
		addMessage(redirectAttributes, "删除赞助车牌成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sponsorLicense/?repage";
	}

}