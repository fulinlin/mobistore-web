package com.wolai.platform.controller.api;


import java.util.HashMap;
import java.util.Map;

import com.wolai.platform.constant.Constant.RespCode;

public class BaseController {
	
	public boolean pagingParamError(Map<String, String> json){
		if (json.get("startIndex") == null || json.get("pageSize") == null) {
			return true;
		}

		return false;
	}

	public Map<String,Object> pagingParamError(){
		Map<String,Object> ret =new HashMap<String, Object>(); 

		ret.put("code", RespCode.INTERFACE_FAIL.Code());
		ret.put("msg", "paging parameters error, startIndex or pageSize missing?");
		return ret;
	}
	
	public Map<String,Object> parameterError(){
		Map<String,Object> ret =new HashMap<String, Object>(); 

		ret.put("code", RespCode.INTERFACE_FAIL.Code());
		ret.put("msg", "parameter error");
		return ret;
	}
	
	public Map<String,Object> notFoundError(){
		Map<String,Object> ret =new HashMap<String, Object>(); 

		ret.put("code", RespCode.INTERFACE_FAIL.Code());
		ret.put("msg", "not found");
		return ret;
	}
}
