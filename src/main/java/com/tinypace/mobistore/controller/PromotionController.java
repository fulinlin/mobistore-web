/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tinypace.mobistore.controller;

import java.util.Arrays;

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

import com.tinypace.mobistore.config.SystemConfig;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.entity.Promotion;
import com.tinypace.mobistore.entity.Promotion.ExchangeCode;
import com.tinypace.mobistore.entity.Promotion.PromotionLimitType;
import com.tinypace.mobistore.service.PromotionService;

/**
 * 优惠活动Controller
 * @author sevenshi
 * @version 2015-07-16
 */
@Controller("webPromotionController")
@RequestMapping(value = "${adminPath}/promotion")
public class PromotionController extends BaseController {

	@Autowired
	private PromotionService promotionService;
	
	@ModelAttribute
	public Promotion get(@RequestParam(required=false) String id) {
		Promotion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (Promotion) promotionService.get( Promotion.class,id);
		}
		if (entity == null){
			entity = new Promotion();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Promotion promotion,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(Promotion.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        page = promotionService.findPage(dc,  (pageNo-1)*pageSize, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("promotion", promotion);
		return "sys/promotion/promotionList";
	}

	@RequestMapping(value = "form")
	public String form(Promotion promotion, Model model) {
		model.addAttribute("promotion", promotion);
		model.addAttribute("exchangeCode",Arrays.asList(ExchangeCode.values()));
		model.addAttribute("eromotionLimitType",Arrays.asList(PromotionLimitType.values()));
		return "sys/promotion/promotionForm";
	}

	@RequestMapping(value = "save")
	public String save(Promotion promotion, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, promotion)){
			return form(promotion, model);
		}
		
		String path = "";
		ExchangeCode promotionCode = promotion.getCode();
	    if (ExchangeCode.POINTS_EXCHANGE.equals(promotionCode)) {
	    	path = "points";
	    } else if (ExchangeCode.SNAPUP_FREE.equals(promotionCode)) {
	    	path = "snapup";
	    }
		// http://101.200.189.57:9090/wolai-web/wolai-mobi/dist/#/promotion/points
		String url = Constant.WEB_PATH + "wolai-mobi/dist/#/promotion/" + path;
		promotion.setUrl(url);
		promotionService.saveOrUpdate(promotion);
		addMessage(redirectAttributes, "保存优惠活动成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/promotion/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Promotion promotion, RedirectAttributes redirectAttributes) {
		promotionService.delete(promotion);
		addMessage(redirectAttributes, "删除优惠活动成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/promotion/?repage";
	}

}