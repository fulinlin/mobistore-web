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

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.BaseController;
import com.tinypace.mobistore.entity.StrClient;
import com.tinypace.mobistore.entity.StrOrder;
import com.tinypace.mobistore.entity.StrRecipient;
import com.tinypace.mobistore.entity.SysArea;
import com.tinypace.mobistore.entity.SysConfig;
import com.tinypace.mobistore.service.AreaService;
import com.tinypace.mobistore.service.ClientService;
import com.tinypace.mobistore.service.RecipientService;
import com.tinypace.mobistore.service.SysConfigService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.util.StringUtil;
import com.tinypace.mobistore.vo.AreaVo;
import com.tinypace.mobistore.vo.ClientVo;
import com.tinypace.mobistore.vo.OrderVo;
import com.tinypace.mobistore.vo.RecipientVo;

@Controller
@RequestMapping(Constant.API + "address/")
public class AddressAction extends BaseController {
	
	@Autowired
	ClientService clientService;
	@Autowired
	RecipientService recipientService;
	@Autowired
	AreaService areaService;
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);

		List<StrRecipient> ls = recipientService.list(client.getId());
		List<RecipientVo> vos = new ArrayList<RecipientVo>();
		
		for (StrRecipient po : ls) {
			RecipientVo vo = new RecipientVo();
			BeanUtilEx.copyProperties(vo, po);
			vos.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vos);
		return ret;
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String id = json.get("addressId");
		
		StrRecipient rec = (StrRecipient) recipientService.get(StrRecipient.class, id);
		RecipientVo vo = new RecipientVo();
		BeanUtilEx.copyProperties(vo, rec);
		
		ret.put("data", vo);
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StrClient client = (StrClient) request.getAttribute(Constant.REQUEST_USER);
		
		String id = json.get("id");
		String name = json.get("name");
		String phone = json.get("phone");
		String provice = json.get("provice");
		String city = json.get("city");
		String region = json.get("region");
		String proviceId = json.get("proviceId");
		String cityId = json.get("cityId");
		String regionId = json.get("regionId");
		String street = json.get("street");
		String address = json.get("address");
		String isDefault = json.get("defaultt");
		
		StrRecipient rec = null;
		if (StringUtil.IsEmpty(id)) {
			rec = new StrRecipient(); 
			rec.setClientId(client.getId());
		} else {
			rec = (StrRecipient) recipientService.get(StrRecipient.class, id);
		}
		
		rec.setName(name);
		rec.setPhone(phone);
		rec.setProvice(provice);
		rec.setCity(city);
		rec.setRegion(region);

		rec.setProviceId(Integer.valueOf(proviceId));
		rec.setCityId(Integer.valueOf(cityId));
		if (!StringUtil.IsEmpty(regionId)) {
			rec.setRegionId(Integer.valueOf(regionId));
		}
		
		rec.setStreet(street);
		rec.setAddress(address);
		rec.setDefaultt(Boolean.valueOf(isDefault));
		recipientService.saveOrUpdate(rec);
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
	
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> remove(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String addressId = json.get("addressId");
		StrRecipient rec = (StrRecipient) areaService.get(StrRecipient.class, addressId);
		areaService.remove(rec);
		
		ret.put("code", RespCode.SUCCESS.Code());
		
		return ret;
	}
	
	@RequestMapping(value = "getAreas", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAreas(HttpServletRequest request, @RequestBody Map<String, String> json) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		String type = json.get("type");
		String proviceId = json.get("proviceId");
		String cityId = json.get("cityId");

		List<SysArea> ls = areaService.list(type, proviceId, cityId);
		List<AreaVo> vos = new ArrayList<AreaVo>();
		
		for (SysArea po : ls) {
			AreaVo vo = new AreaVo();
			BeanUtilEx.copyProperties(vo, po);
			vos.add(vo);
		}
		ret.put("data", vos);
		return ret;
	}
}
