package com.wolai.platform.controller.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.wolai.platform.util.Encodes;
import com.wolai.platform.util.WebClientUtil;

public class TestPost{
	public static void main(String[] args){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		//新利泊对账接口测试
		String url="http://36.110.25.198:90/checkaccount?token=b1d4163f9829453d9aeed855b02b4cbc";
		try{
		    Date queryDate=df.parse("2015-06-03");
			String json="{date:"+queryDate.getTime()+",timestamp:"+(new Date()).getTime()+"}";
			json=Encodes.sign(json);
			System.out.println("===========================json:"+json);
			String result=WebClientUtil.post(url,json);
			System.out.println("====================result:"+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    /**
     * 通过url获取POST信息
     * @param str_url
     * @return
     */
	public static String getPost(String str_url,String param){
		try {
			URL url = new URL(str_url);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setInstanceFollowRedirects(true);
			urlConn.connect();
			DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
			out.writeBytes(param);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"UTF-8"));
			String inputLine = null;
			while((inputLine = reader.readLine()) != null){
				System.out.println(inputLine);
			}
			reader.close();
			urlConn.disconnect();
			return inputLine;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	 /** 
	  * post方式提交表单（模拟用户登录请求） 
	  */  
	  public static void postForm(){
		  //创建默认的httpClient实例.   
		  CloseableHttpClient httpclient = HttpClients.createDefault();  
		  try{   
			    DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			    Date queryDate=df.parse("2015-06-03");
		        // 创建httppost    
		        HttpPost httppost = new HttpPost("http://36.110.25.198:90/checkaccount?token=b1d4163f9829453d9aeed855b02b4cbc");  
		        // 创建参数队列    
		        List<NameValuePair> formparams = new ArrayList<NameValuePair>();   
		        formparams.add(new BasicNameValuePair("date",String.valueOf(queryDate.getTime())));
		        formparams.add(new BasicNameValuePair("timestamp",String.valueOf((new Date()).getTime())));
		        UrlEncodedFormEntity uefEntity=new UrlEncodedFormEntity(formparams,"UTF-8");  
		        httppost.setEntity(uefEntity);  
		        System.out.println("executing request " + httppost.getURI());  
		        CloseableHttpResponse response = httpclient.execute(httppost);  
	            try {  
	                HttpEntity entity = response.getEntity();  
	                if (entity != null) {  
	                    System.out.println("--------------------------------------");  
	                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
	                    System.out.println("--------------------------------------");  
	                }  
	            } finally {  
	                response.close();  
	            }  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (UnsupportedEncodingException e1) {  
	            e1.printStackTrace();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            // 关闭连接,释放资源    
	            try {  
	                httpclient.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }
}
