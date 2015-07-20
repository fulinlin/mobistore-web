package com.wolai.platform.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.SysRole;
import com.wolai.platform.exception.LoginAccountAuthException;
import com.wolai.platform.util.DateUtils;
import com.wolai.platform.util.WebUtils;
import com.wolai.platform.validator.BeanValidators;

/**
 * 
 * 
 * <简述>控制器基类
 * <详细描述>
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
public class BaseController {
	
    /**
     * 分页大小
     */
    protected Integer limit=Constant.PAGE_SIZE;
    
    /**
     * 分页开始索引
     */
    protected Integer start=0;
    
    /**
     * 分页page vo
     */
    @SuppressWarnings("rawtypes")
    protected Page page;
    
    /**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;
    
    /**
     * 
     *〈简述〉获取保存在Session中的用户对象  
     *〈详细描述〉
     * @author xuxiang
     * @param request 请求
     * @return 返回
     */
    protected LoginInfo getLoginInfoSession(HttpServletRequest request) {  
        return (LoginInfo) request.getSession(false).getAttribute(Constant.SESSION_LOGINFO);  
    }  
    
    /**
     * 
     *〈简述〉将用户对象保存到Session中  
     *〈详细描述〉
     * @author xuxiang
     * @param request 请求
     * @param loginInfo 用户登录信息
     */
    protected void setLoginInfoSession(HttpServletRequest request, LoginInfo loginInfo) {  
        request.getSession().setAttribute(Constant.SESSION_LOGINFO, loginInfo);  
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
    public String exception(HttpServletRequest request,HttpServletResponse response, Exception e) {
    	if(e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException && isAjaxRequest(request)){
    		WebUtils.renderJson(response, "{success:false,msg:'文件过大!'}");
    		return null;
		}else if(e instanceof LoginAccountAuthException){
			
		}else{
			//添加自己的异常处理逻辑，如日志记录
	    	request.setAttribute("error", e.getMessage());
		}
    	e.printStackTrace();
        return "error";
    }  
    
	  /**
     * 是否为ajax请求判断。
     * @param request
     * @return 是：true; 否：false.
     */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}
    
	
	/**
	 * 添加Model消息
	 * @param messages 消息
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
     * @param messages 消息
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	
	protected Boolean hasRole(HttpServletRequest request,String roleCode){
		LoginInfo  info= getLoginInfoSession(request);
		
		for(SysRole role:info.getRoles()){
			if(roleCode.equals(role.getCode())){
				return true;
			}
		}
		return false;
	}
	
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author xuxiang
     * @return 返回
     */
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }
    
    public void setStart(int start) {
        this.start = start;
    }
    
    @SuppressWarnings("rawtypes")
    public Page getPage() {
        return page;
    }
    
    @SuppressWarnings("rawtypes")
    public void setPage(Page page) {
        this.page = page;
    }
    
    public void setStart(Integer start) {
        this.start = start;
    }  
}
