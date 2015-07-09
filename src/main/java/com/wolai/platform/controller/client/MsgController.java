package com.wolai.platform.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.annotation.AuthPassport;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.ParkingLot;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.Promotion;
import com.wolai.platform.entity.SysMessage;
import com.wolai.platform.entity.SysMessageSend;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.AssetService;
import com.wolai.platform.service.MsgService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.MessageVo;
import com.wolai.platform.vo.ParkingLotVo;
import com.wolai.platform.vo.PromotionVo;

@Controller
@RequestMapping(Constant.API_CLIENT + "msg/")
public class MsgController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MsgService msgService;

	@AuthPassport(validate=true)
	@RequestMapping(value="list")
	@ResponseBody
	public List<MessageVo> list(HttpServletRequest request, @RequestBody Map<String, String> json, @RequestParam String token){
		SysUser user = userService.getUserByToken(token);
		String userId = user.getId();
		List<SysMessageSend> ls = msgService.listByUser(userId);

		List<MessageVo> vols = new ArrayList<MessageVo>();
		for (SysMessageSend po : ls) {
			MessageVo vo = new MessageVo();
			BeanUtilEx.copyProperties(vo, po.getMessage());
			vo.setSendTime(po.getSendTime());
			vols.add(vo);
		}
		
		return vols;
	}
	
	@AuthPassport(validate=true)
	@RequestMapping(value="detail")
	@ResponseBody
	public MessageVo detail(HttpServletRequest request, @RequestBody Map<String, String> json){
		String id = json.get("id");
		SysMessage po = (SysMessage) msgService.get(SysMessage.class, id);

		MessageVo vo = new MessageVo();
		BeanUtilEx.copyProperties(vo, po);
		
		return vo;
	}
}
