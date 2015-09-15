package com.tinypace.mobistore.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tinypace.mobistore.bean.LoginInfo;
import com.tinypace.mobistore.config.SystemConfig;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.entity.License;
import com.tinypace.mobistore.entity.LicenseCategory;
import com.tinypace.mobistore.entity.SponsorLicense;
import com.tinypace.mobistore.service.LicenseCategoryService;
import com.tinypace.mobistore.service.LicenseService;
import com.tinypace.mobistore.service.SponsorLicenseService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.SponsorLicenseVo;

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

    @Autowired
    private LicenseCategoryService licenseCategoryService;

    @Autowired
    private LicenseService licenseService;
    
    @Autowired
    private UserService userService;

    @ModelAttribute
    public SponsorLicense get(@RequestParam(required = false) String id) {
        SponsorLicense entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = (SponsorLicense) sponsorLicenseService.get(SponsorLicense.class, id);
        }
        if (entity == null) {
            entity = new SponsorLicense();
        }
        return entity;
    }

    @RequestMapping(value = { "list", "" })
    public String list(SponsorLicense sponsorLicense, @RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,HttpServletRequest request, HttpServletResponse response, Model model) {
    	if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
    	DetachedCriteria dc = DetachedCriteria.forClass(SponsorLicense.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        LoginInfo loginInfo = getLoginInfoSession(request);
        dc.add(Restrictions.eq("loginAccountId", loginInfo.getLoginAccount().getId()));
        if (StringUtils.isNotBlank(sponsorLicense.getLicenseCategoryId())) {
            dc.add(Restrictions.eq("licenseCategoryId", sponsorLicense.getLicenseCategoryId()));
        }
        if (StringUtils.isNotBlank(sponsorLicense.getCarNo())) {
            dc.add(Restrictions.like("carNo", sponsorLicense.getCarNo(), MatchMode.ANYWHERE).ignoreCase());
        }
        List<LicenseCategory> licenseCategories = licenseCategoryService.getLicenseCategories(loginInfo.getLoginAccount().getId());
        model.addAttribute("licenseCategories", licenseCategories);

        page = sponsorLicenseService.findPage(dc,  (pageNo-1)*pageSize, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("sponsorLicense", sponsorLicense);
        return "sys/sponsorLicense/sponsorLicenseList";
    }

    @RequestMapping(value = "form")
    public String form(SponsorLicense sponsorLicense, HttpServletRequest request, Model model) {
        LoginInfo loginInfo = getLoginInfoSession(request);
        List<LicenseCategory> licenseCategories = licenseCategoryService.getLicenseCategories(loginInfo.getLoginAccount().getId());
        model.addAttribute("sponsorLicense", sponsorLicense);
        model.addAttribute("licenseCategories", licenseCategories);
        return "sys/sponsorLicense/sponsorLicenseForm";
    }

    @RequestMapping(value = "save")
    public String save(SponsorLicense sponsorLicense, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, sponsorLicense)) {
            return form(sponsorLicense, request, model);
        }
        //去除车牌空格
        sponsorLicense.setCarNo(sponsorLicense.getCarNo().trim());

        LoginInfo loginInfo = getLoginInfoSession(request);
        sponsorLicense.setLoginAccountId(loginInfo.getLoginAccount().getId());
        License license = licenseService.getLincense(sponsorLicense.getCarNo());
        sponsorLicense.setLicenseId(license==null?null:license.getId());
        sponsorLicenseService.saveOrUpdate(sponsorLicense);
        addMessage(redirectAttributes, "保存赞助车牌成功");
        return "redirect:" + SystemConfig.getAdminPath() + "/sponsorLicense/?repage";
    }

    @RequestMapping(value = "delete")
    public String delete(SponsorLicense sponsorLicense, RedirectAttributes redirectAttributes) {
        sponsorLicenseService.delete(sponsorLicense);
        addMessage(redirectAttributes, "删除赞助车牌成功");
        return "redirect:" + SystemConfig.getAdminPath() + "/sponsorLicense/?repage";
    }

    @RequestMapping(value = "checkCarNo")
    @ResponseBody
    public boolean checkCarNo(HttpServletRequest request, @RequestParam String carNo, String id) {
        LoginInfo loginInfo = getLoginInfoSession(request);
        return sponsorLicenseService.checkCodeUnique(SponsorLicense.class, new String[] { "carNo", "loginAccountId" }, id ,carNo, loginInfo.getLoginAccount().getId());
    }

    @RequestMapping(value = "licenses")
    @ResponseBody
    public Map<String, Object> getSponsorLicenses(@RequestParam String q, String page, HttpServletRequest request) {
        DetachedCriteria dc = DetachedCriteria.forClass(SponsorLicense.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        LoginInfo loginInfo = getLoginInfoSession(request);
        dc.add(Restrictions.eq("loginAccountId", loginInfo.getLoginAccount().getId()));
        if (StringUtils.isNotBlank(q)) {
            dc.add(Restrictions.like("carNo", q, MatchMode.ANYWHERE).ignoreCase());
        }
        this.page = sponsorLicenseService.findPage(dc, (Integer.valueOf(page) - 1) * 15, limit);
        Map<String, Object> ret = new HashMap<String, Object>();
        List<SponsorLicenseVo> vols = new ArrayList<SponsorLicenseVo>();
        for (Object obj : this.page.getItems()) {
            SponsorLicense po = (SponsorLicense) obj;
            SponsorLicenseVo vo = new SponsorLicenseVo();
            BeanUtilEx.copyProperties(vo, po);
            vols.add(vo);
        }
        ret.put("code", RespCode.SUCCESS.Code());
        ret.put("data", vols);
        return ret;
    }

}