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
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.service.CouponService;

/**
 * 优惠券Controller
 * @author sevenshi
 * @version 2015-07-16
 */
@Controller("webCouponController")
@RequestMapping(value = "${adminPath}/coupon")
public class CouponController extends BaseController {

	@Autowired
	private CouponService couponService;
	
	@ModelAttribute
	public Coupon get(@RequestParam(required=false) String id) {
		Coupon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (Coupon) couponService.get(Coupon.class, id);
		}
		if (entity == null){
			entity = new Coupon();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
	    DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        page = couponService.findPage(dc, start, limit);
		model.addAttribute("page", page);
		model.addAttribute("coupon", coupon);
		return "sys/coupon/couponList";
	}

	@RequestMapping(value = "form")
	public String form(Coupon coupon, Model model) {
		model.addAttribute("coupon", coupon);
		return "sys/coupon/couponForm";
	}

	@RequestMapping(value = "save")
	public String save(Coupon coupon, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, coupon)){
			return form(coupon, model);
		}
		couponService.saveOrUpdate(coupon);
		addMessage(redirectAttributes, "保存优惠券成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/coupon/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Coupon coupon, RedirectAttributes redirectAttributes) {
		couponService.delete(coupon);
		addMessage(redirectAttributes, "删除优惠券成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/coupon/?repage";
	}

}