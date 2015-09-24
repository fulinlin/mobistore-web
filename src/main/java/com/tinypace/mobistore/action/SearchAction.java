package com.tinypace.mobistore.action;

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

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrSearchHistory;
import com.tinypace.mobistore.service.SearchService;

@Controller
@RequestMapping(Constant.API + "search/")
public class SearchAction extends BaseController {
	@Autowired
	SearchService searchService;

	@RequestMapping(value = "opt/history", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doSomething(HttpServletRequest request, @RequestBody Object json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		List<String> hots = searchService.getHot();
		List<String> histories = searchService.getHistory(client.getId());
		
		
		ret.put("code", 1);
		ret.put("hots", hots);
		ret.put("histories", histories);
		return ret;
	}
}
