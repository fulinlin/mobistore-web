package com.wolai.platform.util;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;


public class EncodeUtils {
	public static int OFFSET = 10;
	
	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		 sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
		try {
			return decoder.decodeBuffer(input);
		} catch (IOException e) {
			;
		}
		return null;
	}
	
	/**
	 * Base64加密.
	 */
	public static String encodeBase64(byte[] bstr) {  
		return new sun.misc.BASE64Encoder().encode(bstr);  
	} 
	
	// 前十位和后面所有位调换位置
    private static String Confuse(String str){
    	str = str.substring(OFFSET, str.length()) + str.substring(0,OFFSET);
    	return str;
    }
    private static String UnConfuse(String str){
    	str = str.substring(str.length()-OFFSET, str.length()) + str.substring(0, str.length() - OFFSET);
    	return str;
    }
    
    /**
     * 签名
     * @param source
     * @return
     */
    public static String sign(String source){
    	// 先做base64
    	String base64 = EncodeUtils.encodeBase64(source.getBytes());
    	// 然后移位
    	return EncodeUtils.Confuse(base64);
    }
    
    /**
     * 获取加密后字符串
     * @param source
     * @return
     */
    public static String unSign(String source){
    	String base64 = EncodeUtils.UnConfuse(source);
    	return new String(decodeBase64(base64));
    }
    
    public static void main(String args[]){
    	// 请将银联卡绑定的请求JSON对象参数设置为: {"sign": "I6ImFjYy1ubyIsICJjZXJ0aWZJZCI6ImNlcnRpZi1pZCIsICJjdm4iOiJjdm4iLCJl..."}
    	// 服务器端会取出sign键对应的值，进行解密
    	
    	String params = "{\"accNo\":\"acc-no\", \"certifId\":\"certif-id\", \"cvn\":\"cvn\",\"expired\":\"expired\"}";
    	System.out.println("原文:"+params);
    	String encodes = EncodeUtils.sign(params);
    	System.out.println("加密后:"+encodes);
    	
    	String str = EncodeUtils.unSign(encodes);
    	System.out.println("解密后:" + str);
    	
    	JSONObject jsonObj = JSONObject.parseObject(str);
    	System.out.println("转换后:" + jsonObj.getString("accNo"));
    	System.out.println("转换后:" + jsonObj.getString("certifId"));
    	System.out.println("转换后:" + jsonObj.getString("cvn"));
    	System.out.println("转换后:" + jsonObj.getString("expired"));
    }
}
