package com.wolai.platform.controller.api;

import java.util.Calendar;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.ParkingRecord.ParkStatus;
import com.wolai.platform.entity.SysUser.PayType;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.LicenseService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.PaymentService;
import com.wolai.platform.service.PaymentUnionpayService;
import com.wolai.platform.util.Encodes;
import com.wolai.platform.util.Exceptions;
import com.wolai.platform.util.StringUtil;
import com.wolai.platform.vo.EntranceNoticeVo;
import com.wolai.platform.vo.PaycheckVo;
import com.wolai.platform.vo.PaychekResponseVo;

@Controller
@RequestMapping(Constant.API_EX)
public class ApiController extends BaseController {

	@Autowired
	private ParkingService parkingService;
	
	@Autowired
	private BillService billService;

	@Autowired
	private LicenseService licenseService;

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentUnionpayService paymentUnionpayService;
	
	private static Integer NOT_WOLAI_USER=2;
	private static Integer IS_WOLAI_USER=1;
	
	private static Log log = LogFactory.getLog(ApiController.class);
	
	/**
	 * 进场信息通知接口
	 * 
	 * @param vo
	 * @return
	 */
	
	@RequestMapping("enterNotice")
	@ResponseBody
	public Map<String,Object> entranceNotice(@RequestBody String sign, HttpServletRequest request) {
		Map<String,Object> result = Maps.newHashMap();
		EntranceNoticeVo vo =getRequestParameter(EntranceNoticeVo.class,sign);
		if (vo==null || !beanValidator(result, vo)) {
			result.put("code", -100);
			return result;
		}

		String parkingLotId = getParkingLotId(request);

		License license = licenseService.getLincense(vo.getCarNo().trim());

		// 检测是否有未付费账单
		if(billService.hasUnPayedBill(vo.getCarNo().trim())){
			result.put("code",NOT_WOLAI_USER);
			result.put("msg", "喔来客户，但是存在未结清账单！");
			return result;
		}
		
		// 防止多次调用
		ParkingRecord record = parkingService.getParkingRecordbyExNo(vo.getCarNo(),parkingLotId);
		
		if (record == null) {
			record = new ParkingRecord();
		}
		
		if (license != null) {
			record.setCarNoId(license.getId());
			
		}
		
		if(license==null || UserType.TEMP.equals(license.getUser().getCustomerType())){
			result.put("code",NOT_WOLAI_USER);
		}else{
			result.put("code",IS_WOLAI_USER);
		}
		if(license!=null){
			record.setUserId(license.getUserId());
		}
		record.setCarNo(vo.getCarNo().trim());
		record.setCarPicPath(vo.getCarPicUrl().trim());
		record.setEntranceNo(vo.getEntranceNo().trim());
		record.setExNo(vo.getExNo());
		record.setParkingLotId(parkingLotId);
		record.setParkStatus(ParkStatus.IN);
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(vo.getEnterTime());
		record.setDriveInTime(ca.getTime());
		parkingService.savePaingRecord(record);

		result.put("msg", "success");
		return result;
	}

	/**
	 * 出厂通知接口
	 * 
	 * @param vo
	 * @return
	 */
	
	@RequestMapping("payCheck")
	@ResponseBody
	public Object payCheck(@RequestBody String  sign, HttpServletRequest request) {
		Map<String,Object> result = Maps.newHashMap();
		PaycheckVo vo =getRequestParameter(PaycheckVo.class,sign);
		if (vo==null || !beanValidator(result, vo)) {
			result.put("code", -100);
			return result;
		}
		
		PaychekResponseVo responseVo = new PaychekResponseVo();
		String parkingLotId = getParkingLotId(request);
		// 获取请求停车记录
		ParkingRecord record = parkingService.getParkingRecordbyExNo(vo.getExNo(),parkingLotId);
	
		if(record==null){   // 没有停车记录即为非喔来用户
			parkingService.deleteTempRecord(vo.getExNo());
			responseVo.setExNo(vo.getExNo());
			responseVo.setIsPaid(false);
			responseVo.setCode(NOT_WOLAI_USER);
		}else{
			/* 根据外部接口修正停车记录相关信息*/
			
			//  出库时间
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(vo.getExitTime());
			record.setDriveOutTime(ca.getTime());
			
			// 修正入库时间
			Calendar enterCa = Calendar.getInstance();
			enterCa.setTimeInMillis(vo.getEnterTime());
			record.setDriveInTime(enterCa.getTime());
			
			// 出口编号
			record.setExportNo(vo.getExportNo());
			
			// 拼装返回数据
			responseVo.setCarNo(record.getCarNo());
			responseVo.setEnterTime(record.getDriveInTime().getTime());
			responseVo.setExitTime(record.getDriveOutTime().getTime());
			responseVo.setExNo(record.getExNo());
			responseVo.setExportNo(record.getExportNo());
			
			/* 出账处理 */
			Bill bill = billService.getBillByParking(record.getId());
			
			// 除支付成功的账单不处理，其他情况计算下账单信息
			if(!(bill !=null && ( PayStatus.SUCCESSED.equals(bill.getPayStatus()) || PayStatus.IN_PROGRESS.equals(bill.getPayStatus()) ) )){
				boolean isPostPay =false;
				
				// 用户为后付费用户的情况
				isPostPay = PayType.POSTPAID.equals(record.getUser().getPayType());
				
				if(!isPostPay){
					// 确认后付费的用户：只有确认后才进行后付费扣款
					// 当前状态为预付费的用户：账单为后付费账单
					isPostPay= bill !=null && bill.getIsPostPay();
				}
				
				bill = paymentService.createBillIfNeededWithoutUpdateCouponPers(record,isPostPay);
			}
			
			/*
			 * 是否已付费检查：
			 * 1.手动付费账单，检查是否已付费，以及付费金额是否足额
			 * 2.后付费用户，新建待扣费账单，直接返回已付费
			 */
			if(bill.getIsPostPay()){
				// 后付费账单直接放行
				responseVo.setIsPaid(true);
				// 调用一次扣费接口
				try{
					paymentUnionpayService.postPayBillSattlement(bill.getId(),record.getUserId());
				}catch(Exception e){
					if(log.isWarnEnabled()){
						log.warn(Exceptions.getStackTraceAsString(e));
					}
				}
			}else{
				// 预付费账单检查是否已缴费成功
				if(PayStatus.SUCCESSED.equals(bill.getPayStatus())){
					responseVo.setIsPaid(true);
				}else{
					// 未缴费成功的账单，更改缴费方式为现金缴费，同时释放曾经选择的优惠券
					responseVo.setIsPaid(false);
					bill = paymentService.releaseCashPaidCouponsPers(bill.getId());
				}
			}
			// 更新记录为离场
			record.setParkStatus(ParkStatus.OUT);
			parkingService.saveOrUpdate(record);
			
			/* 
			 * 拼接返回字段
			 */
			responseVo.setCode(IS_WOLAI_USER);
			responseVo.setPayTime(bill.getCreateTime().getTime());
			responseVo.setOrderCreateTime(bill.getCreateTime().getTime());
			responseVo.setOrderId(bill.getId());
			// 如果选择了优惠券进行结算的话，返回优惠券的id以及相关信息
			if(StringUtils.isNotBlank(bill.getCouponId())){
				responseVo.setCouponSn(bill.getCouponId());
				Object couponObj = paymentService.get(Coupon.class, bill.getCouponId()); 
				Coupon coupon = null;
				if (couponObj != null) {
					coupon = (Coupon)couponObj;
					responseVo.setCouponTime(coupon.getTime());
					responseVo.setCouponAmount(coupon.getMoney());
				}
			}
			responseVo.setPayAmount(StringUtil.formatMoney(bill.getPayAmount()));
		}
		return responseVo;
	}
	private String getParkingLotId(HttpServletRequest request) {
		return request.getAttribute(Constant.REQUEST_PARINGLOTID).toString();
	}
	
	private <E> E  getRequestParameter(Class<E> voClass,String sign){
		sign = (String) JSONObject.parseObject(sign).get("sign");
		if(StringUtils.isNotBlank(sign)){
			String MD5key = sign.substring(0, 32);
			String aesKey  = MD5key.substring(0,3)+MD5key.substring(8,11)+MD5key.substring(19, 22);// 1-3,9-11,20-22
			String jsonString=new String(Encodes.decodeAES(sign.substring(32),aesKey));
				return JSON.parseObject(jsonString,voClass);
		}
		return null;
	}
}
