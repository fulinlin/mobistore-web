package com.tinypace.mobistore.controller.api.mobi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.annotation.AuthPassport;
import com.tinypace.mobistore.bean.Page;
import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.ParkingLot;
import com.tinypace.mobistore.entity.Promotion;
import com.tinypace.mobistore.service.ParkingLotService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.ParkingLotVo;

@Controller
@RequestMapping(Constant.API_MOBI + "parkingLot/")
public class ParkingLotController extends BaseController {
	
	@Autowired
	ParkingLotService parkingLotService;

	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, @RequestBody Map<String, String> json){
		if (pagingParamError(json)) {
			return pagingParamError();
		}
		int startIndex = Integer.valueOf(json.get("startIndex"));
		int pageSize = Integer.valueOf(json.get("pageSize"));
		
		Map<String,Object> ret =new HashMap<String, Object>();
		
		String city = json.get("city");

		List<ParkingLotVo> vols = new ArrayList<ParkingLotVo>();
		Page page = parkingLotService.listByCity(city, startIndex, pageSize);
		for (Object obj : page.getItems()) {
			ParkingLot po = (ParkingLot) obj;
			ParkingLotVo vo = new ParkingLotVo();
			BeanUtilEx.copyProperties(vo, po);
			vols.add(vo);
		}
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vols);
		ret.put("totalPages", page.getTotalPages());
		return ret;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="detail")
	@ResponseBody
	public Map<String,Object> detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		Map<String,Object> ret =new HashMap<String, Object>(); 
		
		String id = json.get("id");
		if (StringUtils.isEmpty(id)) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "parameters error");
			return ret;
		}
		
		Object obj = parkingLotService.get(ParkingLot.class, id);
		if (obj == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "not found");
			return ret;
		}
		
		ParkingLot po = (ParkingLot) obj;

		ParkingLotVo vo = new ParkingLotVo();
		BeanUtilEx.copyProperties(vo, po);
		
		ret.put("code", RespCode.SUCCESS.Code());
		ret.put("data", vo);
		return ret;
	}
}
