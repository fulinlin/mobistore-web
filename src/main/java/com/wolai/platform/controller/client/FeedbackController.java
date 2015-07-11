package com.wolai.platform.controller.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.FeedBack;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.FeedbackService;
import com.wolai.platform.service.UserService;

@Controller
@RequestMapping(Constant.API_CLIENT + "feedback/")
public class FeedbackController extends BaseController {
	@Autowired
	UserService userService;
	
	@Autowired
	FeedbackService feedbackService;

	@RequestMapping(value="submit")
	@ResponseBody
	public Map<String,Object> submit(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		String content = json.get("content");
		FeedBack po = new FeedBack();
		po.setContent(content);
		po.setUserId(user.getId());
		feedbackService.create(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
}
