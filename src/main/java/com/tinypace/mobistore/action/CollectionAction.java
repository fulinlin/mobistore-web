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
import com.tinypace.mobistore.entity.StrCollection;
import com.tinypace.mobistore.entity.StrMsg;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CollectionService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.CollectionVo;
import com.tinypace.mobistore.vo.MsgVo;
import com.tinypace.mobistore.vo.ProductVo;

@Controller
@RequestMapping(Constant.API + "collection/")
public class CollectionAction extends BaseController {

	@Autowired
	CollectionService collectionService;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody Object json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);

		Page page = collectionService.list(client.getId(), 0, 10);
		List<CollectionVo> vos = new ArrayList<CollectionVo>();
		for (Object obj : page.getItems()) {
			StrCollection po = (StrCollection) obj;
			CollectionVo vo = new CollectionVo();
			BeanUtilEx.copyProperties(vo, po);
			BeanUtilEx.copyProperties(vo, po.getProduct());
			vos.add(vo);
		}

		ret.put("data", vos);
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}

	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();

		String msgId = json.get("msgId");

		StrCollection po = (StrCollection) collectionService.get(StrCollection.class, msgId);

		CollectionVo vo = new CollectionVo();
		BeanUtilEx.copyProperties(vo, po);

		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);

		return ret;
	}
}
