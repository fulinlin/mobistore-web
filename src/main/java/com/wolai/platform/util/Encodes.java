package com.wolai.platform.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wolai.platform.vo.EntranceNoticeVo;

/**
 * 封装各种格式的编码解码工具类.
 * 1.Commons-Codec的 hex/base64 编码
 * 2.自制的base62 编码
 * 3.Commons-Lang的xml/html escape
 * 4.JDK提供的URLEncoder
 * @author calvin
 * @version 2013-01-15
 */
public class Encodes {

	private static Log log =LogFactory.getLog(Encodes.class);
	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	private static final String KEY_ALGORITHM = "AES";
	private static final String IV="1234567812345678";
	
	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	 */
	public static String encodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base62编码。
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String escapeXml(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8. 
	 */
	public static String urlDecode(String part) {

		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}
	
	public static String encodeAES(byte[] source,String key){
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			Key secretKey =getAESKeyForPKCS7Padding(key);
			//使用加密模式初始化 密钥
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));
			byte[] encrypt = cipher.doFinal(source); //按单部分操作加密或解密数据，或者结束一个多部分操作。
			
			return  Base64.encodeBase64String(encrypt);
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
		}
		return "";
	}
	
	public static byte[]  decodeAES(String dest,String key){
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			Key secretKey =getAESKeyForPKCS7Padding(key);
			
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV.getBytes()));//使用解密模式初始化 密钥
			return(cipher.doFinal(decodeBase64(dest)));
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage());
			}
		}
		return new byte[]{};
	}
	
	private static  Key getAESKeyForPKCS7Padding(String key){
		int base = 16;
		
		byte[] keyBytes = key.getBytes();
		
		/* 因为java没有PKCS7Padding的支持方式，
		 * 此处虽然了采用PKCS7Padding，
		 * 当自己实现补位后其实就不需要进行补位了，
		 * 实际使用的即为PKCS7Padding
		*/
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}
		
		return new SecretKeySpec(keyBytes, KEY_ALGORITHM);
	}
	
	public static void main(String args[]){
		String a = "{\"sign\":\"d7912479dddd40ffecb59b5c6cce4865a+XXT1LqrsVCbXEFX8RI0W0H6j+U0jpzzevcajKpGUS9QHy8N4QHp33SWapJ1F62ayuGQrNL18eUc+78CXuClSAojHsqGSV6f/HZox9mfmSMUmyI6mEab3/Hp0For1ppo9Sr4pk8z1u++M+pQISKcbEf+7H0yEMUDvDgmX/5Op4=\"}";
		Date d = new Date();
		for(int i=0;i<10000;i++){
			getRequestParameter(EntranceNoticeVo.class,a);
		}
		Date d1 = new Date();
		System.out.println((d1.getTime()-d.getTime()));
	}
	
	public static <E> E  getRequestParameter(Class<E> voClass,String sign){
		sign = (String) JSONObject.parseObject(sign).get("sign");
		if(StringUtils.isNotBlank(sign)){
			String MD5key = sign.substring(0, 32);
			String aesKey  = MD5key.substring(0,3)+MD5key.substring(8,11)+MD5key.substring(19, 22);// 1-3,9-11,20-22
			String jsonString=new String(Encodes.decodeAES(sign.substring(32),aesKey));
				return JSON.parseObject(jsonString,voClass);
		}
		return null;
	}
}
