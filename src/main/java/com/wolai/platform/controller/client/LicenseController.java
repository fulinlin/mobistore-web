package com.wolai.platform.controller.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.wolai.platform.entity.FeedBack;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.LicensePlateService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.LicenseVo;
import com.wolai.platform.vo.ParkingLotVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "license/")
public class LicenseController {
	
	@Autowired
	LicensePlateService licensePlateService;
	
	@Autowired
	UserService userService;

	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>();
		
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		List<LicenseVo> vols = new ArrayList<LicenseVo>();
		Page page = licensePlateService.listByUser(userId);
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
	
	@AuthPassport(validate=true)
	@RequestMapping(value="create")
	@ResponseBody
	public Map<String,Object> create(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		SysUser user = userService.getUserByToken(token);
		
		License po = new License();
		po.setCarNo(json.get("carNo"));
		po.setFrameNumber(json.get("frameNumber"));
		po.setBrand(json.get("brand"));
		po.setIsPostpaid(Boolean.valueOf(json.get("isPostpaid")));
		po.setUserId(user.getId());
		
		licensePlateService.create(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");
		
		Object obj = licensePlateService.get(License.class, id);
		if (obj == null) {
			ret.put("code", RespCode.FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		License po = (License)obj;
		po.setCarNo(json.get("carNo"));
		po.setFrameNumber(json.get("frameNumber"));
		po.setBrand(json.get("brand"));
		po.setIsPostpaid(Boolean.valueOf(json.get("isPostpaid")));
		
		licensePlateService.update(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
}
