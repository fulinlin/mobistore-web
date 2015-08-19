package com.wolai.platform.controller.api.mobi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.constant.Constant.RespCode;
import com.wolai.platform.controller.api.BaseController;
import com.wolai.platform.entity.RewardPoints;
import com.wolai.platform.entity.SysUser;
import com.wolai.platform.service.RewardPointsService;
import com.wolai.platform.service.UserService;
import com.wolai.platform.util.BeanUtilEx;
import com.wolai.platform.vo.RewardPointsVo;

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
