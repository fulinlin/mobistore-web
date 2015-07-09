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
@RequestMapping(Constant.API_CLIENT + "licensePlate/")
public class LicenseController {
	
	@Autowired
	LicensePlateService licensePlateService;
	
	@Autowired
	UserService userService;

	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public List<LicenseVo> list(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		SysUser uesr = userService.getUserByToken(token);
		String userId = uesr.getId();

		List<LicenseVo> vols = new ArrayList<LicenseVo>();
		List<License> ls = licensePlateService.listByUser(userId);
		for (License licensePlate : ls) {
			LicenseVo vo = new LicenseVo();
			BeanUtilEx.copyProperties(vo, licensePlate);
			vols.add(vo);
		}
		
		return vols;
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
		
		licensePlateService.getDao().saveOrUpdate(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		SysUser user = userService.getUserByToken(token);
		String itemId = json.get("itemId");
		
		License po = (License) licensePlateService.getDao().get(License.class, itemId);
		po.setCarNo(json.get("carNo"));
		po.setFrameNumber(json.get("frameNumber"));
		po.setBrand(json.get("brand"));
		po.setIsPostpaid(Boolean.valueOf(json.get("isPostpaid")));
		
		licensePlateService.getDao().saveOrUpdate(po);

		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}
}
