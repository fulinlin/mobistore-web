package com.tinypace.mobistore.controller.api.mobi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.entity.Bill.PayType;
import com.tinypace.mobistore.service.AssetService;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.service.ParkingLotService;
import com.tinypace.mobistore.service.ParkingService;
import com.tinypace.mobistore.service.PaymentService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.util.FileUtils;
import com.tinypace.mobistore.vo.AlipayVo;
import com.tinypace.mobistore.vo.BillVo;
import com.tinypace.mobistore.vo.ParkingLotVo;
import com.tinypace.mobistore.vo.ParkingVo;
import com.tinypace.mobistore.vo.PayVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/")
public class PaymentController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	ParkingService parkingService;
	
	@Autowired
	ParkingLotService parkingLotService;
	
	@Autowired
	BillService billService;

	@RequestMapping(value="payInfo")
	@ResponseBody
	public Map<String,Object> payInfo(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		
		String parkingId = json.get("parkingId");
		String couponId = json.get("couponId");
		
		if (StringUtils.isEmpty(parkingId)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Object obj = parkingService.get(ParkingRecord.class, parkingId);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		ParkingRecord park = (ParkingRecord) obj;
		Bill bill = paymentService.createBillIfNeededPersAndUpdateCouponPers(park, couponId, false, null);
		PayVo payVo = new PayVo();

		payVo.setTotalAmount(bill.getTotalAmount());
		payVo.setPayAmount(bill.getPayAmount());
		
		payVo.setIsPaid(Bill.PayStatus.SUCCESSED.equals(bill.getPayStatus()) || Bill.PayStatus.IN_PROGRESS.equals(bill.getPayStatus()));
		payVo.setCanPay(Bill.PayStatus.INIT.equals(bill.getPayStatus()) 
				|| Bill.PayStatus.FEATURE.equals(bill.getPayStatus()));
		
		if (SysUser.PayType.CONFIRM_POSTPAID.toString().equals(user.getPayType().toString()) ) {
			payVo.setConfirmPostPay(true);
		} else {
			payVo.setConfirmPostPay(false);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", payVo);
		
		return ret;
	}
	
	@RequestMapping(value="payNone")
	@ResponseBody
	public Map<String,Object> payNone(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String parkingId = json.get("parkingId");
		String couponId = json.get("couponId");
		if (StringUtils.isEmpty(parkingId)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Bill bill = billService.getBillByParking(parkingId);
		if (bill == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "bill not found");
			return ret;
		}

		paymentService.payNonePers(bill, couponId);
		
		return ret;
	}

}
