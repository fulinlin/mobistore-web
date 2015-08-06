package com.wolai.platform.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.License;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.service.LicenseService;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.vo.EntranceNoticeVo;

@Controller
@RequestMapping(Constant.API_EX)
public class ApiController extends BaseController{

	@Autowired
	private ParkingService parkingService;
	
	@Autowired
	private LicenseService licenseService;
	
	/**
	 * 进场信息通知接口
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("entranceNotice")
	public Model entranceNotice(@RequestBody EntranceNoticeVo vo,HttpServletRequest request,Model model){
		String parkingLotId = getParkingLotId(request);
		vo=(EntranceNoticeVo) request.getAttribute("vo");
		if(beanValidator(model, vo)){
			License license = licenseService.getLincense(vo.getCarNO().trim());
			if(license!=null){
				ParkingRecord record = parkingService.getParkingRecordbyExNo(vo.getExNo());
				if(record==null){
					record = new ParkingRecord();
				}
				
			}else{
				
			}
		}else{
			model.addAttribute("code", -200);
		}
		
		return model;
	}
	
	
	   private  String getParkingLotId(HttpServletRequest request) {
			return request.getAttribute(Constant.REQUEST_PARINGLOTID).toString();
		}
}
