package com.wolai.platform.controller.api.mobi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.vo.ParkingLotVo;

@Controller
@RequestMapping(Constant.API_MOBI + "payment/")
public class PaymentController extends BaseController {
	
	@Autowired
	AssetService assetService;

	@RequestMapping(value="pay")
	@ResponseBody
	public List<ParkingLotVo> pay(HttpServletRequest request, @RequestBody Map<String, String> json){
		
		//TODO:
		return null;
	}
}
