package com.haveatry.climbworm.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {
	static URL url = FileUtils.class.getClassLoader().getResource("ip.txt");  
	/**
	 * 获取的ip存入到文件
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeFile(Object object) throws IOException {
		File file = new File("d:\\ip.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		if (object instanceof List) {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			if (object instanceof List) {
				for (Map<String, Object> map : (List<Map<String, Object>>) object) {
					bw.write(map.get("ip") + ":" + map.get("port") + "\r\n");
				}
			}
			bw.close();
			osw.close();
		}
	}

	public static List<Map<String, Object>> getIPsFromFile(String url) {
		List<Map<String, Object>> maps = new ArrayList<>();
		File file = new File(url);
		String line = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			br = new BufferedReader(reader);
			Map<String, Object> map = null;
			while ((line = br.readLine()) != null) {
				map = new HashMap<>();
				map.put("ip", line.split(":")[0]);
				map.put("port", line.split(":")[1]);
				maps.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return maps;
	}

	public static void downloadPicture(String urlList, String index, String name) {
		URL url = null;
		try {
			url = new URL(urlList);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());

			File file = new File(System.getProperty("user.dir")+"\\" + index + "\\" + name);
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdir();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			byte[] context = output.toByteArray();
			fileOutputStream.write(output.toByteArray());
			dataInputStream.close();
			fileOutputStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void getPict(String address,String dirName,String fileName) throws Exception {
		HttpURLConnection conn = HttpUtil.getConnection(address);
		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();
		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = readInputStream(inStream);
		// new一个文件对象用来保存图片，默认保存当前工程根目录
		File imageFile = new File("C:\\download\\" + dirName + "\\" + fileName);
		File parentFile = imageFile.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdir();
		}
		// 创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		// 写入数据
		outStream.write(data);
		// 关闭输出流
		outStream.close();
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
}
