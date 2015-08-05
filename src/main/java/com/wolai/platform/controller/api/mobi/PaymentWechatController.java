package com.wolai.platform.controller.api.mobi;

import java.util.HashMap;
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
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.service.WechatPayRequest;
import com.wolai.platform.util.FileUtils;
import com.wolai.platform.vo.WechatPayVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/wechat/")
public class PaymentWechatController extends BaseController {
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
	
	@Autowired
	WechatPayRequest wechatPayRequest;

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
		
		WechatPayVo payRequest = new WechatPayVo();

		String out_trade_no = bill.getId();
		payRequest.setOut_trade_no(out_trade_no);
		
		String total_fee = bill.getMoney().toBigInteger().toString();
		payRequest.setTotal_fee(Integer.valueOf(total_fee));
		
		String spbill_create_ip = json.get("spbill_create_ip");
		payRequest.setSpbill_create_ip(spbill_create_ip);
		
		String sign = wechatPayRequest.getSign(wechatPayRequest.toMap(payRequest));
		payRequest.setSign(sign);
		
		String result;
		try {
			result = wechatPayRequest.sendPost(Constant.wechat_pay_request_url, payRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		downloadBillResData = (DownloadBillResData) wechatPayRequest.getObjectFromXML(result, DownloadBillResData.class);

		
		ret.put("code", RespCode.SUCCESS.Code());
//		ret.put("data", payVo);
		return ret;
	}
	
	@AuthPassport(validate=false)
	@RequestMapping(value="callback")
	@ResponseBody
	public String wechatCallback(HttpServletRequest request){
		Map<String, String[]> params = request.getParameterMap(); 
		
		String[] return_code = params.get("return_code");
		String[] return_msg = params.get("return_msg");
		String[] result_code = params.get("result_code");
		String[] err_code = params.get("err_code"); 
		String[] err_code_des = params.get("err_code_des"); 
		String[] out_trade_no = params.get("out_trade_no"); 
		String[] total_fee = params.get("total_fee");
		
//		return_code
		return "";
	}
    
}
