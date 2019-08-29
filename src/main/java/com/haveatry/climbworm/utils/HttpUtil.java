package com.haveatry.climbworm.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

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

    public static String httpPost(String url, String requestParams) {
        if (StringUtils.isEmpty(url))
            throw new RuntimeException("url is null");
        JSONObject jb = new JSONObject();
        jb.put("code", 0);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(300 * 1000)
                    .setConnectTimeout(300 * 1000)
                    .build();
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            StringEntity postingString = new StringEntity(requestParams,
                    "utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);
            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);
            return content;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            System.out.println("调用Dat+"
                    + ".aService接口超时,超时时间:" + 300
                    + "秒,url:" + url + ",参数：" + requestParams);
            return jb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用DataService接口失败,url:" + url + ",参数：" + requestParams);
            return jb.toString();
        }
    }

    /**
     * 发送Http post请求
     * 使用java URL
     *
     * @param xmlInfo json转化成的字符串
     * @param URL     请求url
     * @return 返回信息
     */
    public static String doHttpPost(String xmlInfo, String URL) {
        if (StringUtils.isEmpty(URL))
            throw new RuntimeException("url is null");
        System.out.println("发起的数据:" + xmlInfo);
        byte[] xmlData = xmlInfo.getBytes();
        InputStream instr = null;
        java.io.ByteArrayOutputStream out = null;
        try {
            URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("content-Type", "application/json");
            urlCon.setRequestProperty("charset", "utf-8");
            urlCon.setRequestProperty("Content-length",
                    String.valueOf(xmlData.length));
            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(
                    urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println("返回空");
            }
            System.out.println("返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (instr != null) {
                    instr.close();
                }
            } catch (Exception ex) {
                return "0";
            }
        }
    }
}
