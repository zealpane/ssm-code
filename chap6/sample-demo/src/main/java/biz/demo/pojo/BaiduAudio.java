package biz.demo.pojo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class BaiduAudio {

	private static String accessToken;
	
	// 首先要定义一个全局参数，用来被其它地方引用
	public static String getAccessToken() {
		if (accessToken == null) {
			baiduAudio();
		}
		return accessToken;
	}
	
	static void baiduAudio() {
		// 获得http客户端
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 构造get请求，这里需要根据自己的账号信息写入测试
		HttpGet httpGet = new HttpGet("https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" +
				"&client_id=Fb44vTEFeFzWcd9wNCRG0X40" +
				"&client_secret=ewbTkAAQMap2eI0e4j9ONmRGYyoP5Gl0"
		);
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 执行get请求
			response = httpClient.execute(httpGet);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			String resString = EntityUtils.toString(responseEntity);
			JSONObject jsonObj = JSONObject.parseObject(resString);
			accessToken = jsonObj.getString("access_token");
			System.out.println("token：" + accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
