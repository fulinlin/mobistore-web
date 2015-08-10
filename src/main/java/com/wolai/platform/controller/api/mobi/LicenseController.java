package com.wolai.platform.controller.api.mobi;

import java.util.ArrayList;
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

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.License.LICENSE_COLOR;
import com.wolai.platform.entity.SysCarBrand;
import com.wolai.platform.entity.SysCarModel;
import com.wolai.platform.entity.SysMessage;
import com.wolai.platform.entity.SysMessageSend;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.CarService;
import com.wolai.platform.service.LicenseService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.CommonUtils;
import com.wolai.platform.vo.BrandVo;
import com.wolai.platform.vo.LicenseVo;
import com.wolai.platform.vo.MessageVo;
import com.wolai.platform.vo.ModelVo;

@Controller
@RequestMapping(Constant.API_MOBI + "license/")
public class LicenseController extends BaseController {
	
	@Autowired
	LicenseService licensePlateService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CarService carService;

	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestParam String token){
		
		Map<String,Object> ret =new HashMap<String, Object>();
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();

		List<LicenseVo> vols = new ArrayList<LicenseVo>();
		Page page = licensePlateService.listByUser(userId, 0, 100);
		for (Object obj : page.getItems()) {
			License po = (License)obj;
			LicenseVo vo = new LicenseVo();
			BeanUtilEx.copyProperties(vo, po);
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
		
		Object obj = licensePlateService.get(License.class, id);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		License po = (License) obj;

		LicenseVo vo = new LicenseVo();
		BeanUtilEx.copyProperties(vo, po);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}

	@RequestMapping(value="create")
	@ResponseBody
	public Map<String,Object> create(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String carNo = json.get("carNo");
		String frameNumber = json.get("frameNumber");
		String brand = json.get("brand");
		String model = json.get("model");
		String color = json.get("color");
		
		if (StringUtils.isEmpty(carNo) 
				|| StringUtils.isEmpty(brand) || StringUtils.isEmpty(model) || StringUtils.isEmpty(color)
				|| LICENSE_COLOR.value(color) == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		String regex = ".*[A-Za-z0-9]{5}$";
		if (!carNo.matches(regex)) {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "车牌后五位只能是字母或数字");
			return ret;
		}
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		License lincense = licensePlateService.getLincense(carNo);
		if (lincense != null && !"TEMP".equals(lincense.getUser().getCustomerType())) {
			ret.put("code", RespCode.BIZ_FAIL_2.Code());
			ret.put("msg", "车牌已注册");
			return ret;
		} else if (lincense != null && "TEMP".equals(lincense.getUser().getCustomerType())) { 
			// 用户已经被导入
			lincense.setUserId(user.getId());
			licensePlateService.create(lincense);
		} else {
			// 新创建车牌
			License po = new License();
			po.setCarNo(carNo.toUpperCase());
			po.setFrameNumber(frameNumber);
			po.setBrand(brand);
			po.setModel(model);
			po.setColor(LICENSE_COLOR.value(color));
			
			po.setUserId(user.getId());
			
			licensePlateService.create(po);
		}

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");
		String color = json.get("color");
		String carNo = json.get("carNo");
		String frameNumber = json.get("frameNumber");
		String brand = json.get("brand");
		String model = json.get("model");
		
		if (StringUtils.isEmpty(id) || 
				(color != null && LICENSE_COLOR.value(color) == null) ) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		License po = licensePlateService.getLicense(id, user.getId());
		if (po == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		if (!StringUtils.isEmpty(carNo)) {
			po.setCarNo(carNo);
		}
		if (!StringUtils.isEmpty(frameNumber)) {
			po.setFrameNumber(frameNumber);
		}
		if (!StringUtils.isEmpty(brand)) {
			po.setBrand(brand);
		}
		if (!StringUtils.isEmpty(model)) {
			po.setModel(model);
		}

		if (!StringUtils.isEmpty(color)) {
			po.setColor(LICENSE_COLOR.value(color));
		}
		
		licensePlateService.update(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="remove")
	@ResponseBody
	public Map<String,Object> remove(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		Object obj = licensePlateService.getLicense(id, user.getId());
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		License po = (License)obj;
		po.setIsDelete(true);
		
		licensePlateService.update(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@RequestMapping(value="listBrand")
	@ResponseBody
	public Map<String,Object> listBrand(HttpServletRequest request){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		List<SysCarBrand> ls = carService.listBrand();
		
		List<BrandVo> vols = new ArrayList<BrandVo>();
		for (SysCarBrand po : ls) {
			BrandVo vo = new BrandVo();
			BeanUtilEx.copyProperties(vo, po);
			vols.add(vo);
		}

		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vols);
		return ret;
	}
	
	@RequestMapping(value="listModel")
	@ResponseBody
	public Map<String,Object> listModel(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String brandId = json.get("brandId");
		if (StringUtils.isEmpty(brandId)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		List<SysCarModel> ls = carService.listModelByBrand(brandId);
		
		List<ModelVo> vols = new ArrayList<ModelVo>();
		for (SysCarModel po : ls) {
			ModelVo vo = new ModelVo();
			BeanUtilEx.copyProperties(vo, po);
			vols.add(vo);
		}

		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vols);
		return ret;
	}
}
