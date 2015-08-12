package com.wolai.platform.controller.api.mobi;

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

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.CouponService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.ParkingLotVo;
import com.wolai.platform.vo.ParkingVo;

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

			Bill bill = paymentService.createBillIfNeededWithoutUpdateCouponPers(po, SysUser.PayType.POSTPAID.equals(user.getPayType()));
			
			vo.setMoney(bill.getTotalAmount());
			vo.setPaidMoney(bill.getPayAmount());
		} else {
			ret.put("data", null);
		}
		
		long couponCount = couponService.countAllByUser(userId);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("couponCount", couponCount);
		
		if (SysUser.PayType.CONFIRM_POSTPAID.toString().equals(user.getPayType().toString()) ) {
			ret.put("confirmPostPay", true);
		} else {
			ret.put("confirmPostPay", false);
		}
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
		if (bill != null) {
			vo.setPaytype(bill.getPaytype());
			vo.setCouponType(bill.getCoupon().getType());
			vo.setCouponMoney(new BigDecimal(bill.getCoupon().getMoney()));
			vo.setCouponTime(bill.getCoupon().getTime());
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}
	
}
