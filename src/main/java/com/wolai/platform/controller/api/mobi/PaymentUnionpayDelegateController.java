package com.wolai.platform.controller.api.mobi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.entity.UnionpayCardBound;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.service.PaymentUnionpayService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.util.FileUtils;
import com.wolai.platform.util.IdGen;
import com.wolai.platform.vo.AlipayVo;
import com.wolai.platform.vo.BillVo;
import com.wolai.platform.vo.LicenseVo;
import com.wolai.platform.vo.ParkingLotVo;
import com.wolai.platform.vo.ParkingVo;
import com.wolai.platform.vo.UnionpayCardBoundVo;
import com.wolai.platform.vo.UnionpayVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/unionpay/delegate/")
public class PaymentUnionpayDelegateController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(FileUtils.class);
	
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
		
		String accNo = json.get("accNo");
		String certifId = json.get("certifId");
		String cvn = json.get("cvn");
		String expired = json.get("expired");
		
		
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
		
		ret.put("code", RespCode.SUCCESS.Code());
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
		
		ret.put("code", RespCode.SUCCESS.Code());
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
		Bill bill = paymentService.createBillIfNeededPersAndUpdateCouponPers(park, couponId, true);
		
		ret.put("code", RespCode.SUCCESS.Code());
		return ret;
	}

	// 银联代扣回调
	@AuthPassport(validate=false)
	@RequestMapping(value="callback")
	@ResponseBody
	public String unionpayDelegateCallback(HttpServletRequest request){
		Map<String, String[]> params = request.getParameterMap(); 
		log.info(params.toString());
		
		String[] merId = params.get("merId");
		String[] orderId = params.get("orderId");
		String[] txnAmt = params.get("merId");
		String[] queryId = params.get("orderId");
		// TODO: 
		
		return "";
	}
	
}
