package com.tinypace.mobistore.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SmsUtil {
	private static Log log = LogFactory.getLog(SmsUtil.class);  
	
	public static String NAME = "wolai@wolaitech.com";
	public static String PASSWORD = "EB5B8F6FAC215B5DAA8C911AE1D8";
	
	public static void send(String mobile, String content) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://sms.1xinxi.cn/asmx/smsservice.aspx");  
        // 创建参数队列    
        // name=test&=112345&content=testmsg&mobile=13566677777,18655555555&stime=2012-08-01 8:20:23&sign=testsign&type=pt&extno=123
        List<NameValuePair> params = new ArrayList<NameValuePair>();  
        params.add(new BasicNameValuePair("name", NAME));
        params.add(new BasicNameValuePair("pwd", PASSWORD));
        params.add(new BasicNameValuePair("content", content));
        params.add(new BasicNameValuePair("mobile", mobile));
        params.add(new BasicNameValuePair("type", "pt"));
        
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(params, "UTF-8");  
            httppost.setEntity(uefEntity);  
            log.info("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {   
                	log.info("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  

	public static void main(String[] args) {
		// 多个手机号以英文逗号隔开
		SmsUtil.send("18626203266", "TEST MSG");
	}
}
