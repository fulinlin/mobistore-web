package com.wolai.platform.controller.api.mobi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.FeedBack;
import com.wolai.platform.entity.FeedBack.FeedbackType;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.FeedbackService;
import com.wolai.platform.service.UserService;

@Controller
@RequestMapping(Constant.API_MOBI + "feedback/")
public class FeedbackController extends BaseController {
	@Autowired
	UserService userService;
	
	@Autowired
	FeedbackService feedbackService;

	@RequestMapping(value="feedback")
	@ResponseBody
	public Map<String,Object> feedback(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		String content = json.get("content");
		String type = json.get("type");
		
		if (StringUtils.isEmpty(content)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		FeedBack po = new FeedBack();
		if (FeedbackType.COMPLAIN.toString().equals(type)) {
			po.setType(FeedbackType.COMPLAIN);
		} else {
			po.setType(FeedbackType.SUGGESTION);
		}
		
		po.setContent(content);
		po.setUserId(user.getId());
		feedbackService.create(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@RequestMapping(value="appealForLincense")
	@ResponseBody
	public Map<String,Object> appealForLincense(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		String content = json.get("content");
		
		if (StringUtils.isEmpty(content)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		FeedBack po = new FeedBack();
		po.setType(FeedbackType.APPEAL_FOR_LINCENSE);
		po.setContent(content);
		po.setUserId(user.getId());
		feedbackService.create(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
}
