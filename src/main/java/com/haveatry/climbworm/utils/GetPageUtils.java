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
		HttpGet httpGet = HttpUtil.getHttpGet(adddress);
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
		HttpHost proxy = null;
		List<Map<String, Object>> ips = null;
		if (ips == null) {
			try {
				ips = FileUtils.getIPsFromFile(ResourceUtil.getPropertyValue("ip-address"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (ips != null && ips.size() > 0 && pageSize < ips.size()) {
			System.out.println("pageSize:" + pageSize);
			System.out.println(ips.get(pageSize).get("ip") + ":" + ips.get(pageSize).get("port"));
			proxy = HttpUtil.getHttpHost(ips.get(pageSize).get("ip").toString(),Integer.parseInt(ips.get(pageSize).get("port").toString()));
		}
		HttpGet httpGet = HttpUtil.getHttpGet(adddress,proxy);
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
