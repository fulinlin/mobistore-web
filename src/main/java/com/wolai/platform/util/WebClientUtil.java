package com.wolai.platform.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.wolai.platform.constant.HttpServerConstants;

/**
 * 远程访问工具
 * @author Ethan
 *
 */
public class WebClientUtil {
	
		public static	Log logger = LogFactory.getLog(WebClientUtil.class);
	
	   public  static String post(String url, String json) {
	    	String resultJson = "";
	    	// 创建默认的httpClient实例.
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        
	        // 创建httppost
	        HttpPost httppost = new HttpPost(url);
	        // 设置超时时间
	        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000).setConnectTimeout(2000)  
	        	    .setSocketTimeout(50).build();
	        httppost.setConfig(requestConfig);
	        
	        CloseableHttpResponse response = null;
	        try {
	        	StringEntity entity = new StringEntity(json, HttpServerConstants.Encoding);
	        	entity.setContentEncoding(HttpServerConstants.Encoding);
	        	entity.setContentType(HttpServerConstants.ContentType);
	        	httppost.setEntity(entity);
	        	response = httpclient.execute(httppost);
	    		HttpEntity result = response.getEntity();
	    		resultJson = EntityUtils.toString(result, HttpServerConstants.Encoding);
	        } catch (Exception e) {
	        	if(logger.isDebugEnabled()){
	        		logger.debug(Exceptions.getStackTraceAsString(e));
	        	}
	        } finally {
	        	// 关闭连接,释放资源
	        	if (response != null){
	        		try{response.close();}catch(IOException e){}
	        	}
	        	try{httpclient.close();}catch(IOException e){}
	        }
	        return resultJson;
	    }
}
