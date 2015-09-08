package com.wolai.platform.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Bill;
import com.wolai.platform.entity.ParkingRecord;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.service.BillService;
import com.wolai.platform.service.MsgService;
import com.wolai.platform.service.PaymentWechatService;
import com.wolai.platform.util.DateUtils;
import com.wolai.platform.util.TimeUtils;
import com.wolai.platform.wechat.WechatConfigure;
import com.wolai.platform.wechat.OrderReqData;
import com.wolai.platform.wechat.OrderResData;
import com.wolai.platform.wechat.QueryReqData;
import com.wolai.platform.wechat.QueryResData;
import com.wolai.platform.wechat.Signature;
import com.wolai.platform.wechat.WechatUtil;
import com.wolai.platform.wechat.XMLParser;

@Service
public class PaymentWechatServiceImpl extends CommonServiceImpl implements PaymentWechatService {
	private static Log log = LogFactory.getLog(PaymentWechatServiceImpl.class);
	
	@Autowired
	BillService billService;
	
	@Autowired
	MsgService msgService;
	
	@Autowired
	PaymentWechatHttpsRequestImpl wechatHttpsRequest;

	/**
		* @param wolaiTradeNo  商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
		* @param totalFee 订单总金额，单位为“分”，只能整数
		* @param spBillCreateIP 订单生成的机器IP
		* @param timeStart 订单生成时间， 格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10 秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器
		* @param timeExpire 订单失效时间，格式同上
		 * @throws IOException 
		 * @throws NoSuchAlgorithmException 
		 * @throws KeyStoreException 
		 * @throws KeyManagementException 
		 * @throws UnrecoverableKeyException 
	*/
	@Override
	public Map<String, Object> preparePay(String wolaiTradeNo, int totalFee, String ip) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		
    	Date now = new Date();
    	String timeStart = DateUtils.formatDate(now, "yyyyMMddHHmmss");
    	String timeExpire = DateUtils.formatDate(TimeUtils.getDateAfter(now, 1) , "yyyyMMddHHmmss");
		
    	//--------------------------------------------------------------------
        //构造请求“被扫支付API”所需要提交的数据
        //--------------------------------------------------------------------
    	OrderReqData orderData = new OrderReqData(
			null,
			"喔来停车费",
			"喔来停车费",
			wolaiTradeNo,
			totalFee,
			null,
			ip,
			timeStart,
			timeExpire,
			"tag"
    	);
    	
        //接受API返回
        String payServiceResponseString;

        long costTimeStart = System.currentTimeMillis();


        log.info("支付API返回的数据如下：");
        payServiceResponseString = wechatHttpsRequest.sendPost(WechatConfigure.UNIFIED_ORDER_API, orderData);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.info("api请求总耗时：" + totalTimeCost + "ms");

        //打印回包数据
        log.info(payServiceResponseString);
        
        

        //将从API返回的XML数据映射到Java对象
        OrderResData orderResData = (OrderResData) WechatUtil.getObjectFromXML(payServiceResponseString, OrderResData.class);

        if (orderResData == null || orderResData.getReturn_code() == null) {
        	String msg = "【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问";
        	log.error(msg);
        	ret.put("success", false);
        	ret.put("msg", msg);
            return ret;
        }

        if (orderResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
        	String msg = "【支付失败】支付API系统返回失败，请检测Post给API的数据是否规范合法";
        	log.error(msg);
        	ret.put("success", false);
        	ret.put("msg", msg);
        } else {
            log.info("支付API系统成功返回数据");
            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------
            if (!Signature.checkIsSignValidFromResponseString(payServiceResponseString)) {
            	String msg = "【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了";
            	log.error(msg);
            	ret.put("msg", msg);
            	ret.put("success", false);
            }
            
            Map<String,Object> map = XMLParser.getMapFromXML(payServiceResponseString);

            //获取错误码
            String errorCode = orderResData.getErr_code();
            //获取错误描述
            String errorCodeDes = orderResData.getErr_code_des();

            if (orderResData.getResult_code().equals("SUCCESS")) {

                //--------------------------------------------------------------------
                //1)直接扣款成功
                //--------------------------------------------------------------------

            	String msg = "【预支付请求成功】";
            	log.info(msg);
            	ret.put("msg", msg);

            	String sign = Signature.getSignFromResponseString(payServiceResponseString);
        		String prepayId = map.get("prepay_id").toString();
            	ret.put("sign", sign);
            	ret.put("prepayId", prepayId);
            	
            	ret.put("success", true);
            }else{
                //出现业务错误
            	String msg = "业务返回失败：" + "err_code:" + errorCode + "，err_code_des:" + errorCodeDes;

                //业务错误时错误码有好几种，商户重点提示以下几种
                if (errorCode.equals("AUTHCODEEXPIRE") || errorCode.equals("AUTH_CODE_INVALID") || errorCode.equals("NOTENOUGH")) {
                    //--------------------------------------------------------------------
                    //2)扣款明确失败
                    //--------------------------------------------------------------------

                    //对于扣款明确失败的情况直接走撤销逻辑
//                    doReverseLoop(outTradeNo,resultListener);

                    //以下几种情况建议明确提示用户，指导接下来的工作
                    if (errorCode.equals("AUTHCODEEXPIRE")) {
                        //表示用户用来支付的二维码已经过期，提示收银员重新扫一下用户微信“刷卡”里面的二维码
                        msg += "，【支付扣款明确失败】原因是：" + errorCodeDes;
                    } else if (errorCode.equals("AUTH_CODE_INVALID")) {
                        //授权码无效，提示用户刷新一维码/二维码，之后重新扫码支付
                    	msg += "，【支付扣款明确失败】原因是：" + errorCodeDes;
                    } else if (errorCode.equals("NOTENOUGH")) {
                        //提示用户余额不足，换其他卡支付或是用现金支付
                    	msg += "，【支付扣款明确失败】原因是：" + errorCodeDes;
                    }
                }
               
            	log.error(msg);
            	ret.put("msg", msg);
                ret.put("success", false);
                ret.put("msg", msg);
            }
        }

        return ret;
	}

	@Override
	public Map<String, Object> query(String wolaiTradeNo) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		
        String payQueryServiceResponseString;

        QueryReqData queryReqData = new QueryReqData("", wolaiTradeNo);
        
        payQueryServiceResponseString = wechatHttpsRequest.sendPost(WechatConfigure.ORDER_QUERY_API, queryReqData);

        log.info("支付订单查询API返回的数据如下：");
        log.info(payQueryServiceResponseString);

        //将从API返回的XML数据映射到Java对象
        QueryResData queryResData = (QueryResData) WechatUtil.getObjectFromXML(payQueryServiceResponseString, QueryResData.class);
        if (queryResData == null || queryResData.getReturn_code() == null) {
        	String msg = "支付订单查询请求逻辑错误，请仔细检测传过去的每一个参数是否合法";
        	log.error(msg);
        	ret.put("success", false);
        	ret.put("msg", msg);
            return ret;
        }

        if (queryResData.getReturn_code().equals("FAIL")) {
            //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
        	String msg = "支付订单查询API系统返回失败，失败信息为：" + queryResData.getReturn_msg();
        	log.error(msg);

        	ret.put("success", false);
        	ret.put("msg", msg);
        } else {

            if (!Signature.checkIsSignValidFromResponseString(payQueryServiceResponseString)) {
            	String msg = "【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了";
            	log.error(msg);
            	ret.put("tradeState", queryResData.getTrade_state());
            	ret.put("success", false);
            	ret.put("msg", msg);
                return ret;
            }

            if (queryResData.getResult_code().equals("SUCCESS")) {//业务层成功
                
                if (queryResData.getTrade_state().equals("SUCCESS")) {
                    //表示查单结果为“支付成功”
                	String msg = "查询到订单支付成功";
                	log.info(msg);

                	ret.put("success", true);
                	ret.put("paySuccess", true);
                	ret.put("msg", msg);
                } else {
                    //支付不成功
                	String msg = ("查询到订单支付不成功");
                	log.error(msg);
                	
                	ret.put("success", true);
                	ret.put("paySuccess", false);
                	ret.put("msg", msg);
                }
            } else {
            	String msg = "查询出错，错误码：" + queryResData.getErr_code() + "     错误信息：" + queryResData.getErr_code_des();
            	log.error(msg);

            	ret.put("success", false);
            	ret.put("msg", msg);
            }
        }
        ret.put("return_code", queryResData.getReturn_code());
        ret.put("return_msg", queryResData.getReturn_msg());
        ret.put("result_code", queryResData.getResult_code());
        ret.put("trade_state", queryResData.getTrade_state());
        ret.put("total_fee", queryResData.getTotal_fee());
        
        return ret;
	}

	@Override
	public void callbackPers(Bill bill, String returnCode, String returnMsg, String tradeStatus, String totalFee, String wolaiTradeNo) {
		bill.setPaytype(Bill.PayType.WEIXIN);
		bill.setTradeStatus(tradeStatus);
		bill.setTradeAmount(new BigDecimal(totalFee));
		
		String title = "";
		String msgShort = "";
		String msgFull = "";
		if ("SUCCESS".equals(tradeStatus)) {
			bill.setPayStatus(PayStatus.SUCCESSED);
			
			title = Constant.payment_paySuccess_title;
			msgShort = Constant.payment_paySuccess_msg_short.replaceAll("%AMOUNT%", totalFee)
					.replace("%LINCENST%", bill.getCarNo());
			msgFull = Constant.payment_paySuccess_full.replaceAll("%AMOUNT%", totalFee)
					.replace("%LINCENST%", bill.getCarNo());
		} else {
			bill.setPayStatus(PayStatus.FEATURE);
			
			title = Constant.payment_payFail_title;
			msgShort = Constant.payment_payFail_msg_short.replaceAll("%AMOUNT%", String.valueOf(bill.getPayAmount().intValue()))
					.replace("%LINCENST%", bill.getCarNo());
			msgFull = Constant.payment_payFail_full.replaceAll("%AMOUNT%", String.valueOf(bill.getPayAmount().intValue()))
					.replace("%LINCENST%", bill.getCarNo());
		}
		msgService.sendAppMsg(bill.getParkingRecord().getUser(), title, msgShort, msgFull);
		
		bill.setTradeResponseTime(new Date());
		
		saveOrUpdate(bill);
		
		ParkingRecord park = (ParkingRecord) billService.get(ParkingRecord.class, bill.getParkingRecordId());
		park.setIsPaid(true);
		saveOrUpdate(park);
	}
}
