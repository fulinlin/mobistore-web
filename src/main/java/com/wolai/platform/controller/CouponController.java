/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wolai.platform.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.Coupon.CouponType;
import com.wolai.platform.entity.Enterprise;
import com.wolai.platform.entity.LicenseCategory;
import com.wolai.platform.entity.SponsorLicense;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.EnterpriseService;
import com.wolai.platform.service.LicenseCategoryService;
import com.wolai.platform.service.SponsorLicenseService;

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

    @Autowired
    private LicenseCategoryService licenseCategoryService;

    @Autowired
    private SponsorLicenseService sponsorLicenseService;

    @Autowired
    private EnterpriseService enterpriseService;

    @ModelAttribute
    public Coupon get(@RequestParam(required = false) String id) {
        Coupon entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = (Coupon) couponService.get(Coupon.class, id);
        }
        if (entity == null) {
            entity = new Coupon();
        }
        return entity;
    }

    @RequestMapping(value = { "list", "" })
    public String list(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
        DetachedCriteria dc = DetachedCriteria.forClass(Coupon.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        dc.add(Restrictions.eq("isUsed", coupon.getIsUsed()));
        LoginInfo loginInfo = getLoginInfoSession(request);
        dc.add(Restrictions.eq("loginAccountId", loginInfo.getLoginAccount().getId()));
        if( StringUtils.isNotBlank(coupon.getCarNo())){
            dc.add(Restrictions.like("carNo", coupon.getCarNo() , MatchMode.ANYWHERE).ignoreCase());
        }
        if( StringUtils.isNotBlank(coupon.getOrigin())){
            dc.add(Restrictions.like("origin", coupon.getOrigin() , MatchMode.ANYWHERE).ignoreCase());
        }
        if( coupon.getStartDate() != null){
            dc.add(Restrictions.gt("startDate", coupon.getStartDate()));
        }
        if( coupon.getEndDate() != null){
            dc.add(Restrictions.lt("startDate", coupon.getEndDate()));
        }
        page = couponService.findPage(dc, start, limit);
        model.addAttribute("page", page);
        model.addAttribute("coupon", coupon);
        return "sys/coupon/couponList";
    }

    @RequestMapping(value = "form")
    public String form(Coupon coupon, HttpServletRequest request, Model model) {
        LoginInfo loginInfo = getLoginInfoSession(request);
        List<LicenseCategory> licenseCategories = licenseCategoryService.getLicenseCategories(loginInfo.getLoginAccount().getId());
        model.addAttribute("licenseCategories", licenseCategories);
        model.addAttribute("coupon", coupon);
        return "sys/coupon/couponForm";
    }

    @RequestMapping(value = "save")
    public String save(Coupon coupon, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, coupon)) {
            return form(coupon, request, model);
        }
        List<Coupon> saveList = new ArrayList<Coupon>();
        Set<SponsorLicense> set = new HashSet<SponsorLicense>();
        if (coupon.getCarNos() != null && coupon.getCarNos().length > 0) {
            List<SponsorLicense> licenses = sponsorLicenseService.getSponsorLicensesByIds(coupon.getCarNos());
            set.addAll(licenses);
        }
        if (coupon.getLicenseCategories() != null && coupon.getLicenseCategories().length > 0) {
            List<SponsorLicense> licenses = sponsorLicenseService.getSponsorLicensesByCategory(coupon.getLicenseCategories());
            set.addAll(licenses);
        }
        LoginInfo loginInfo = getLoginInfoSession(request);
        for (SponsorLicense sponsorLicense : set) {
            Coupon po = new Coupon();
            po.setCarNo(sponsorLicense.getCarNo());
            po.setType(CouponType.TIME);
            po.setTime(coupon.getTime());
            po.setOrigin(coupon.getOrigin());
            po.setNote(coupon.getNote());
            po.setStartDate(coupon.getStartDate());
            po.setEndDate(coupon.getEndDate());
            po.setLoginAccountId(loginInfo.getLoginAccount().getId());
            saveList.add(po);
        }
        Enterprise enterprise = enterpriseService.getEnterprise(loginInfo.getUser().getId());
        long time = coupon.getTime();
        String message =  couponService.deductTime(enterprise, saveList, time);
        addMessage(redirectAttributes, message);
        return "redirect:" + SystemConfig.getAdminPath() + "/coupon/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(Coupon coupon, RedirectAttributes redirectAttributes) {
        couponService.delete(coupon);
        addMessage(redirectAttributes, "删除优惠券成功");
        return "redirect:" + SystemConfig.getAdminPath() + "/coupon/?repage";
    }

}