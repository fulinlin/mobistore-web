package com.wolai.platform.util;

import java.io.IOException;


public class EncodeUtils {
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
	
    public static String Confuse(String str){
    	str = str.substring(10, str.length()) + str.substring(0,10);
    	return str;
    }
    public static String UnConfuse(String str){
    	str = str.substring(str.length()-10, str.length()) + str.substring(0, str.length() - 10);
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
    public static String getJsonString(String source){
    	String base64 = EncodeUtils.UnConfuse(source);
    	return new String(decodeBase64(base64));
    }
    
    public static void main(String args[]){
    	String params = "{\"id\":\"123456\"}";
    	System.out.println("原文:"+params);
    	String encodes = EncodeUtils.sign(params);
    	System.out.println("加密后:"+encodes);
    	
    	System.out.println("解密:"+EncodeUtils.getJsonString(encodes));
    }
}
