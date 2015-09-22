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
import com.tinypace.mobistore.entity.StrProduct;
import com.tinypace.mobistore.service.CategoryService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ProductVo;

@Controller
@RequestMapping(Constant.API + "category/")
public class CategoryAction extends BaseController {
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value = "opt/listProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listByCatetory(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String categoryId = json.get("categoryId").toString();
		
		Page page = categoryService.listProudct(categoryId, 0, 10);
		List<ProductVo> cates = new ArrayList<ProductVo>();
		for (Object obj : page.getItems()) {
			StrProduct po = (StrProduct) obj;
			ProductVo vo = new ProductVo();
			BeanUtilEx.copyProperties(vo, po);
			cates.add(vo);
		}
		
		ret.put("data", cates);
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("msg", "成功");
		return ret;
	}
}
