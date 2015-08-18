package com.wolai.platform.controller.api.mobi;

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

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.FileUtils;
import com.wolai.platform.vo.AlipayVo;
import com.wolai.platform.vo.BillVo;
import com.wolai.platform.vo.ParkingLotVo;
import com.wolai.platform.vo.ParkingVo;
import com.wolai.platform.vo.PayVo;

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

		// TODO: 测试数据
//		payVo.setTotalAmount(bill.getTotalAmount());
//		payVo.setPayAmount(bill.getPayAmount());
		payVo.setTotalAmount(new BigDecimal(0.2));
		payVo.setPayAmount(new BigDecimal(0.1));
		
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
