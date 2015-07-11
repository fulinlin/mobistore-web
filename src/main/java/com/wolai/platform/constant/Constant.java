package com.wolai.platform.constant;


/**
 * 系统常量设置类
 * 
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */

public final class Constant {
	
	/**
	 * app访问接口前缀
	 */
	public static final String API_CLIENT = "api/mobi/";
	
	/**
	 * app响应控制器包名
	 */
	public static final String API_CLIENT_PACKAGE="com.wolai.platform.controller.client";
	
	/**
	 * 
	 */
	public static final String API_OUT_PACKAGE="com.wolai.platform.controller.api";
    /**
     * 管理员权限编码
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    
    /**
     * 登录跳转页
     */
    public static final String LOGIN_URL = "/index.jsp";
    
    /**
     * 前台首页
     */
    public static final String APP_FRONT_INDEX = "index";
    
    /**
     * 分页默认大小
     */
    public static final int PAGE_SIZE = 15;
    
    /**
     * 用户登录信息
     */
    public static final String SESSION_LOGINFO = "session_loginfo";
    
    /**
     * app端用户获取key
     */
    public static final String REQUEST_USER = "request_user";
    
    /**
     * 排序方向-升序
     */
    public static final String ORDER_BY_ASC = "asc"; 
    
    /**
     * 排序方向-降序
     */
    public static final String ORDER_BY_DESC = "desc";

    public static final Long LONG_ZERO=0L;
    
    public static final String STRING_TRUE="true";
    
    public static final String STRING_FALSE="false";
    
    
  	public static enum RespCode{
  		SUCCESS(1), BIZ_FAIL(-1), INTERFACE_FAIL(-10), NOT_LOGIN(-100);
  		
  		private RespCode(int code){
  			this.code = code;
  		}
  		private int code;
  		
  		public int Code(){
  			return code;
  		}
  	}
  
}