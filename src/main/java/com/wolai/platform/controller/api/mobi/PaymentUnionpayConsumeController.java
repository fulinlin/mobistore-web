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
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.service.PaymentUnionpayService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.FileUtils;
import com.wolai.platform.vo.AlipayVo;
import com.wolai.platform.vo.BillVo;
import com.wolai.platform.vo.ParkingLotVo;
import com.wolai.platform.vo.ParkingVo;
import com.wolai.platform.vo.UnionpayVo;

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
		Bill bill = paymentService.createBillIfNeededPersAndUpdateCouponPers(park, couponId, false);
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
		
		Map<String, String> resMap = paymentUnionpayService.prepareTrans(wolaiTradeNo, payAmount.multiply(new BigDecimal(100)).intValue());
		payVo.setPayTradeNo(resMap.get("tn"));
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", payVo);
		return ret;
	}
	
	// 银联消费回调
	@AuthPassport(validate=false)
	@RequestMapping(value="callback")
	@ResponseBody
	public String unionpayConsumeCallback(HttpServletRequest request){
		Map<String, String[]> params = request.getParameterMap(); 
		log.info("银联消费回调===" + params.toString());
		
		String[] merId = params.get("merId");
		String[] orderId = params.get("orderId");
		String[] txnAmt = params.get("merId");
		String[] queryId = params.get("orderId");
		String[] payCardNo = params.get("trade_status");
		String[] payCardIssueName = params.get("merId");
		// TODO: 
		
		return "";
	}
	
}
