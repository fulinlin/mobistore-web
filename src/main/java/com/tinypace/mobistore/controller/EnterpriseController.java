package com.tinypace.mobistore.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tinypace.mobistore.config.SystemConfig;
import com.tinypace.mobistore.entity.Enterprise;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.service.EnterpriseService;

/**
 * 企业Controller
 * @version 2015-07-15
 */
@Controller("webEnterpriseController")
@RequestMapping(value = "${adminPath}/sys/enterprise")
public class EnterpriseController extends BaseController {

	@Autowired
	private EnterpriseService enterpriseService;
	
	@ModelAttribute
	public Enterprise get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return  (Enterprise) enterpriseService.get(Enterprise.class,id);
		}
		Enterprise entity = new Enterprise();
		entity.setUser(new SysUser());
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Enterprise enterprise, @RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(Enterprise.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
	    page = enterpriseService.findPage(dc,(pageNo-1)*pageSize, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("enterprise", enterprise);
		return "sys/enterpriseList";
	}

	@RequestMapping(value = "form")
	public String form(Enterprise enterprise, Model model) {
		model.addAttribute("enterprise", enterprise);
		return "sys/enterpriseForm";
	}

	@RequestMapping(value = "save")
	public String save(Enterprise enterprise, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterprise)){
			return form(enterprise, model);
		}
		enterpriseService.saveOrUpdate(enterprise);
		addMessage(redirectAttributes, "保存企业成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sys/user/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Enterprise enterprise, RedirectAttributes redirectAttributes) {
		enterpriseService.delete(enterprise);
		addMessage(redirectAttributes, "删除企业成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sys/user/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String eId, String name){
		// 新名称为空的情况
		if(StringUtils.isBlank(name)){
			return "false";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Enterprise.class);
	    dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
	    if(StringUtils.isNotBlank(eId)){
	    	dc.add(Restrictions.ne("id", eId));
	    }
	    dc.add(Restrictions.eq("name", name));
		List<Enterprise> existsList =  enterpriseService.findAllByCriteria(dc);
		if(existsList==null || existsList.size()==0){
			return "true";
		}
		return "false";
	}
	
}