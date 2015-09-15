package com.tinypace.mobistore.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.service.impl.PaymentUnionpayServiceImpl;

import push.android.AndroidBroadcast;
import push.android.AndroidUnicast;
import push.ios.IOSBroadcast;
import push.ios.IOSUnicast;

public class PushUtil {
	private static Log log = LogFactory.getLog(PaymentUnionpayServiceImpl.class);
	
	public static String APP_KEY_ANDROID = "55aceaf3e0f55abb11003b4f";
	public static String APP_SECRET_ANDROID = "8ky9r6gf8rwa3vvys6wofzitsmputjiy";

	public static String APP_KEY_IOS = "55aceb2667e58e3b46000e08";
	public static String APP_SECRET_IOS = "jjkcc9trpjhkxjkhhuqbdkea5ottkx58";

	private String appkey = null;
	private String appMasterSecret = null;

	public PushUtil(String key, String secret) {
		appkey = key;
		appMasterSecret = secret;
	}
	
	/**
	 * 发送Android广播消息
	 * 
	 * @param displaytype	必填 消息类型，值可以为: notification-通知，message-消息
	 * @param ticker	必填 通知栏提示文字
	 * @param title	必填 通知标题
	 * @param text	必填 通知文字描述
	 * @param after_open	必填 值可以为: "go_app": 打开应用, "go_url": 跳转到URL, "go_activity": 打开特定的activity
	 * @param url	可选 当"after_open"为"go_url"时，必填。 通知栏点击后跳转的URL，要求以http或者https开头  
	 * @param activity	可选 当"after_open"为"go_activity"时，必填。 通知栏点击后打开的Activity
	 * @throws Exception
	 */
	public void sendAndroidBroadcastMsg(String title, String msg) {
		try {
			AndroidBroadcast broadcast = new AndroidBroadcast();
			broadcast.setAppMasterSecret(appMasterSecret);
			broadcast.setPredefinedKeyValue("appkey", this.appkey);
			broadcast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
			
			broadcast.setPredefinedKeyValue("display_type", "notification");
			broadcast.setPredefinedKeyValue("ticker", title);
			broadcast.setPredefinedKeyValue("title", title);
			broadcast.setPredefinedKeyValue("text", msg);
			
			broadcast.setPredefinedKeyValue("after_open", "go_app");
			broadcast.setPredefinedKeyValue("production_mode", "true");
//			broadcast.setExtraField("param1", "test");

			broadcast.send();
		} catch (Exception e) {
			log.error(Exceptions.getStackTraceAsString(e));
		}
	}

	/**
	 * 发送Android单播消息
	 * 
	 * @param deviceTokens	必填 友盟设备编号
	 * @param displaytype	必填 消息类型，值可以为: notification-通知，message-消息
	 * @param ticker	必填 通知栏提示文字
	 * @param title	必填 通知标题
	 * @param text	必填 通知文字描述
	 * @param after_open	必填 值可以为: "go_app": 打开应用, "go_url": 跳转到URL, "go_activity": 打开特定的activity
	 * @param url	可选 当"after_open"为"go_url"时，必填。 通知栏点击后跳转的URL，要求以http或者https开头  
	 * @param activity	可选 当"after_open"为"go_activity"时，必填。 通知栏点击后打开的Activity
	 * @throws Exception
	 */
	public void sendAndroidUnicastMsg(String deviceTokens, String title, String msg) {
		try {
			AndroidUnicast unicast = new AndroidUnicast();
			unicast.setAppMasterSecret(appMasterSecret);
			unicast.setPredefinedKeyValue("appkey", this.appkey);
			unicast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
	
			unicast.setPredefinedKeyValue("device_tokens", deviceTokens);
			unicast.setPredefinedKeyValue("display_type", "notification");
			unicast.setPredefinedKeyValue("ticker", title);
			unicast.setPredefinedKeyValue("title", title);
			unicast.setPredefinedKeyValue("text", msg);
			unicast.setPredefinedKeyValue("after_open", "go_app");
	
			unicast.setPredefinedKeyValue("production_mode", "true");
	//		broadcast.setExtraField("param1", "test");

			unicast.send();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendIosBroadcast(String msg) {
		IOSBroadcast broadcast = new IOSBroadcast();

		try {
			broadcast.setAppMasterSecret(appMasterSecret);
			broadcast.setPredefinedKeyValue("appkey", this.appkey);
			broadcast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
	
			broadcast.setPredefinedKeyValue("alert", msg);
			broadcast.setPredefinedKeyValue("badge", 0);
			broadcast.setPredefinedKeyValue("sound", "chime");
			broadcast.setPredefinedKeyValue("production_mode", "true");
			broadcast.setCustomizedField("test", "helloworld");
			broadcast.send();
		} catch (Exception e) {
			log.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	public void sendIosUnicast(String deviceTokens, String msg) {
		IOSUnicast unicast = new IOSUnicast();
		
		try {
			unicast.setAppMasterSecret(appMasterSecret);
			unicast.setPredefinedKeyValue("appkey", this.appkey);
			unicast.setPredefinedKeyValue("timestamp", Integer.toString((int) (System.currentTimeMillis() / 1000)));
			unicast.setPredefinedKeyValue("device_tokens", deviceTokens);
			unicast.setPredefinedKeyValue("alert", msg);
			unicast.setPredefinedKeyValue("badge", 0);
			unicast.setPredefinedKeyValue("sound", "chime");
			unicast.setPredefinedKeyValue("production_mode", "true");

			unicast.setCustomizedField("test", "helloworld");
			unicast.send();
		} catch (Exception e) {
			log.error(Exceptions.getStackTraceAsString(e));
		}
	}

	/**
	 * 
	 * @param deviceTokens	必填 友盟设备编号
	 * @param alert 提示消息
	 * @throws Exception
	 */
	public static void main(String[] args) {
		PushUtil android = new PushUtil(APP_KEY_ANDROID, APP_SECRET_ANDROID);
		PushUtil ios = new PushUtil(APP_KEY_IOS, APP_SECRET_IOS);
		try {
//			android.sendAndroidBroadcastMsg(Constant.payment_paySuccess_title, Constant.payment_paySuccess_msg_short);
			android.sendAndroidUnicastMsg("Ao4_4KaT40Rj_sfLHA8AIX0u8_Wr6CmcfoQi33M-qO8A", 
					Constant.payment_paySuccess_title, Constant.payment_paySuccess_msg_short);

//			ios.sendIosBroadcast(Constant.payment_paySuccess);
//			ios.sendIosUnicast("87589e545fba5eb2f42531536209dd3c6a99e02b63b0d4cd0f6d6f7962636b77", Constant.payment_payFail);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
