package com.wolai.platform.controller;

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

import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.LicenseCategory;
import com.wolai.platform.entity.SponsorLicense;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.SysUser.PayType;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.LicenseCategoryService;
import com.wolai.platform.service.LicenseService;
import com.wolai.platform.service.SponsorLicenseService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.SponsorLicenseVo;

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
    public String list(SponsorLicense sponsorLicense, HttpServletRequest request, HttpServletResponse response, Model model) {
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

        page = sponsorLicenseService.findPage(dc, start, limit);
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
        if (license == null) {
            //创建TEMP用户 默认是PERPAID
            SysUser user = new SysUser();
            user.setCustomerType(UserType.TEMP);
            user.setPayType(PayType.PERPAID);
            licenseService.saveOrUpdate(user);

            //创建车牌
            license = new License();
            license.setCarNo(sponsorLicense.getCarNo());
            license.setUserId(user.getId());
            licenseService.saveOrUpdate(license);
        }
        sponsorLicense.setLicenseId(license.getId());
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