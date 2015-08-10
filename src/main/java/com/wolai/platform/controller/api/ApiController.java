package com.wolai.platform.controller.api;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.ParkingRecord.ParkStatus;
import com.wolai.platform.entity.SysUser.UserType;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.LicenseService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.util.Encodes;
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

		// 防止多次调用
		ParkingRecord record = parkingService.getParkingRecordbyExNo(vo.getCarNo(),parkingLotId);
		if (record == null) {
			record = new ParkingRecord();
		}
		// 存在已绑定车牌
		if (license != null) {
			record.setCarNoId(license.getId());
			if(UserType.TEMP.equals(license.getUser().getCustomerType())){
				result.put("code", 1);
			}
		}else{
			result.put("code", 2);
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
		
		ParkingRecord record = parkingService.getParkingRecordbyExNo(vo.getExNo(),parkingLotId);
		if(record==null){
			parkingService.deleteTempRecord(vo.getExNo());
			responseVo.setIsPaid(false);
			responseVo.setCode(2);
		}else{
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
			
			/*
			 * 1.非后付费人员，检查是否已付费，以及付费金额是否足额
			 * 2.确认后付费的用户，检查账单是否已付，以及付费金额是否足额
			 * 3.后付费用户，新建待扣费账单，直接返回已付费
			 */
			Bill bill = billService.getBillByParking(record.getId());
			if(bill==null){
				bill = new Bill();
				bill.setCarNo(record.getCarNo());
				bill.setLicensePlateId(record.getCarNoId());
				bill.setCreateTime(new Date());
				bill.setIsPostPay(true);
				bill.setParkingRecordId(record.getId());
				bill.setTotalAmount(vo.getFee());
				bill.setPayStatus(PayStatus.INIT);
				bill.setPaytype(PayType.UNIONPAY);
				billService.saveOrUpdate(bill);
				responseVo.setIsPaid(true);
			}else{
				if(PayStatus.SUCCESSED.equals(bill.getPayStatus())){
					responseVo.setIsPaid(true);
				}else{
					responseVo.setIsPaid(false);
				}
			}
			responseVo.setCode(1);
			responseVo.setPayTime(bill.getCreateTime().getTime());
			responseVo.setOrderCreateTime(bill.getCreateTime().getTime());
			responseVo.setOrderId(bill.getId());
			responseVo.setPayAmount(bill.getTotalAmount());
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
