package com.tinypace.mobistore.controller.api.mobi;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.Bill;
import com.tinypace.mobistore.entity.License;
import com.tinypace.mobistore.entity.ParkingRecord;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.entity.UnionpayCardBound;
import com.tinypace.mobistore.entity.Bill.PayType;
import com.tinypace.mobistore.service.AssetService;
import com.tinypace.mobistore.service.BillService;
import com.tinypace.mobistore.service.ParkingLotService;
import com.tinypace.mobistore.service.ParkingService;
import com.tinypace.mobistore.service.PaymentService;
import com.tinypace.mobistore.service.PaymentUnionpayService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.util.EncodeUtils;
import com.tinypace.mobistore.util.Encodes;
import com.tinypace.mobistore.util.FileUtils;
import com.tinypace.mobistore.util.IdGen;
import com.tinypace.mobistore.vo.AlipayVo;
import com.tinypace.mobistore.vo.BillVo;
import com.tinypace.mobistore.vo.LicenseVo;
import com.tinypace.mobistore.vo.ParkingLotVo;
import com.tinypace.mobistore.vo.ParkingVo;
import com.tinypace.mobistore.vo.UnionpayCardBoundVo;
import com.tinypace.mobistore.vo.UnionpayVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/unionpay/delegate/")
public class PaymentUnionpayDelegateController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(PaymentUnionpayDelegateController.class);
	
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
	
	// 银联卡绑定查询
	@RequestMapping(value="boundQuery")
	@ResponseBody
	public Map<String,Object> boundQuery(HttpServletRequest request){
		Map<String,Object> ret = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		
		UnionpayCardBound bound = paymentUnionpayService.boundQueryByUser(userId);
		if (bound == null) {
			ret.put("code", RespCode.SUCCESS.Code());
			ret.put("payMode", user.getPayType());
			ret.put("data", null);
			return ret;
		}
		UnionpayCardBoundVo vo = new UnionpayCardBoundVo();
		
		if(bound != null) {
			BeanUtilEx.copyProperties(vo, bound);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("payMode", user.getPayType());
		ret.put("data", vo);
		return ret;
	}
	
	// 银联卡绑定
	@RequestMapping(value="bound")
	@ResponseBody
	public Map<String,Object> bound(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>();
		
		String sign = json.get("sign");
		String unSign = EncodeUtils.unSign(sign);
		JSONObject jsonObj = JSONObject.parseObject(unSign);
		
		String accNo = jsonObj.getString("accNo");
		String certifId = jsonObj.getString("certifId");
		String cvn = jsonObj.getString("cvn");
		String expired = jsonObj.getString("expired");
		
		if (StringUtils.isEmpty(accNo) || StringUtils.isEmpty(certifId) || StringUtils.isEmpty(cvn) || StringUtils.isEmpty(expired)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		expired = "" + expired.substring(2,4) + expired.substring(0,2);
		
		UnionpayCardBound po1 = paymentUnionpayService.boundQueryByCard(accNo);
		if (po1 != null) {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "该卡已经绑定");
			return ret;
		}
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		
		String wolaiTradeNo = IdGen.uuid();
		Map<String, String> resMap = paymentUnionpayService.boundPers(userId, wolaiTradeNo, accNo, certifId, cvn, expired);
		if ("00".equals(resMap.get("respCode"))) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "银联卡绑定失败");
		}
		
		ret.put("data", resMap);
		return ret;
	}
	
	// 银联卡解绑
	@RequestMapping(value="unbound")
	@ResponseBody
	public Map<String,Object> unbound(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret = new HashMap<String, Object>(); 
		String orderId = json.get("orderId");
		
		if (StringUtils.isEmpty(orderId)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();
		
		Map<String, String> resMap = paymentUnionpayService.unboundPers(userId, orderId);
		if ("00".equals(resMap.get("respCode"))) {
			ret.put("code", RespCode.SUCCESS.Code());
		} else {
			ret.put("code", RespCode.BIZ_FAIL.Code());
			ret.put("msg", "银联卡解绑失败");
		}
		ret.put("data", resMap);
		return ret;
	}
	
	@RequestMapping(value="confirmPostPay")
	@ResponseBody
	public Map<String,Object> confirmPostPay(HttpServletRequest request, @RequestBody Map<String, String> json){
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
		Bill bill = paymentService.createBillIfNeededPersAndUpdateCouponPers(park, couponId, true, PayType.UNIONPAY);
		
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}

	// 银联代扣回调
	@AuthPassport(validate=false)
	@RequestMapping(value="callback")
	@ResponseBody
	public String callback(HttpServletRequest request){
		log.info("银联代扣回调===");
		
		Map<String, String> resp = paymentUnionpayService.getUnionpayResp(request);
		
		log.info(resp.toString());
		
		paymentUnionpayService.callbackPers(resp);
		
		return "success";
	}
	
}
