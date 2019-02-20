package com.haveatry.climbworm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetPageUtils {
	private static int pageSize = 1;

	/**
	 * 发送HttpGet请求获取网页
	 * 
	 * @return
	 */
	public static String httpGetPage(String adddress) {
		String page = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(adddress);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(httpGet);
			entity = response.getEntity();// 获取返回实体
			page = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return page;
	}

	/**
	 * 使用java自带io流获取网页
	 * 
	 * @return
	 */
	public static String ioGetPage(String adddress) {
		String page = null;
		String line = null;
		StringBuffer pageSB = new StringBuffer();
		BufferedReader in = null;
		try {
			URL url = new URL(adddress);
			URLConnection con = url.openConnection();
			con.connect();
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = in.readLine()) != null) {
				pageSB.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return page = pageSB.toString();
	}

	/**
	 * 模拟浏览器进行访问，会报证书异常
	 * 
	 * @return
	 */
	public static String httpLikeBrower(String adddress) {
		String page = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(adddress);
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();// 获取返回实体
			page = EntityUtils.toString(entity, "utf-8");
			// 查看响应类型
			System.out.println(entity.getContentType().getValue());
			System.out.println(response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// HTTP/1.1 200 OK 200
			try {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return page;
	}

	public static String getPageFullMethod(String adddress) {
		String page = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(adddress);
		// 使用动态ip start ，处理网站反爬虫机制
		HttpHost proxy = null;
		List<Map<String, Object>> ips = null;
		if (ips == null) {
			ips = FileUtils.getIPsFromFile();
		}
		if (ips != null && ips.size() > 0 && pageSize < ips.size()) {
			System.out.println("pageSize:" + pageSize);
			System.out.println(ips.get(pageSize).get("ip") + ":" + ips.get(pageSize).get("port"));
			proxy = new HttpHost((String) ips.get(pageSize).get("ip"),
					Integer.parseInt((String) ips.get(pageSize).get("port")));
		}
		RequestConfig config = null;
		config = RequestConfig.custom().setProxy(proxy).build();
		httpGet.setConfig(config);
		// 使用动态ip end ，处理网站反爬虫机制
		// 设置连接时间，读取时间start
		config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
		httpGet.setConfig(config);
		// 设置连接时间，读取时间end
		// 模拟浏览器进行访问，需要证书设置
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
		try {
			CloseableHttpResponse httpResponse = client.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 403) {
				pageSize++;
			}
			HttpEntity entity = httpResponse.getEntity();
			String content = EntityUtils.toString(entity);
			page = new String(content.getBytes("utf-8"), "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}
}
