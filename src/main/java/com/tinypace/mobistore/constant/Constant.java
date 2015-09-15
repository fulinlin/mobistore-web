package com.tinypace.mobistore.constant;



/**
 * 系统常量设置类
 * 
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */

public final class Constant {
	public static String WEB_ROOT = "__UNKNOW__";
	public static String WEB_PATH = "__UNKNOW__";
	public static String CLIENT_ROOT = "__UNKNOW__"; // 促销WebApp地址
	
	public static String THIRD_PART_SERVER = "__UNKNOW__";
	
	/**
	 * appClient访问接口前缀
	 */
	public static final String API_MOBI = "api/mobi/";
	
	/**
	 * webClint访问接口前缀
	 */
	public static final String API_WEB = "api/web/";
	
	/**
	 * 外部接口访问接口前缀
	 */
	public static final String API_EX= "api/ex/";
	
	/**
	 * 
	 */
	public static final String API_OUT_PACKAGE_CLIENT="com.tinypace.mobistore.controller.api";
	
	public static final  String payment_productionName = "停车费";
	public static final  String payment_productionDesrcription = "喔来停车费";
	
	public static final  String payment_paySuccess_title = "[喔来智能停车]支付成功";
	public static final  String payment_paySuccess_msg_short = "您已成功支付%AMOUNT%元停车费，车牌号%LINCENST%。";
	public static final  String payment_payFail_title = "[喔来智能停车]支付失败";
	public static final  String payment_payFail_msg_short = "%AMOUNT%元停车费支付失败，车牌号%LINCENST%。";
	
	public static final  String payment_paySuccess_full = "[喔来智能停车]" + Constant.payment_paySuccess_msg_short;
	public static final  String payment_payFail_full = "[喔来智能停车]" + Constant.payment_payFail_msg_short;
	
	public static final  String alipay_partnerId = "2088021183682236";
	public static final  String alipay_partnerPrivKey = "MIICWwIBAAKBgQC2QclvVe6hggoS/O0zkExy8VWqc+xCKl89OhU68Lf12ZJErxZXAX98G/vkmXQi7tgD7F77eXxQNRqkzcMIxdPCoZFoF77idC2l4L0jo6nX7LiibaDkEOrn41zH1hO+PuoTOYGs4cie6mdkVivakn1FLQU1BspYq0l6qZjIZ3PRHQIDAQABAoGAbtEBUZjGR51x0qnG8BtE4H6sv79MX86zqd6vGe9Ta0CnFuv+gVh/006QkiZb3wGqhngqKG1tkLc5RAt4EtV2M1Jn8qdUmCaFvKtjUKKDaDgUzLZtyMx0LOsAQQ3h8W2Ov6XPnT7u2GistXaTeGNEt8QK9zAbK1Uv84UHUbf7+mECQQDiA6wBwGVazrmXqSQlH3W+KByRV8P99lC6UKWqfv66LoFlQQVHh0wCRhsRfZGa6WGzTpa57v/K+xhKyGBnbip3AkEAzm/xv0uIl3UAoXpnLC3n49rccNp55g4tMELKwkC4on1L5vcWoDDUh18GH/qCy3/GBWNje/ie0qpTbwlpZGlyCwJAHlS10apbL7uMZpD5W84EBBfn9Y1crQlQPiCyitXuI1g4XzlBlx7OEYQM/+TlFG/7f4+tZJhxVXErCmYnodnD8wJALsoEmvz9uoZUXEUn+UV2V5iVeiaqllAEQfoBhsGwl3OaroC2ahxnLz+EXd8joggV08bRF5okNU9RlcsmThHwPQJANDEP8MbzBT6u5+lFpBaaiye7BtJEcsmQRNgJmtPyvGAJlRFk3ghsW1U/Tgz/5YevkZujao0yHYCwPUgfaaV+yQ==";	
	public static final  String alipay_partnerPrivKey_pkcs8 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALZByW9V7qGCChL87TOQTHLxVapz7EIqXz06FTrwt/XZkkSvFlcBf3wb++SZdCLu2APsXvt5fFA1GqTNwwjF08KhkWgXvuJ0LaXgvSOjqdfsuKJtoOQQ6ufjXMfWE74+6hM5gazhyJ7qZ2RWK9qSfUUtBTUGylirSXqpmMhnc9EdAgMBAAECgYBu0QFRmMZHnXHSqcbwG0Tgfqy/v0xfzrOp3q8Z71NrQKcW6/6BWH/TTpCSJlvfAaqGeCoobW2QtzlEC3gS1XYzUmfyp1SYJoW8q2NQooNoOBTMtm3IzHQs6wBBDeHxbY6/pc+dPu7YaKy1dpN4Y0S3xAr3MBsrVS/zhQdRt/v6YQJBAOIDrAHAZVrOuZepJCUfdb4oHJFXw/32ULpQpap+/rougWVBBUeHTAJGGxF9kZrpYbNOlrnu/8r7GErIYGduKncCQQDOb/G/S4iXdQChemcsLefj2txw2nnmDi0wQsrCQLiifUvm9xagMNSHXwYf+oLLf8YFY2N7+J7SqlNvCWlkaXILAkAeVLXRqlsvu4xmkPlbzgQEF+f1jVytCVA+ILKK1e4jWDhfOUGXHs4RhAz/5OUUb/t/j61kmHFVcSsKZieh2cPzAkAuygSa/P26hlRcRSf5RXZXmJV6JqqWUARB+gGGwbCXc5qugLZqHGcvP4Rd3yOiCBXTxtEXmiQ1T1GVyyZOEfA9AkA0MQ/wxvMFPq7n6UWkFpqLJ7sG0kRyyZBE2Ama0/K8YAmVEWTeCGxbVT9ODP/lh6+Rm6NqjTIdgLA9SB9ppX7J";
	public static final  String alipay_sellerAccount = "wolai@wolaitech.com";
	public static final  String alipay_notify_uri = "api/mobi/payment/alipay/callback";

	public static final  String unionpay_mchId = "898320148160286";
	public static final  String unionpay_request_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final  String unionpay_notify_uri_consume = "api/mobi/payment/unionpay/consume/callback";
	public static final  String unionpay_notify_uri_delegate = "api/mobi/payment/unionpay/delegate/callback";
	
	public static final  String wechat_pay_notify_uri = "api/mobi/payment/wechat/callback";
	
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
    
    public static final String REQUEST_PARINGLOTID = "parkingLotId";
    
    /**
     * 设备类型
     */
    public static final String DEVICE_TYPE_IOS="ios";
    public static final String DEVICE_TYPE_ANDROID="android";
    
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
  		SUCCESS(1), BIZ_FAIL(-1), BIZ_FAIL_2(-2), INTERFACE_FAIL(-10), NOT_LOGIN(-100);
  		
  		private RespCode(int code){
  			this.code = code;
  		}
  		private int code;
  		
  		public int Code(){
  			return code;
  		}
  	}
  
}