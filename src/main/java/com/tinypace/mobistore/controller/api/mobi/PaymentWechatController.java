package com.tinypace.mobistore.controller.api.mobi;

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

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.Bill.PayStatus;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.entity.Bill.PayType;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.service.ParkingLotService;
import com.tinypace.mobistore.service.ParkingService;
import com.tinypace.mobistore.service.PaymentService;
import com.tinypace.mobistore.service.PaymentWechatService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.EncodeUtils;
import com.tinypace.mobistore.util.StringUtil;
import com.tinypace.mobistore.vo.AlipayVo;
import com.tinypace.mobistore.vo.UnionpayVo;
import com.tinypace.mobistore.vo.WechatVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/wechat/")
public class PaymentWechatController extends BaseController {
	
	private static Log log = LogFactory.getLog(PaymentWechatController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PaymentWechatService paymentWechatService;
	
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
		
	   String ip = request.getHeader("x-forwarded-for");
	   if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
	            ip = request.getRemoteAddr();
	   }
	   if (ip.indexOf(":") > -1) {
		   ip = "127.0.0.1";
	   }
	   log.info(ip);
		
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
		Bill bill = paymentService.createBillIfNeededPersAndUpdateCouponPers(park, couponId, false, PayType.ALIPAY);
		
		String wolaiTradeNo = bill.getId();
		BigDecimal totalAmount = bill.getTotalAmount();
		BigDecimal payAmount = bill.getPayAmount();
		WechatVo payVo = new WechatVo();
		payVo.setWolaiTradeNo(wolaiTradeNo);
		
		// TODO: 测试数据
//		payVo.setTotalAmount(totalAmount);
//		payVo.setPayAmount(payAmount);
		payVo.setTotalAmount(new BigDecimal(2));
		payVo.setPayAmount(new BigDecimal(1));
	
		Map<String, Object> resMap;
		try {
			resMap = paymentWechatService.preparePay(wolaiTradeNo, payVo.getPayAmount().multiply(new BigDecimal(100)).intValue(), ip);
			if (Boolean.valueOf(resMap.get("success").toString())) {

				payVo.setSign(resMap.get("sign").toString());
				payVo.setPrepayId(resMap.get("prepayId").toString());
				payVo.setSignStr(resMap.get("signStr").toString());
				
				payVo.setNonce_str(resMap.get("nonce_str").toString());
				payVo.setTimestamp(resMap.get("timestamp").toString());
				
				ret.put("code", RespCode.SUCCESS.Code());
			} else {
				ret.put("code", RespCode.BIZ_FAIL.Code());
				ret.put("msg", "微信支付服务不可用");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "微信支付服务不可用");
		}
		ret.put("data", payVo);
		return ret;
	}
	
	@RequestMapping(value="query")
	@ResponseBody
	public Map<String,Object> query(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String wolaiTradeNo = json.get("wolaiTradeNo");
		
		if (StringUtils.isEmpty(wolaiTradeNo)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Object obj = billService.get(Bill.class, wolaiTradeNo);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		Bill bill = (Bill) obj;
		PayStatus payStatus = bill.getPayStatus();
		if (PayStatus.SUCCESSED.equals(payStatus)) {
			ret.put("code", RespCode.SUCCESS.Code());
			ret.put("paySuccess", "true");
			return ret;
		} else if (PayStatus.FEATURE.equals(payStatus)){
			ret.put("code", RespCode.SUCCESS.Code());
			ret.put("paySuccess", "false");
			return ret;
		}
		
		Map<String, Object> resMap;
		try {
			resMap = paymentWechatService.query(wolaiTradeNo);
			if ( Boolean.valueOf(resMap.get("success").toString()) && Boolean.valueOf(resMap.get("paySuccess").toString()) ) {
				ret.put("code", RespCode.SUCCESS.Code());
				ret.put("paySuccess", "true");
			} else {
				ret.put("code", RespCode.SUCCESS.Code());
				ret.put("paySuccess", "false");
			}
			
			String returnCode = resMap.get("return_code").toString(); // 请求返回码
			String returnMsg = resMap.get("return_msg").toString(); // 消息
			String resultCode = resMap.get("result_code").toString(); // 交易状态
			String tradeState = resMap.get("trade_state").toString(); // 交易状态
			
			paymentWechatService.callbackPers(bill, returnCode, returnMsg, resultCode + "-" + tradeState, bill.getPayAmount().toString(), wolaiTradeNo);
		} catch (Exception e) {
			log.error(e.toString());
			ret.put("code", RespCode.SUCCESS.Code());
			ret.put("paySuccess", "false");
		}

		return ret;
	}

	@AuthPassport(validate=false)
	@RequestMapping(value="callback")
	@ResponseBody
	public String callback(HttpServletRequest request){
		String returnCode = request.getParameter("return_code"); // 请求返回码
		String returnMsg = request.getParameter("return_msg"); // 消息
		String tradeStatus = request.getParameter("result_code"); // 交易状态
		String totalFee = request.getParameter("total_fee"); // 交易金额
		String wolaiTradeNo = request.getParameter("out_trade_no"); // 喔来交易号
		
		if (StringUtil.IsEmpty(returnCode) || StringUtil.IsEmpty(returnMsg) || StringUtil.IsEmpty(tradeStatus)
				|| StringUtil.IsEmpty(totalFee) || StringUtil.IsEmpty(wolaiTradeNo)) {
			log.info("微信异步通知接口参数错误");
			return "error";
		}
		
		if (!"SUCCESS".equals(returnCode)) {
			log.info("收到微信异步通知接口'" + returnCode + "'类型的请求，不处理！");
			return "success";
		}
		
		Object obj = billService.get(Bill.class, wolaiTradeNo);
		if (obj == null) {
			log.info("微信通知接口-未找到out_trade_no对应的Bill对象");
			return "error";
		}
		
		Bill bill = (Bill) obj;
		BigDecimal amount = new BigDecimal(totalFee).divide(new BigDecimal(100));
		paymentWechatService.callbackPers(bill, returnCode, returnMsg, tradeStatus, amount.toString(), wolaiTradeNo);
		log.info("微信通知接口返回：" +wolaiTradeNo + "-" + tradeStatus);
		
		return "success";
	}
}
