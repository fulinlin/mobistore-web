package com.wolai.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.config.SystemConfig;
import com.wolai.platform.entity.SysAPIKey;
import com.wolai.platform.service.ApiKeyService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.util.IdGen;

@Controller("webApiKeyController")
@RequestMapping(value = "${adminPath}/apikey")
public class ApiKeyController extends BaseController{
	
	@Autowired
	ApiKeyService apiKeyService;
	
	@Autowired
	ParkingLotService parkingLotService;
	
	@ModelAttribute
	public SysAPIKey get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return (SysAPIKey)apiKeyService.getById(SysAPIKey.class,id);
		}else{
			return new SysAPIKey();
		}
	}
	
    @RequestMapping(value = {"list", ""})
    public String list(SysAPIKey apikey,@RequestParam(required=false) String name,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,HttpServletRequest request, HttpServletResponse response, Model model) {
    	if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
    	
        DetachedCriteria dc = DetachedCriteria.forClass(SysAPIKey.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        dc.add(Restrictions.eq("isDisable", Boolean.FALSE));
        
        if(StringUtils.isNotBlank(name)){
        	dc.createAlias("parkingLot", "parkingLot",JoinType.LEFT_OUTER_JOIN);
        	dc.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
        	
        }
        
        if(StringUtils.isNotBlank(apikey.getToken())){
            dc.add(Restrictions.like("token", apikey.getToken(),MatchMode.ANYWHERE));
        }
        
        page = apiKeyService.findPage(dc, (pageNo-1)*pageSize, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("name", name);
        model.addAttribute("apikey", apikey);
        
        return "sys/apikey/apikeyList";
    }
	
    @RequestMapping("form")
	public String form(SysAPIKey apikey, Model model){
    	model.addAttribute("apikey", apikey);
		model.addAttribute("parkinglots", parkingLotService.findAllByPage(null,0,Integer.MAX_VALUE).getItems());
		
		return "sys/apikey/apikeyForm";
	}
	
	@RequestMapping("save")
	public String save(SysAPIKey apikey, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		// 参数校验
		if (!beanValidator(model, apikey)) {
			return form(apikey, model);
		}
		if(apikey.getId()!=null){
			apikey.setToken(IdGen.uuid());
		}
		apiKeyService.saveOrUpdate(apikey);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + SystemConfig.getAdminPath() + "/apikey/?repage";
	}
    
	@RequestMapping("enable")
	public String enable(SysAPIKey apikey, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		if(apikey!=null){
			apikey.setIsDisable(false);
			apiKeyService.saveOrUpdate(apikey);
			addMessage(redirectAttributes, "授权已启用！");
		}else{
			addMessage(redirectAttributes, "授权不存在！");
		}
		
		return "redirect:" + SystemConfig.getAdminPath() + "/apikey/?repage";
	}
	
	@RequestMapping("disable")
	public String disable(SysAPIKey apikey, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
		if(apikey!=null){
			apikey.setIsDisable(true);
			apiKeyService.saveOrUpdate(apikey);
			addMessage(redirectAttributes, "授权已禁用！");
		}else{
			addMessage(redirectAttributes, "授权不存在！");
		}
		
		return "redirect:" + SystemConfig.getAdminPath() + "/apikey/?repage";
	}
}
