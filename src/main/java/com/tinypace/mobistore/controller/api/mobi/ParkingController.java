package com.tinypace.mobistore.controller.api.mobi;

import java.math.BigDecimal;
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

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.Coupon;
import com.tinypace.mobistore.entity.ParkingLot;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.entity.Bill.PayType;
import com.tinypace.mobistore.entity.Coupon.CouponType;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.service.CouponService;
import com.tinypace.mobistore.service.ParkingLotService;
import com.tinypace.mobistore.service.ParkingService;
import com.tinypace.mobistore.service.PaymentService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ParkingLotVo;
import com.tinypace.mobistore.vo.ParkingVo;

@Controller
@RequestMapping(Constant.API_MOBI + "parking/")
public class ParkingController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	ParkingService parkingService;
	
	@Autowired
	ParkingLotService parkingLotService;
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	BillService billService;
	
	@Autowired
	PaymentService paymentService;

	@RequestMapping(value="parkInfo")
	@ResponseBody
	public Map<String,Object> parkInfo(HttpServletRequest request, @RequestParam String token){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		ParkingRecord po = parkingService.parkInfo(userId);

		ParkingVo vo = new ParkingVo();
		if (po != null) {
			BeanUtilEx.copyProperties(vo, po);
			
			ParkingLot parkingLotPo = po.getParkingLot();
			ParkingLotVo parkingLotVo = new ParkingLotVo();
			BeanUtilEx.copyProperties(parkingLotVo, parkingLotPo);
			vo.setParkingLotVo(parkingLotVo);
			
			if (vo.getCarPicPath() == null) { //TODO: 
				String baseUrl = request.getRequestURL().toString().split("api/")[0];
				vo.setCarPicPath(baseUrl + parkingLotPo.getImage());
			}
			ret.put("data", vo);
		} else {
			ret.put("data", null);
		}
		
		long couponCount = couponService.countAllByUser(userId);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("couponCount", couponCount);
		
		return ret;
	}

	@RequestMapping(value="parkHistory")
	@ResponseBody
	public Map<String,Object> parkHistory(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String baseUrl = request.getRequestURL().toString().split("api/")[0];
		
		SysUser user = userService.getUserByToken(token);
		String userId = user.getId();
		Page page = parkingService.parkHistory(userId, startIndex, pageSize);
		
		List<ParkingVo> parkVoList = new ArrayList<ParkingVo>();
		
		for (Object obj : page.getItems()) {
			ParkingRecord po = (ParkingRecord) obj;
			ParkingVo vo = new ParkingVo();
			BeanUtilEx.copyProperties(vo, po);
			
			Bill bill = billService.getBillByParking(po.getId());
			parkingService.setBillInfoForPark(vo, bill);
			
			ParkingLot parkingLotPo = po.getParkingLot();
			ParkingLotVo parkingLotVo = new ParkingLotVo();
			BeanUtilEx.copyProperties(parkingLotVo, parkingLotPo);
			vo.setParkingLotVo(parkingLotVo);
//			if (vo.getCarPicPath() == null) { //TODO: 
				vo.setCarPicPath(baseUrl + parkingLotPo.getImage());
//			}
			
			parkVoList.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", parkVoList);
		ret.put("totalPages", page.getTotalPages());
		return ret;
	}
	
	@RequestMapping(value="parkHistoryDetail")
	@ResponseBody
	public Map<String,Object> parkHistoryDetail(HttpServletRequest request, @RequestParam String token, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String id = json.get("id");
		if (StringUtils.isEmpty(id)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Object obj = parkingService.get(ParkingRecord.class, id);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		ParkingRecord park = (ParkingRecord) obj;
		Bill bill = billService.getBillByParking(park.getId());
		
		ParkingVo vo = new ParkingVo();
		BeanUtilEx.copyProperties(vo, park);
//		if (vo.getCarPicPath() == null) {  //TODO: 
			ParkingLot parkingLotPo = park.getParkingLot();
			String baseUrl = request.getRequestURL().toString().split("api/")[0];
			vo.setCarPicPath(baseUrl + parkingLotPo.getImage());
//		}
		
		parkingService.setBillInfoForPark(vo, bill);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}
	
}
