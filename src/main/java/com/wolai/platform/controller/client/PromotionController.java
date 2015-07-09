package com.wolai.platform.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.service.ParkingLotService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.ParkingLotVo;

@RequestMapping(Constant.API_CLIENT + "parkingLot/")
public class PromotionController {
	
	@Autowired
	ParkingLotService parkingLotService;

	@AuthPassport(validate=true)
	@RequestMapping(value="listByCity")
	@ResponseBody
	public List<ParkingLotVo> list(HttpServletRequest request, @RequestBody Map<String, String> json){
		
		String city = json.get("city");

		List<ParkingLotVo> vols = new ArrayList<ParkingLotVo>();
		List<ParkingLot> ls = parkingLotService.listByCity(city);
		for (ParkingLot parkingLot : ls) {
			ParkingLotVo vo = new ParkingLotVo();
			BeanUtilEx.copyProperties(vo, parkingLot);
			vols.add(vo);
		}
		
		return vols;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="detail")
	@ResponseBody
	public ParkingLotVo detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		
		String id = json.get("id");
		ParkingLot po = (ParkingLot) parkingLotService.get(ParkingLot.class, id);

		ParkingLotVo vo = new ParkingLotVo();
		BeanUtilEx.copyProperties(vo, po);
		
		return vo;
	}
}
