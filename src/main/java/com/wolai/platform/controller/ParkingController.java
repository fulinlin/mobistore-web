package com.wolai.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.service.ParkingService;
import com.wolai.platform.service.UserService;

@Controller("webParkingController")
@RequestMapping(value = "${adminPath}/parking")
public class ParkingController extends BaseController{
	
	@Autowired
	UserService userService;
	
	@Autowired
	ParkingService parkingService;
	
    @RequestMapping(value = {"list", ""})
    public String list(ParkingRecord parkingRecord, @RequestParam (required = false) String mobile ,@RequestParam(required=false)Integer pageNo,@RequestParam(required=false)Integer pageSize, HttpServletRequest request, HttpServletResponse response, Model model) {
    	if(pageNo==null){
			pageNo=1;
		}
		
		if(pageSize==null){
			pageSize=limit;
		}
		
    	DetachedCriteria dc = DetachedCriteria.forClass(ParkingRecord.class);
        dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
        if(StringUtils.isNotBlank(parkingRecord.getCarNo())){
            dc.add(Restrictions.like("carNo", parkingRecord.getCarNo(),MatchMode.ANYWHERE).ignoreCase());
        }
        if(parkingRecord.getDriveInTime() != null){
            dc.add(Restrictions.gt("driveInTime", parkingRecord.getDriveInTime()));
        }
        if(parkingRecord.getDriveOutTime() != null){
            dc.add(Restrictions.lt("driveOutTime", parkingRecord.getDriveOutTime()));
        }
        if(StringUtils.isNotBlank(mobile)){
            dc.createAlias("user", "user_");
            dc.add(Restrictions.like("user_.mobile", mobile,MatchMode.ANYWHERE).ignoreCase());
            model.addAttribute("mobile", mobile);
        }
        page = parkingService.findPage(dc, (pageNo-1)*pageSize, pageSize);
        
        model.addAttribute("page", page);
        model.addAttribute("parkingRecord", parkingRecord);
        return "sys/parkingRecord/parkingRecordList";
    }
	
}
