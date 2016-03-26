package com.tinypace.mobistore.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrMsg;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CollectionService;
import com.tinypace.mobistore.service.MsgService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.MsgVo;
import com.tinypace.mobistore.vo.ProductVo;

@Controller
@RequestMapping(Constant.API + "msg/")
public class MsgAction extends BaseController {

	@Autowired
	MsgService msgService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody Object json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);

		Page page = msgService.list(client.getId(), 0, 10);
		List<MsgVo> vos = new ArrayList<MsgVo>();
		for (Object obj : page.getItems()) {
			StrMsg po = (StrMsg) obj;
			MsgVo vo = new MsgVo();
			BeanUtilEx.copyProperties(vo, po);
			vos.add(vo);
		}

		ret.put("data", vos);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		String msgId = json.get("msgId");

		StrMsg po = (StrMsg) msgService.get(StrMsg.class, msgId);

		MsgVo vo = new MsgVo();
		BeanUtilEx.copyProperties(vo, po);

		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);

		return ret;
	}
}
