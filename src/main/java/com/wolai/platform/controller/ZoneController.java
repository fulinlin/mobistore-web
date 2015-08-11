/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wolai.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.Zone;
import com.wolai.platform.service.ZoneService;

/**
 * 商圈管理Controller
 * @author sevenshi
 * @version 2015-07-27
 */
@Controller("webZoneController")
@RequestMapping(value = "${adminPath}/zone")
public class ZoneController extends BaseController {

	@Autowired
	private ZoneService zoneService;
	
	@ModelAttribute
	public Zone get(@RequestParam(required=false) String id) {
		Zone entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (Zone) zoneService.get(Zone.class, id);
		}
		if (entity == null){
			entity = new Zone();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Zone zone, HttpServletRequest request, @RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,HttpServletResponse response, Model model) {
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(Zone.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        if(StringUtils.isNotBlank(zone.getAreaId())){
            dc.add(Restrictions.eq("areaId", zone.getAreaId()));
        }
        if(StringUtils.isNotBlank(zone.getCode())){
            dc.add(Restrictions.like("code", zone.getCode(),MatchMode.ANYWHERE).ignoreCase());
        }
        if(StringUtils.isNotBlank(zone.getName())){
            dc.add(Restrictions.like("name", zone.getName(),MatchMode.ANYWHERE).ignoreCase());
        }
        page = zoneService.findPage(dc,(pageNo-1)*pageSize, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("zone", zone);
		return "sys/zone/zoneList";
	}

	@RequestMapping(value = "form")
	public String form(Zone zone, Model model) {
		model.addAttribute("zone", zone);
		return "sys/zone/zoneForm";
	}

	@RequestMapping(value = "save")
	public String save(Zone zone, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, zone)){
			return form(zone, model);
		}
		zone.setArea(null);
		zoneService.saveOrUpdate(zone);
		addMessage(redirectAttributes, "保存商圈管理成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/zone/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Zone zone, RedirectAttributes redirectAttributes) {
		zoneService.delete(zone);
		addMessage(redirectAttributes, "删除商圈管理成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/zone/?repage";
	}

}