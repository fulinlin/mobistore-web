package com.tinypace.mobistore.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrClient.AgentType;
import com.tinypace.mobistore.entity.StrSuggestion;
import com.tinypace.mobistore.entity.SysConfig;
import com.tinypace.mobistore.entity.SysVerifyCode;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.service.SuggestionService;
import com.tinypace.mobistore.service.SysConfigService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ClientVo;

@Controller
@RequestMapping(Constant.API + "mine/")
public class MIneAction extends BaseController {
	
	@Autowired
	ClientService clientService;
	@Autowired
	SysConfigService configService;
	@Autowired
	SuggestionService suggestionService;
	
	@RequestMapping(value = "index", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> info(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		ret.put("data", data);
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		ClientVo clientVo = new ClientVo();
		BeanUtilEx.copyProperties(clientVo, client);
		
		Map<String, Long> counts = clientService.count(client.getId());
		
		SysConfig config = configService.getConfig();
		String rateUrl = "ios".equals(json.get("platform"))? config.getIosMkt(): config.getAndroidMkt();

		data.put("collectionCount", counts.get("collectionCount"));
		data.put("msgCount", counts.get("msgCount"));
		
		data.put("waitPayCount", counts.get("waitPayCount"));
		data.put("waitShip", counts.get("waitShip"));
		data.put("waitReceive", counts.get("waitReceive"));
		data.put("waitRate", counts.get("waitRate"));
		data.put("rateUrl", rateUrl);
		data.put("client", clientVo);
		
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
}
