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

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.entity.StrSearchHot;
import com.tinypace.mobistore.service.SearchService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ProductVo;
import com.tinypace.mobistore.vo.SearchHotVo;

@Controller
@RequestMapping(Constant.API + "search/")
public class SearchAction extends BaseController {
	@Autowired
	SearchService searchService;
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> search(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String keywords = json.get("keywords");
		List<StrProduct> pos = searchService.search(keywords);
		
		List<ProductVo> vos = new ArrayList<ProductVo>();
		
		for (StrProduct po : pos) {
			ProductVo vo = new ProductVo();
			BeanUtilEx.copyProperties(vo, po);
			
			vos.add(vo);
		}
		
		ret.put("code", 1);
		ret.put("data", vos);
		return ret;
	}

	@RequestMapping(value = "getHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getHistory(HttpServletRequest request, @RequestBody Object json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		List<String> hots = searchService.getHot();
		List<String> histories = searchService.getHistory(client.getId());
		
		ret.put("code", 1);
		ret.put("hots", hots);
		ret.put("histories", histories);
		return ret;
	}
	
	@RequestMapping(value = "getMatchedKeywords", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMatchedKeywords(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String keywords = json.get("keywords");
		List<StrSearchHot> pos = searchService.getMatchedKeywords(keywords.trim());
		
		List<SearchHotVo> vos = new ArrayList<SearchHotVo>();
		
		for (StrSearchHot po : pos) {
			SearchHotVo vo = new SearchHotVo();
			BeanUtilEx.copyProperties(vo, po);
			
			vos.add(vo);
		}
		
		ret.put("code", 1);
		ret.put("data", vos);
		return ret;
	}

}
