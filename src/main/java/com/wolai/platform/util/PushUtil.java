package com.wolai.platform.util;

import push.android.AndroidBroadcast;
import push.android.AndroidUnicast;
import push.ios.IOSBroadcast;
import push.ios.IOSUnicast;

public class PushUtil {
	public static String APP_KEY_ANDROID = "55aceaf3e0f55abb11003b4f";
	public static String APP_SECRET_ANDROID = "8ky9r6gf8rwa3vvys6wofzitsmputjiy";

	public static String APP_KEY_IOS = "55aceb2667e58e3b46000e08";
	public static String APP_SECRET_IOS = "jjkcc9trpjhkxjkhhuqbdkea5ottkx58";

	private String appkey = null;
	private String appMasterSecret = null;
	private String timestamp = null;

	public PushUtil(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
			timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
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
	public void sendAndroidBroadcast(String displaytype, String ticker, String title, String text,
			String afterOpen, String url, String activity) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast();
		broadcast.setAppMasterSecret(appMasterSecret);
		broadcast.setPredefinedKeyValue("appkey", this.appkey);
		broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
		
		broadcast.setPredefinedKeyValue("display_type", displaytype);
		broadcast.setPredefinedKeyValue("ticker", ticker);
		broadcast.setPredefinedKeyValue("title", title);
		broadcast.setPredefinedKeyValue("text", text);
		
		broadcast.setPredefinedKeyValue("after_open", afterOpen);
		if ("go_url".equals(afterOpen)) {
			broadcast.setPredefinedKeyValue("url", url);
		}
		if ("go_activity".equals(afterOpen)) {
			broadcast.setPredefinedKeyValue("activity", activity);
		}
		
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		broadcast.setPredefinedKeyValue("production_mode", "false");
		
// 		Set customized fields
//		broadcast.setExtraField("param1", "test");
		
		broadcast.send();
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
	public void sendAndroidUnicast(String deviceTokens, String displaytype, String ticker, String title, String text, 
			String afterOpen, String url, String activity) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast();
		unicast.setAppMasterSecret(appMasterSecret);
		unicast.setPredefinedKeyValue("appkey", this.appkey);
		unicast.setPredefinedKeyValue("timestamp", this.timestamp);

		unicast.setPredefinedKeyValue("device_tokens", deviceTokens);
		unicast.setPredefinedKeyValue("display_type", displaytype);
		unicast.setPredefinedKeyValue("ticker", ticker);
		unicast.setPredefinedKeyValue("title", title);
		unicast.setPredefinedKeyValue("text", text);
		unicast.setPredefinedKeyValue("after_open", afterOpen);
		if ("go_url".equals(afterOpen)) {
			unicast.setPredefinedKeyValue("url", url);
		}
		if ("go_activity".equals(afterOpen)) {
			unicast.setPredefinedKeyValue("activity", activity);
		}
		
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		unicast.setPredefinedKeyValue("production_mode", "false");
		
// 		Set customized fields
//		broadcast.setExtraField("param1", "test");
		
		unicast.send();
	}

	/**
	 * 发送iOS广播消息
	 * 
	 * @param deviceTokens	必填 友盟设备编号
	 * @param alert 提示消息
	 * @throws Exception
	 */
	public void sendIOSBroadcast(String alert) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast();
		broadcast.setAppMasterSecret(appMasterSecret);
		broadcast.setPredefinedKeyValue("appkey", this.appkey);
		broadcast.setPredefinedKeyValue("timestamp", this.timestamp);

		broadcast.setPredefinedKeyValue("alert", alert);
		broadcast.setPredefinedKeyValue("badge", 1);
		broadcast.setPredefinedKeyValue("sound", "default");
		
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		broadcast.setPredefinedKeyValue("production_mode", "false");
		
		// Set customized fields
// 		Set customized fields
//		broadcast.setExtraField("param1", "test");
		
		broadcast.send();
	}

	/**
	 * 发送iOS单播消息
	 * 
	 * @param deviceTokens	必填 友盟设备编号
	 * @param alert 提示消息
	 * @throws Exception
	 */
	public void sendIOSUnicast(String deviceTokens, String alert) throws Exception {
		IOSUnicast unicast = new IOSUnicast();
		unicast.setAppMasterSecret(appMasterSecret);
		unicast.setPredefinedKeyValue("appkey", this.appkey);
		unicast.setPredefinedKeyValue("timestamp", this.timestamp);
		
		// TODO Set your device token
		unicast.setPredefinedKeyValue("device_tokens", deviceTokens);
		unicast.setPredefinedKeyValue("alert", alert);
		unicast.setPredefinedKeyValue("badge", 1);
		unicast.setPredefinedKeyValue("sound", "default");

		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setPredefinedKeyValue("production_mode", "false");
		
		// Set customized fields
// 		Set customized fields
//		broadcast.setExtraField("param1", "test");
		
		unicast.send();
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
			demo.sendAndroidBroadcast("notification", "通知栏提示", "通知标题", "通知内容",
					"go_url", "http://www.baidu.com", null);
			demo.sendAndroidUnicast("XXX", "notification", "通知栏提示", "通知标题", "通知内容",
					"go_url", "http://www.baidu.com", null);

			demo.sendIOSBroadcast("通知栏提示");
			demo.sendIOSUnicast("XXX", "通知栏提示");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
