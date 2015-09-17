package com.tinypace.mobistore.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.tinypace.mobistore.constant.Constant.RespCode;
import com.tinypace.mobistore.util.WebUtils;
import com.tinypace.mobistore.validator.BeanValidators;

public class BaseController {
	
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;
	
	public boolean pagingParamError(Map<String, String> json){
		if (json.get("startIndex") == null || json.get("pageSize") == null) {
			return true;
		}

		return false;
	}

    /**
     * 
     * 异常处理
     * @author xuxiang
     * @param request 请求
     * @param e 异常
     * @return 返回视图
     */
    @ExceptionHandler
    public void exception(HttpServletRequest request,HttpServletResponse response, Exception e) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("code", -200);
    	result.put("msg", e.getMessage());
    	e.printStackTrace();
    	WebUtils.renderJson(response, JSON.toJSONString(result));
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
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Map<String,Object> result, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(result, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 添加Model消息
	 * @param messages 消息
	 */
	protected void addMessage(Map<String,Object> result, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		result.put("msg", sb.toString());
	}
}
