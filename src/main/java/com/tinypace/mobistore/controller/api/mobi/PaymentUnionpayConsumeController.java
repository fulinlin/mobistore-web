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
import com.tinypace.mobistore.entity.Bill.PayType;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.service.AssetService;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.service.ParkingLotService;
import com.tinypace.mobistore.service.ParkingService;
import com.tinypace.mobistore.service.PaymentService;
import com.tinypace.mobistore.service.PaymentUnionpayService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.util.FileUtils;
import com.tinypace.mobistore.vo.AlipayVo;
import com.tinypace.mobistore.vo.BillVo;
import com.tinypace.mobistore.vo.ParkingLotVo;
import com.tinypace.mobistore.vo.ParkingVo;
import com.tinypace.mobistore.vo.UnionpayVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/unionpay/consume/")
public class PaymentUnionpayConsumeController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(PaymentUnionpayConsumeController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PaymentUnionpayService paymentUnionpayService;
	
	@Autowired
	ParkingService parkingService;
	
	@Autowired
	ParkingLotService parkingLotService;
	
	@Autowired
	BillService billService;

	@RequestMapping(value="prepare")
	@ResponseBody
	public Map<String,Object> prepare(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
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
		Bill bill = paymentService.createBillIfNeededPersAndUpdateCouponPers(park, couponId, false, PayType.UNIONPAY);
		String wolaiTradeNo = bill.getId();
		BigDecimal totalAmount = bill.getTotalAmount();
		BigDecimal payAmount = bill.getPayAmount();
		UnionpayVo payVo = new UnionpayVo();
		payVo.setWolaiTradeNo(wolaiTradeNo);
		
		// TODO: 测试数据
//		payVo.setTotalAmount(totalAmount);
//		payVo.setPayAmount(payAmount);
		payVo.setTotalAmount(new BigDecimal(0.02));
		payVo.setPayAmount(new BigDecimal(0.01));
		
		Map<String, String> resMap = paymentUnionpayService.prepareTrans(wolaiTradeNo, payVo.getPayAmount().multiply(new BigDecimal(100)).intValue());
		payVo.setPayTradeNo(resMap.get("tn"));
		
		if ("00".equals(resMap.get("respCode"))) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "银联支付服务不可用");
		}
		ret.put("data", payVo);
		return ret;
	}
	
	// 银联消费回调
	@AuthPassport(validate=false)
	@RequestMapping(value="callback")
	@ResponseBody
	public String callback(HttpServletRequest request){
		log.info("银联消费回调===");
		
		Map<String, String> resp = paymentUnionpayService.getUnionpayResp(request);
		
		log.info(resp.toString());
		
		paymentUnionpayService.callbackPers(resp);
		
		return "success";
	}
	
}
