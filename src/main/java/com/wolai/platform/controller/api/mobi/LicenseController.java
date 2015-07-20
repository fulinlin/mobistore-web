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
import com.wolai.platform.entity.SysMessage;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.LicenseService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.CommonUtils;
import com.wolai.platform.vo.LicenseVo;
import com.wolai.platform.vo.MessageVo;

@Controller
@RequestMapping(Constant.API_MOBI + "license/")
public class LicenseController extends BaseController {
	
	@Autowired
	LicenseService licensePlateService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		
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
		String isPostpaid = json.get("isPostpaid");
		
		if (StringUtils.isEmpty(carNo) || StringUtils.isEmpty(frameNumber) 
				|| StringUtils.isEmpty(brand) || StringUtils.isEmpty(isPostpaid) ) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		License lincense = licensePlateService.getLincense(carNo);
		if (lincense != null && !"TEMP".equals(lincense.getUser().getCustomerType())) {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "车牌已注册");
			return ret;
		} else if (lincense != null && "TEMP".equals(lincense.getUser().getCustomerType())) { 
			// 用户已经被导入
			lincense.setUserId(user.getId());
			licensePlateService.create(lincense);
		} else {
			// 新创建车牌
			License po = new License();
			po.setCarNo(json.get("carNo"));
			po.setFrameNumber(json.get("frameNumber"));
			po.setBrand(json.get("brand"));
			po.setIsPostpaid(Boolean.valueOf(json.get("isPostpaid")));
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
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		License po = licensePlateService.getLicense(id, user.getId());
		if (po == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		po.setCarNo(json.get("carNo"));
		po.setFrameNumber(json.get("frameNumber"));
		po.setBrand(json.get("brand"));
		po.setIsPostpaid(Boolean.valueOf(json.get("isPostpaid")));
		
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
}
