package com.tinypace.mobistore.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ClientVo;
import com.tinypace.mobistore.vo.SearchHotVo;

@Controller
@RequestMapping(Constant.API + "client/")
public class ClientAction extends BaseController {
	
	@Autowired
	ClientService clientService;
	
	@AuthPassport(validate=false)
	@RequestMapping(value = "opt/signon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doSomething(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String mobile = json.get("mobile");
		String password = json.get("password");
		String platform = json.get("platform");
		String agent = json.get("agent");
		String deviceToken = json.get("deviceToken");

		StrClient client = clientService.signonPers(mobile, password, platform, agent, deviceToken);
		if (client != null) {
			ret.put("token", client.getAuthToken());
			
			ClientVo vo = new ClientVo();
			BeanUtilEx.copyProperties(vo, client);
			ret.put("data", vo);
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "登录失败");
		}
		
		return ret;
	}
	
	@RequestMapping(value = "opt/info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> info(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		ClientVo clientVo = new ClientVo();
		BeanUtilEx.copyProperties(clientVo, client);
		
		Map<String, Long> counts = clientService.count(client.getId());

		ret.put("collectionCount", counts.get("collectionCount"));
		ret.put("msgCount", counts.get("msgCount"));
		
		ret.put("waitPayCount", counts.get("waitPayCount"));
		ret.put("waitShip", counts.get("waitShip"));
		ret.put("waitReceive", counts.get("waitReceive"));
		ret.put("waitRate", counts.get("waitRate"));
		
		ret.put("client", clientVo);
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
}
