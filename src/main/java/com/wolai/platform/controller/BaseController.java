package com.wolai.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wolai.platform.bean.LoginInfo;
import com.wolai.platform.bean.Page;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.exception.LoginAccountAuthException;
import com.wolai.platform.util.WebUtils;

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
    protected Integer start;
    
    /**
     * 分页page vo
     */
    @SuppressWarnings("rawtypes")
    protected Page page;
    
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
			
		}
    	//添加自己的异常处理逻辑，如日志记录
    	request.setAttribute("error", "文件过大！");
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
