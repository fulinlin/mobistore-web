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

import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.ParkingVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "parking/")
public class ParkingController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	ParkingService parkingService;
	
	@Autowired
	BillService billService;

	@RequestMapping(value="packInfo")
	@ResponseBody
	public Map<String,Object> packInfo(HttpServletRequest request, @RequestParam String token){
		
		Map<String,Object> ret = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		Page page = parkingService.packInfo(userId);
		
		List<ParkingVo> parkVoList = new ArrayList<ParkingVo>();
		
		for (Object obj : page.getItems()) {
			ParkingRecord po = (ParkingRecord) obj;
			ParkingVo vo = new ParkingVo();
			BeanUtilEx.copyProperties(vo, po);
			parkVoList.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", parkVoList);
		return ret;
	}

	@RequestMapping(value="packHistory")
	@ResponseBody
	public Map<String,Object> packHistory(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String,Object> ret = new HashMap<String, Object>();
		
		SysUser user = userService.getUserByToken(token);
		String userId = user.getId();
		Page page = parkingService.packHistory(userId, startIndex, pageSize);
		
		List<ParkingVo> parkVoList = new ArrayList<ParkingVo>();
		
		for (Object obj : page.getItems()) {
			ParkingRecord po = (ParkingRecord) obj;
			ParkingVo vo = new ParkingVo();
			BeanUtilEx.copyProperties(vo, po);
			parkVoList.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", parkVoList);
		return ret;
	}
	
	@RequestMapping(value="packHistoryDetail")
	@ResponseBody
	public Map<String,Object> packHistoryDetail(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		String id = json.get("id");
		
		ParkingRecord park = (ParkingRecord) parkingService.get(ParkingRecord.class, id);
		Bill bill = billService.getBillByPacking(park.getId());
		
		ParkingVo vo = new ParkingVo();
		BeanUtilEx.copyProperties(vo, park);
		if (bill != null) {
			vo.setPaytype(bill.getPaytype());
			vo.setCouponType(bill.getCoupon().getType());
			vo.setCouponMoney(bill.getCoupon().getMoney());
			vo.setCouponTime(bill.getCoupon().getTime());
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}
	
}
