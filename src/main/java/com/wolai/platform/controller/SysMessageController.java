package com.wolai.platform.controller;

import java.util.Date;

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
import com.wolai.platform.entity.SysMessage;
import com.wolai.platform.service.SysMessageService;
import com.wolai.platform.util.PushUtil;

/**
 * 推送消息Controller
 * @author sevenshi
 * @version 2015-07-24
 */
@Controller("webSysMessageController")
@RequestMapping(value = "${adminPath}/sysMessage")
public class SysMessageController extends BaseController {

	@Autowired
	private SysMessageService sysMessageService;
	
	@ModelAttribute
	public SysMessage get(@RequestParam(required=false) String id) {
		SysMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (SysMessage) sysMessageService.get(SysMessage.class, id);
		}
		if (entity == null){
			entity = new SysMessage();
			entity.setCreateTime(new Date());
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SysMessage sysMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
	    DetachedCriteria dc = DetachedCriteria.forClass(SysMessage.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        if(StringUtils.isNotBlank(sysMessage.getTitle())){
            dc.add(Restrictions.like("title", sysMessage.getTitle() , MatchMode.ANYWHERE).ignoreCase());
        }
        dc.add(Restrictions.eq("published", sysMessage.getPublished()));
        page = sysMessageService.findPage(dc, start, limit);
		model.addAttribute("page", page);
		model.addAttribute("sysMessage", sysMessage);
		return "sys/sysMessage/sysMessageList";
	}

	@RequestMapping(value = "form")
	public String form(SysMessage sysMessage, Model model) {
		model.addAttribute("sysMessage", sysMessage);
		return "sys/sysMessage/sysMessageForm";
	}

	@RequestMapping(value = "save")
	public String save(SysMessage sysMessage, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, sysMessage)){
			return form(sysMessage, model);
		}
		PushUtil androidPush = new PushUtil(PushUtil.APP_KEY_ANDROID, PushUtil.APP_SECRET_ANDROID);
		androidPush.sendAndroidBroadcast("message", sysMessage.getTitle(), sysMessage.getTitle() , sysMessage.getContent(),
                "go_app", null, null);
		PushUtil iosPush = new PushUtil(PushUtil.APP_KEY_IOS, PushUtil.APP_SECRET_IOS);
		iosPush.sendIOSBroadcast(sysMessage.getContent());
		sysMessage.setPublished(true);
		sysMessageService.saveOrUpdate(sysMessage);
		addMessage(redirectAttributes, "保存推送消息成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sysMessage/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(SysMessage sysMessage, RedirectAttributes redirectAttributes) {
		sysMessageService.delete(sysMessage);
		addMessage(redirectAttributes, "删除推送消息成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sysMessage/?repage";
	}

}