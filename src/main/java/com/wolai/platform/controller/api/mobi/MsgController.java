package com.wolai.platform.controller.api.mobi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysMessage;
import com.wolai.platform.entity.SysMessageSend;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.MsgService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.MessageVo;

@Controller
@RequestMapping(Constant.API_MOBI + "msg/")
public class MsgController extends BaseController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MsgService msgService;

	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		
		Map<String,Object> ret = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(json.get("before")) || StringUtils.isEmpty(json.get("after"))) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "miss parameters 'after' or 'before'");
			return ret;
		}
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		Date before = new Date(Long.valueOf(json.get("before")));
		Date after = new Date(Long.valueOf(json.get("after")));

		Page page = msgService.listByUser(userId, after, before, startIndex, pageSize);

		List<MessageVo> vols = new ArrayList<MessageVo>();
		for (Object obj : page.getItems()) {
			SysMessageSend po = (SysMessageSend)obj;
			MessageVo vo = new MessageVo();
			SysMessage msg = po.getMessage();
			BeanUtilEx.copyProperties(vo, msg);
			vo.setSendTime(po.getSendTime());
			vols.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vols);
		ret.put("totalPages", page.getTotalPages());
		return ret;
	}

	@RequestMapping(value="detail")
	@ResponseBody
	public Map<String,Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String id = json.get("id");
		if (StringUtils.isEmpty(id)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Object obj = msgService.get(SysMessage.class, id);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		SysMessage po = (SysMessage) obj;

		MessageVo vo = new MessageVo();
		BeanUtilEx.copyProperties(vo, po);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}
}
