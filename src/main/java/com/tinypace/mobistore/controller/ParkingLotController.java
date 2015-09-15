package com.tinypace.mobistore.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tinypace.mobistore.config.SystemConfig;
import com.tinypace.mobistore.entity.ParkingLot;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.service.ParkingLotService;

@Controller("webparkingLotController")
@RequestMapping("${adminPath}/parkinglot")
public class ParkingLotController extends BaseController {

	@Autowired
	public ParkingLotService parkingLotService;
	
	@ModelAttribute
	public ParkingLot get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return (ParkingLot) parkingLotService.get(ParkingLot.class,id);
		}else{
			return new ParkingLot();
		}
	}
	
	@RequestMapping({"list", ""})
	public String list(HttpServletRequest request,ParkingLot parkingLot,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize,Model model){
		if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		page = parkingLotService.findAllByPage(parkingLot, (pageNo-1)*pageSize, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("parkingLot", parkingLot);
		return "sys/parkinglot/list";
	}
	
	@RequestMapping("edit")
	public String edit(ParkingLot parkingLot, Model model){
		if(parkingLot!=null && StringUtils.isNotEmpty(parkingLot.getId())){
			parkingLot = (ParkingLot) parkingLotService.get(SysUser.class, parkingLot.getId());
		}
		model.addAttribute("parkingLot", parkingLot);
		return "sys/parkinglot/form";
	}
	
	@RequestMapping("save")
	public String save(ParkingLot parkingLot, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		// 参数校验
		if (!beanValidator(model, parkingLot)) {
			return edit(parkingLot, model);
		}
		
		parkingLotService.saveOrUpdate(parkingLot);
		addMessage(redirectAttributes, "保存停车场'" + parkingLot.getName()+ "'成功");
		return "redirect:" + SystemConfig.getAdminPath() + "/parkinglot/?repage";
	}
	
	@RequestMapping("view")
	public String view(ParkingLot parkinglot, Model model) {
		if(parkinglot!=null && parkinglot.getId()!=null){
			parkinglot = (ParkingLot) parkingLotService.get(ParkingLot.class, parkinglot.getId());
			model.addAttribute("parkinglot", parkinglot);
			return "sys/parkinglot/view";
		}
		return "";
	}
}
