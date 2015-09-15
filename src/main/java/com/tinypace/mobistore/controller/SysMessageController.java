package com.tinypace.mobistore.controller;

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

import com.tinypace.mobistore.config.SystemConfig;
import com.tinypace.mobistore.entity.SysMessage;
import com.tinypace.mobistore.service.MsgService;
import com.tinypace.mobistore.service.SysMessageService;
import com.tinypace.mobistore.util.PushUtil;

/**
 * 推送消息Controller
 * @author sevenshi
 * @version 2015-07-24
 */
@Controller("webSysMessageController")
@RequestMapping(value = "${adminPath}/sysMessage")
public class SysMessageController extends BaseController {

	@Autowired
	private MsgService msgService;
	
	@ModelAttribute
	public SysMessage get(@RequestParam(required=false) String id) {
		SysMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = (SysMessage) msgService.get(SysMessage.class, id);
		}
		if (entity == null){
			entity = new SysMessage();
			entity.setCreateTime(new Date());
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SysMessage sysMessage, @RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysMessage.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        if(StringUtils.isNotBlank(sysMessage.getTitle())){
            dc.add(Restrictions.like("title", sysMessage.getTitle() , MatchMode.ANYWHERE).ignoreCase());
        }
        dc.add(Restrictions.eq("published", sysMessage.getPublished()));
        page = msgService.findPage(dc,  (pageNo-1)*pageSize, pageSize);
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
		msgService.sendAppMsg(sysMessage.getTitle(), "-", sysMessage.getTitle());
		PushUtil iosPush = new PushUtil(PushUtil.APP_KEY_IOS, PushUtil.APP_SECRET_IOS);
		iosPush.sendIosBroadcast(sysMessage.getContent());
		sysMessage.setPublished(true);
		msgService.saveOrUpdate(sysMessage);
		addMessage(redirectAttributes, "保存推送消息成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sysMessage/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(SysMessage sysMessage, RedirectAttributes redirectAttributes) {
		msgService.delete(sysMessage);
		addMessage(redirectAttributes, "删除推送消息成功");
		return "redirect:"+SystemConfig.getAdminPath()+"/sysMessage/?repage";
	}

}