package com.wolai.platform.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wolai.platform.service.impl.PaymentUnionpayServiceImpl;

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
	private String timestamp = null;

	public PushUtil(String key, String secret) {
		appkey = key;
		appMasterSecret = secret;
		timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
	}

	/**
	 * 发送Android广播消息
	 * 
	 * @param msg	消息内容
	 */
	public void sendAndroidBroadcastMsg(String msg) {
		try {
			AndroidBroadcast broadcast = new AndroidBroadcast();
			broadcast.setAppMasterSecret(appMasterSecret);
			broadcast.setPredefinedKeyValue("appkey", this.appkey);
			broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
			
			broadcast.setPredefinedKeyValue("display_type", "message");
			broadcast.setPredefinedKeyValue("custom", msg);

			broadcast.setPredefinedKeyValue("production_mode", "true");
			
			broadcast.send();
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
	}
	
	/**
	 * 发送Android单播消息
	 * @param deviceTokens 设备tokens
	 * @param msg	消息内容
	 */
	public void sendAndroidUnicastMsg(String msg, String deviceTokens) {
		AndroidUnicast unicast = new AndroidUnicast();
		
		try {
			unicast.setAppMasterSecret(appMasterSecret);
			unicast.setPredefinedKeyValue("appkey", this.appkey);
			unicast.setPredefinedKeyValue("timestamp", this.timestamp);
	
			unicast.setPredefinedKeyValue("device_tokens", deviceTokens);
			unicast.setPredefinedKeyValue("display_type", "message");
			
			unicast.setPredefinedKeyValue("production_mode", "true");

			unicast.send();
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
	}

	/**
	 * 发送iOS广播消息
	 * 
	 * @param deviceTokens	必填 友盟设备编号
	 * @param alert 提示消息
	 * @throws Exception
	 */
	public void sendIOSBroadcast(String alert) {
		IOSBroadcast broadcast = new IOSBroadcast();
		
		try {
			broadcast.setAppMasterSecret(appMasterSecret);
			broadcast.setPredefinedKeyValue("appkey", this.appkey);
			broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
	
			broadcast.setPredefinedKeyValue("alert", alert);
			broadcast.setPredefinedKeyValue("badge", 1);
			broadcast.setPredefinedKeyValue("sound", "default");
			
			broadcast.setPredefinedKeyValue("production_mode", "true");
			
			broadcast.send();
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
	}

	/**
	 * 发送iOS单播消息
	 * 
	 * @param deviceTokens	必填 友盟设备编号
	 * @param alert 提示消息
	 * @throws Exception
	 */
	public void sendIOSUnicast(String deviceTokens, String msg) {
		IOSUnicast unicast = new IOSUnicast();
		
		try {
			unicast.setAppMasterSecret(appMasterSecret);
			unicast.setPredefinedKeyValue("appkey", this.appkey);
			unicast.setPredefinedKeyValue("timestamp", this.timestamp);
			
			unicast.setPredefinedKeyValue("device_tokens", deviceTokens);
			unicast.setPredefinedKeyValue("alert", msg);
			unicast.setPredefinedKeyValue("badge", 1);
			unicast.setPredefinedKeyValue("sound", "default");
			unicast.setPredefinedKeyValue("production_mode", "true");

			unicast.send();
		} catch (Exception e) {
			log.error(e.getStackTrace());
		}
	}

	/**
	 * 
	 * @param deviceTokens	必填 友盟设备编号
	 * @param alert 提示消息
	 * @throws Exception
	 */
	public static void main(String[] args) {
		PushUtil demo = new PushUtil(APP_KEY_ANDROID, APP_SECRET_ANDROID);
		try {
			demo.sendAndroidBroadcastMsg("消息");
			demo.sendAndroidUnicastMsg("XXX", "消息");

			demo.sendIOSBroadcast("消息");
			demo.sendIOSUnicast("XXX", "消息");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
