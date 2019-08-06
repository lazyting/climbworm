package com.haveatry.climbworm.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static HttpURLConnection getConnection(String address) throws IOException {
        URL url = new URL(address);
        // 打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		java.io.IOException: Server returned HTTP response code: 403 for URL
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 设置请求方式为"GET"
        conn.setRequestMethod("GET");
        // 超时响应时间为5秒
        conn.setConnectTimeout(6 * 1000);
        return conn;
    }

    /**
     * 根据ip和端口组装host参数
     *
     * @param host
     * @param port
     * @return
     */
    public static HttpHost getHttpHost(String host, Integer port) {
        return new HttpHost(host, port);
    }

    /**
     * 根据要访问的地址和host参数获得get方法
     *
     * @param adddress
     * @param proxy
     * @return
     */
    public static HttpGet getHttpGet(String adddress, HttpHost proxy) {
        HttpGet httpGet = new HttpGet(adddress);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(config);// 使用动态ip ，处理网站反爬虫机制
        try {
            config = RequestConfig.custom()
                    .setConnectTimeout(Integer.parseInt(ResourceUtil.getPropertyValue("connecttimeout")))
                    .setSocketTimeout(Integer.parseInt(ResourceUtil.getPropertyValue("sockettimeout"))).build();// 设置连接时间，读取时间
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpGet.setConfig(config);
        // 模拟浏览器进行访问，需要证书设置
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        return httpGet;
    }
    /**
     * 根据要访问的地址和host参数获得get方法
     *
     * @param adddress
     * @return
     */
    public static HttpGet getHttpGet(String adddress) {
        HttpGet httpGet = new HttpGet(adddress);
        // 模拟浏览器进行访问，需要证书设置
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        return httpGet;
    }
}
