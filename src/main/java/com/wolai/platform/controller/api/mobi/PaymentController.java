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
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.FileUtils;
import com.wolai.platform.vo.BillVo;
import com.wolai.platform.vo.ParkingLotVo;
import com.wolai.platform.vo.ParkingVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/")
public class PaymentController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(FileUtils.class);
	
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
		Bill bill = paymentService.createBillIfNeededPers(park, couponId);
		BillVo billVo = new BillVo();
		BeanUtilEx.copyProperties(billVo, bill);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", billVo);
		return ret;
	}
	
	@RequestMapping(value="pay")
	@ResponseBody
	public Map<String,Object> pay(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String out_trade_no = json.get("out_trade_no"); // 订单交易号
		String payType = json.get("payType");
		String tradeNo = json.get("tradeNo"); // 支付宝交易号
		
		if (StringUtils.isEmpty(out_trade_no) || StringUtils.isEmpty(payType) || StringUtils.isEmpty(tradeNo)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Object obj = billService.get(Bill.class, out_trade_no);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		Bill bill = (Bill) obj;
		paymentService.payPers(bill, payType, tradeNo);
		
		return ret;
	}
	
	@RequestMapping(value="alipayCallback")
	@ResponseBody
	public String alipayCallback(HttpServletRequest request){
		
		Object out_trade_no = request.getAttribute("out_trade_no"); // 订单交易号
		Object trade_no = request.getAttribute("trade_no"); // 支付宝交易号
		Object trade_status = request.getAttribute("trade_status"); // 支付宝交易状态
		
		if (out_trade_no == null || trade_no == null || trade_status == null) {
			log.error("支付宝异步接口参数错误");
			return "error";
		}
		
		if (!"TRADE_FINISHED".equals(out_trade_no) || !"TRADE_FINISHED".equals(out_trade_no)) {
			log.error("收到支付宝异步接口'" + out_trade_no + "'类型的请求，不处理！");
			return "success";
		}
		
		Object obj = billService.get(Bill.class, out_trade_no.toString());
		if (obj == null) {
			log.error("未找到out_trade_no对应的Bill对象");
			return "error";
		}
		
		Bill bill = (Bill) obj;
		if (trade_no.equals(bill.getTradeNo())) {
			log.error("trade_no参数跟Bill对象的TradeNo不匹配");
			return "error";
		}
		
		paymentService.successPers(bill, trade_no.toString(), trade_status.toString());
		log.info("支付宝交易返回：" + trade_no.toString() + "-" + trade_status.toString());
		
		return "success";
	}
}
