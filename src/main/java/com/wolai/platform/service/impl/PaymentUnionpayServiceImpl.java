package com.wolai.platform.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;
import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.UnionpayCardBound;
import com.wolai.platform.service.PaymentUnionpayService;
import com.wolai.platform.util.TimeUtils;

@Service
public class PaymentUnionpayServiceImpl extends CommonServiceImpl implements PaymentUnionpayService {
	public static String encoding = "UTF-8";
	public static String version = "5.0.0";
	
	@Override
	public UnionpayCardBound boundQueryByCard(String accNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(UnionpayCardBound.class);
		dc.add(Restrictions.eq("accNo", accNo));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));

		List ls = findAllByCriteria(dc);
		if (ls.size() > 0) {
			return (UnionpayCardBound)ls.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public UnionpayCardBound boundQueryByUser(String userId) {
		DetachedCriteria dc = DetachedCriteria.forClass(UnionpayCardBound.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.eq("isDisable", false));

		List ls = findAllByCriteria(dc);
		if (ls.size() > 0) {
			return (UnionpayCardBound)ls.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public UnionpayCardBound createBoundRecordPers(String userId, String accNo, String wolaiTradeNo) {
		
		UnionpayCardBound bound = new UnionpayCardBound();
		bound.setUserId(userId);
		bound.setAccNo(accNo);
		bound.setWolaiTradeNo(wolaiTradeNo);
		saveOrUpdate(bound);
		return bound;
	}
	
	@Override
	public Map<String, String> prepareTrans(String wolaiTradeNo, int amount) {
		/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
//		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
//		data.put("frontUrl", "http://localhost:8080/ACPTest/acp_front_url.do");
		// 后台通知地址
		data.put("backUrl", Constant.unionpay_notify_url_consume);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", Constant.unionpay_mchId);
		// 商户订单号，8-40位数字字母
		data.put("orderId", wolaiTradeNo);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", String.valueOf(amount));
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");

		data = signData(data);

		// 交易请求url 从配置文件读取
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

		Map<String, String> resmap = submitUrl(data, requestAppUrl);

		System.out.println("请求报文=["+data.toString()+"]");
		System.out.println("应答报文=["+resmap.toString()+"]");
		return resmap;
	}
	
	@Override
	public Map<String, String> boundPers(String userId, String orderId, String accNo, String certifId, String cvn, String expired) {
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); //"201508145625";// --订单发送时间
		/**
		 * 初始化证书
		 */
	/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		/**
		 * 交易请求url 从配置文件读取
		 */
		 String requestBackUrl = SDKConfig.getConfig()
				.getBackRequestUrl();
		/**
		 * 组装请求报文
		 */
		Map<String, Object> contentData = new HashMap<String, Object>();
		// 固定填写
		contentData.put("version", version);// M

		// 默认取值：UTF-8
		contentData.put("encoding", encoding);// M
		//
		// //通过MPI插件获取
		// contentData.put("certId", certId);//M
		//
		// //填写对报文摘要的签名
		// contentData.put("signature", signature);//M

		// 01：RSA02： MD5 (暂不支持)
		contentData.put("signMethod", "01");// M

		// 取值:72
		contentData.put("txnType", "72");// M

		// 02：免验建立绑定关系
		contentData.put("txnSubType", "02");// M

		contentData.put("bizType", "000501");// M

		contentData.put("channelType", "07");// M


		// 后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
//		contentData.put("backUrl", backUrl);// C

		// 0：普通商户直连接入2：平台类商户接入
		contentData.put("accessType", "0");// M

		// 　
		contentData.put("merId", Constant.unionpay_mchId);// M

		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerId", subMerId);//C
		//
		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerName", subMerName);//C
		//
		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerAbbr", subMerAbbr);//C

		// 　
		contentData.put("orderId", orderId);// M

		// 　
		contentData.put("txnTime", txnTime);// M

		// //　
		// contentData.put("accType", accType);//O
		//
		// //对于前台类交易，返回卡号后4位，后台类交易，原样返回
		contentData.put("accNo", accNo);//O
		//
		// //　
		 contentData.put("customerInfo", getCustomer(accNo, certifId, cvn, expired));//O
		//
		// //商户自定义保留域，交易应答时会原样返回
		// contentData.put("reqReserved", reqReserved);//O
		//
		// //格式如下：{子域名1=值&子域名2=值&子域名3=值} 移动支付参考消费绑定关系信息 {bindInfo=XXXXX} 特殊商户上送
		// contentData.put("reserved", reserved);//O
		//
		// //格式如下：{子域名1=值&子域名2=值&子域名3=值}有风险级别要求的商户必填 风险级别 {riskLevel=XX}
		// contentData.put("riskRateInfo", riskRateInfo);//O
		//
		// //当使用银联公钥加密密码等信息时，需上送加密证书的CertID
		// contentData.put("encryptCertId", encryptCertId);//C
		//
		// //移动支付业务需要上送
		// contentData.put("userMac", userMac);//O
		//
		// //需做建立绑定关系交易时填写
		 contentData.put("bindId", orderId);//C
		//
		// //用于填写关联业务类型01：消费02：代收
		// contentData.put("relTxnType", relTxnType);//C
		//
		// //　
		// contentData.put("payCardType", payCardType);//O
		//
		// //　
		// contentData.put("issInsCode", issInsCode);//O
		//
		// //渠道类型为语音支付时使用用法见VPC交易信息组合域子域用法
		// contentData.put("vpcTransData", vpcTransData);//C

		Map<String, String> submitFromData = signData(contentData);
//		String html = createHtml(requestBackUrl, submitFromData);

		Map<String, String> resMap = submitUrl(submitFromData,requestBackUrl);
		
		if ("00".equals(resMap.get("respCode"))) {
			// 移除老的绑定记录
			UnionpayCardBound po = boundQueryByUser(userId);
			if (po != null) {
				po.setIsDelete(true);
				saveOrUpdate(po);
			}
			
			// 创建新的记录
			createBoundRecordPers(userId, accNo, orderId);
		}

//		System.out.println(html);

		System.out.println(resMap.toString());
		return resMap;
	}
	
	@Override
	public Map<String, String> unboundPers(String userId, String orderId) {
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// --订单发送时间
		/**
		 * 初始化证书
		 */
	/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		/**
		 * 组装请求报文
		 */
		Map<String, Object> contentData = new HashMap<String, Object>();
		// 固定填写
		contentData.put("version", version);// M

		// 默认取值：UTF-8
		contentData.put("encoding", encoding);// M

		// //通过MPI插件获取
		// contentData.put("certId", certId);//M
		//
		// //填写对报文摘要的签名
		// contentData.put("signature", signature);//M

		// 01RSA02 MD5 (暂不支持)
		contentData.put("signMethod", "01");// M

		// 取值:74
		contentData.put("txnType", "74");// M

		// 00：默认
		contentData.put("txnSubType", "00");// M

		// 默认:000000
		contentData.put("bizType", "000000");// M

		contentData.put("channelType", "07");// M

		// 0：普通商户直连接入2：平台类商户接入
		contentData.put("accessType", "0");// M

		// 　
		contentData.put("merId", Constant.unionpay_mchId);// M

		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerId", subMerId);//C
		//
		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerName", subMerName);//C
		//
		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerAbbr", subMerAbbr);//C

		// 　
		contentData.put("orderId", orderId);// M

		// 　
		contentData.put("txnTime", txnTime);// M

		// 　用于唯一标识绑定关系 根据实际情况填写
		contentData.put("bindId", orderId);// M

		// //上送卡号后4位或完整卡号
		// contentData.put("accNo", accNo);//O
		//
		// //商户自定义保留域，交易应答时会原样返回
		// contentData.put("reqReserved", reqReserved);//O
		//
		// //格式如下：{子域名1=值&子域名2=值&子域名3=值} 移动支付参考消费
		// contentData.put("reserved", reserved);//O
		//
		// //格式如下：{子域名1=值&子域名2=值&子域名3=值}有风险级别要求的商户必填 风险级别 {riskLevel=XX}
		// contentData.put("riskRateInfo", riskRateInfo);//O

		
		/**
		 * 交易请求url 从配置文件读取
		 */
		String requestBackUrl = SDKConfig.getConfig()
				.getBackRequestUrl();
		Map<String, String> submitFromData = signData(contentData);

		Map<String, String> resMap = submitUrl(submitFromData,requestBackUrl);

		
		if ("00".equals(resMap.get("respCode"))) {
			// 移除绑定记录
			UnionpayCardBound po = boundQueryByUser(userId);
			if (po != null) {
				po.setIsDelete(true);
				saveOrUpdate(po);
			}
		}
		
		System.out.println(resMap.toString());
		
		return resMap;
	}
	
	@Override
	public Map<String, String> postPayConsume(String wolaiTradeNo, String accNo, BigDecimal amount) {
		String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // --订单发送时间
		/**
		 * 初始化证书
		 */
	/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		/**
		 * 交易请求url 从配置文件读取
		 */
		 String requestBackUrl = SDKConfig.getConfig()
				.getBackRequestUrl();
		/**
		 * 组装请求报文
		 */
		Map<String, Object> contentData = new HashMap<String, Object>();
		// 固定填写
		contentData.put("version", version);// M

		// 默认取值：UTF-8
		contentData.put("encoding", encoding);// M

		// //通过MPI插件获取
		// contentData.put("certId", certId);//M
		//
		// //填写对报文摘要的签名
		// contentData.put("signature", signature);//M

		// 01RSA02 MD5 (暂不支持)
		contentData.put("signMethod", "01");// M

		// 取值：11
		contentData.put("txnType", "11");// M

		// 默认：00
		contentData.put("txnSubType", "00");// M

		contentData.put("bizType", "000401");// M//000401 代付

		contentData.put("channelType", "07");// M


		// 交易后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
		contentData.put("backUrl", Constant.unionpay_notify_url_delegate);// M

		// 0：普通商户直连接入2：平台类商户接入
		contentData.put("accessType", "0");// M

		// 交易
		contentData.put("merId", Constant.unionpay_mchId);// M
		//
		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerId", subMerId);//C
		//
		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerName", subMerName);//C
		//
		// //商户类型为平台类商户接入时必须上送
		// contentData.put("subMerAbbr", subMerAbbr);//C

		// 交易
		contentData.put("orderId", wolaiTradeNo);// M

		// 交易
		contentData.put("txnTime", txnTime);// M

		// //　
		// contentData.put("accType", "0");//O
		// // 0：商户直连接入
		// // 1：收单机构接入
		// // 2：平台商户接入

		// 非绑定类交易时需上送卡号
		contentData.put("accNo", accNo);// M

		// 　
		contentData.put("txnAmt", amount.multiply(new BigDecimal(100)).intValue());// M

		// 默认为156交易，填写参考公参
		contentData.put("currencyCode", "156");// M

		// //　
		// contentData.put("customerInfo", getCustomer(encoding));//O
		//
		// //用于唯一标识绑定关系
		// contentData.put("bindId", bindId);//O
		//
		// //参看数据元说明
		// contentData.put("billType", billType);//O
		//
		// //账单查询/支付类交易中填写具体账单号码用法一：账单查询/支付类交易中网上缴税用法，填写纳税人编码用法二：账单查询/支付类交易中信用卡还款用法，填写信用卡卡号
		// contentData.put("billNo", billNo);//O
		//
		// //前台交易，有IP防钓鱼要求的商户上送
		// contentData.put("customerIp", customerIp);//C
		//
		// //格式为：yyyyMMdd-yyyyMMdd
		// contentData.put("billPeriod", billPeriod);//O
		//
		// //商户自定义保留域，交易应答时会原样返回
		// contentData.put("reqReserved", reqReserved);//O
		//
		// //格式如下：{子域名1=值&子域名2=值&子域名3=值} 移动支付参考消费
		// contentData.put("reserved", reserved);//O
		//
		// //格式如下：{子域名1=值&子域名2=值&子域名3=值}有风险级别要求的商户必填 风险级别 {riskLevel=XX}
		// contentData.put("riskRateInfo", riskRateInfo);//O
		//
		// //当使用银联公钥加密密码等信息时，需上送加密证书的CertID
		// contentData.put("encryptCertId", encryptCertId);//C
		//
		// //存折交易时必填
		// contentData.put("issInsCode", issInsCode);//C
		//
		// //　
		// contentData.put("termId", termId);//O
		//
		// //有卡交易必填
		// contentData.put("cardTransData", cardTransData);//C

		Map<String, String> submitFromData = signData(contentData);

		Map<String, String> resMap = submitUrl(submitFromData,requestBackUrl);
		System.out.println(resMap.toString());
		return resMap;

	}
	
	private Map<String, String> signData(Map<String, ?> contentData) {
		Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap<String, String>();
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Entry<String, String>) it.next();
			String value = obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
				System.out
						.println(obj.getKey() + "-->" + String.valueOf(value));
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.sign(submitFromData, encoding);

		return submitFromData;
	}
	
	private Map<String, String> submitUrl(
			Map<String, String> submitFromData,String requestUrl) {
		String resultString = "";
		System.out.println("requestUrl====" + requestUrl);
		System.out.println("submitFromData====" + submitFromData.toString());
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, encoding);
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
			// 打印返回报文
			System.out.println("打印返回报文：" + resultString);
		}
		return resData;
	}

	/**
	 * 
	 * @param accNo 卡号
	 * @param certifId 证件号码
	 * @param cvn	卡验证号
	 * @param expired 有效期
	 * @return
	 */
	private String getCustomer(String accNo, String certifId, String cvn, String expired) {
		StringBuffer sf = new StringBuffer("{");
		// 证件类型
		String certifTp = "01";
//		// 姓名
//		String customerNm = "互联网";
//		// 手机号
//		String phoneNo = "13552535506";
//		// 短信验证码
//		String smsCode = "123311";
//		// 持卡人密码
//		String pin = "123456";

		sf.append("certifTp=" + certifTp + SDKConstants.AMPERSAND);
		sf.append("certifId=" + certifId + SDKConstants.AMPERSAND);
//		sf.append("customerNm=" + customerNm + SDKConstants.AMPERSAND);
//		sf.append("phoneNo=" + phoneNo + SDKConstants.AMPERSAND);
//		sf.append("smsCode=" + smsCode + SDKConstants.AMPERSAND);
		// 密码加密
//		sf.append("pin=" + SDKUtil.encryptPin("622188123456789", pin, encoding)
//				+ SDKConstants.AMPERSAND);
		// 密码不加密
		// sf.append("pin="+pin + SDKConstants.AMPERSAND);
		// cvn2加密
		// sf.append(SDKUtil.encrptCvn2(cvn2, encoding) +
		// SDKConstants.AMPERSAND);
		// cvn2不加密
		sf.append("cvn2=" + cvn + SDKConstants.AMPERSAND);
		// 有效期加密
		// sf.append(SDKUtil.encrptAvailable(expired, encoding));
		// 有效期不加密
		sf.append("expired=" + expired);
		sf.append("}");
		String customerInfo = sf.toString();
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					encoding)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

}
