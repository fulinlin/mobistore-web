package com.wolai.platform.controller.api.mobi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.EncodeUtils;
import com.wolai.platform.util.StringUtil;
import com.wolai.platform.vo.AlipayVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/alipay/")
public class PaymentAlipayController extends BaseController {
	
	private static Log log = LogFactory.getLog(PaymentAlipayController.class);
	
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

	@RequestMapping(value="prepare")
	@ResponseBody
	public Map<String,Object> prepare(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String parkingId = json.get("parkingId");
		String couponId = json.get("couponId");
		String clientType = json.get("clientType");
		
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
		Bill bill = paymentService.createBillIfNeededPersAndUpdateCouponPers(park, couponId, false, PayType.ALIPAY);
		AlipayVo alipayVo = new AlipayVo();
		alipayVo.setWolaiTradeNo(bill.getId());
		// TODO: 测试数据
//		alipayVo.setTotalAmount(bill.getTotalAmount());
//		alipayVo.setPayAmount(bill.getPayAmount());
		alipayVo.setTotalAmount(new BigDecimal(0.02));
		alipayVo.setPayAmount(new BigDecimal(0.01));
		
		String key = Constant.alipay_partnerPrivKey;
		if (clientType != null && "ios".equals(clientType.toLowerCase())) {
			key = Constant.alipay_partnerPrivKey_pkcs8;
		}
		key = EncodeUtils.sign(key);
		alipayVo.setPartnerPrivKey(key);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", alipayVo);
		return ret;
	}

	@AuthPassport(validate=false)
	@RequestMapping(value="callback")
	@ResponseBody
	public String callback(HttpServletRequest request){
		Map<String, String[]> params = request.getParameterMap(); 
		
		String[] wolaiTradeNo = params.get("out_trade_no"); // 订单交易号
		String[] alipayTradeNo = params.get("trade_no"); // 支付宝交易号
		String[] alipayTradeStatus = params.get("trade_status"); // 支付宝交易状态
		String[] alipayTotal = params.get("total_fee"); // 支付宝交易金额
		
		if (wolaiTradeNo ==null || alipayTradeNo ==null || alipayTradeStatus ==null || alipayTotal == null ||
				wolaiTradeNo.length == 0 || alipayTradeNo.length == 0 || alipayTradeStatus.length == 0 || alipayTotal.length == 0) {
			log.info("支付宝异步接口参数错误");
			return "error";
		}
		
		if (!"TRADE_SUCCESS".equals(alipayTradeStatus[0]) && !"TRADE_FINISHED".equals(alipayTradeStatus[0])) {
			log.info("收到支付宝异步接口'" + alipayTradeStatus[0] + "'类型的请求，不处理！");
			return "success";
		}
		
		Object obj = billService.get(Bill.class, wolaiTradeNo[0]);
		if (obj == null) {
			log.info("未找到out_trade_no对应的Bill对象");
			return "error";
		}
		
		Bill bill = (Bill) obj;
		if (alipayTradeNo[0].equals(bill.getTradeNo())) {
			log.info("trade_no参数跟Bill对象的TradeNo不匹配");
			return "error";
		}
		
		paymentService.alipayCallbackPers(bill, alipayTradeNo[0], alipayTradeStatus[0], alipayTotal[0], Bill.PayType.ALIPAY);
		log.info("支付宝交易返回：" + alipayTradeNo[0] + "-" + alipayTradeStatus[0]);
		
		return "success";
	}
}
