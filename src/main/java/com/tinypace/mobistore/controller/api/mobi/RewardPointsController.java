package com.tinypace.mobistore.controller.api.mobi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.controller.api.BaseController;
import com.tinypace.mobistore.entity.RewardPoints;
import com.tinypace.mobistore.entity.SysUser;
import com.tinypace.mobistore.service.RewardPointsService;
import com.tinypace.mobistore.service.UserService;
import com.tinypace.mobistore.util.BeanUtilEx;
import com.tinypace.mobistore.vo.RewardPointsVo;

@Controller
@RequestMapping(Constant.API_MOBI + "rewardPoints/")
public class RewardPointsController extends BaseController {
	@Autowired
	UserService userService;
	
	@Autowired
	RewardPointsService rewardPointsService;
	
	@RequestMapping(value="view")
	@ResponseBody
	public Object view(HttpServletRequest request, @RequestParam String token){
		Map<String, Object> ret = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getAttribute(Constant.REQUEST_USER);
		String userId = user.getId();

		RewardPoints rewardPoints = rewardPointsService.getByUserPers(userId);
		
		if (rewardPoints == null) {
			ret.put("code", RespCode.INTERFACE_FAIL.Code());
			ret.put("msg", "record not found");
			return ret;
		}
		RewardPointsVo vo = new RewardPointsVo();
		BeanUtilEx.copyProperties(vo, rewardPoints);
		return vo;
	}
	
}
