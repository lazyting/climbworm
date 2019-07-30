package com.haveatry.climbworm.page;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;

import com.haveatry.climbworm.utils.FileUtils;
import com.haveatry.climbworm.utils.GetPageUtils;
import com.haveatry.climbworm.utils.IPUtil;
import com.haveatry.climbworm.utils.JsoupUtils;

public class Test {
	private static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(5);

	public static void main(String[] args) throws Exception {
		// String getAttr="111112222222211";
		// String regex = "[\\u4e00-\\u9fa5]+";
		// Pattern p = Pattern.compile(regex);
		// if (!p.matcher(getAttr).find()) {
		// System.out.println(1);
		// }else{
		// System.out.println(2);
		// }
		// getPict();
		// String newPictHtml =
		// GetPageUtils.getPageFullMethod("http://www.aiimg.com/photoshop/201708/96409.html");
		// System.out.println(newPictHtml);
		// List<String> divs = JsoupUtils.getElements(newPictHtml, "class",
		// "imgbox", "attr", "src", "img");
	}

	/**
	 * 
	 */
	public static void getPict() {
		for (int i = 1; i < 10; i++) {
			final int index = i;
			threadPoolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					String ip = IPUtil.PICT_ADDRESS + index + ".html";
					String html = GetPageUtils.getPageFullMethod(ip);
					List<String> pictNewAdds = JsoupUtils.getElements(html, "target", "_blank", "attr", "href", "");
					String www = IPUtil.PICT_ADDRESS.substring(0, IPUtil.PICT_ADDRESS.indexOf("com") + 3);
					String newAdd = null;
					for (int a = 0, b = pictNewAdds.size(); a < b; a++) {
						if (pictNewAdds.get(a) != null) {
							if (!pictNewAdds.get(a).contains("www")) {
								newAdd = www + pictNewAdds.get(a);
							} else {
								newAdd = pictNewAdds.get(a);
							}
							String newPictHtml = GetPageUtils.getPageFullMethod(newAdd);
							List<String> divs = JsoupUtils.getElements(newPictHtml, "class", "imgbox", "attr", "src",
									"img");
							if (divs != null && divs.size() > 0 && !divs.isEmpty()) {
								FileUtils.downloadPicture(divs.get(0), String.valueOf(a), "pict_" + a + ".png");
							}
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

//	public static void getPict1() {
//
//		for (int i = 0, z = Integer.valueOf(maxPage); i < z; i++) {
//			final int index = i;
//			threadPoolExecutor.execute(new Runnable() {
//				@Override
//				public void run() {
//					String tragetIP = IPEnum.DONGMANPICT + index + ".htm";
//					String firstPage = GetPageUtils.getPageFullMethod(tragetIP);
//					String ulPage = OtherUtils
//							.turn(JSoupUtils.getElements(firstPage, "", "class", "textList", "", "", false));
//					List<Map<String, Object>> aHtmlHrefs = JSoupUtils.getElements(ulPage, "", "target", "_blank",
//							"attr", "href", true);
//					// String newPictHtml =
//					for (int a = 0, b = aHtmlHrefs.size(); a < b; a++) {
//						String dirName = aHtmlHrefs.get(a).get("name").toString();
//						String newAdd = aHtmlHrefs.get(a).get("value").toString();
//						String newPictHtml = GetPageUtils.getPageFullMethod(IPEnum.prufix + newAdd);
//						List<Map<String, Object>> imgSrcs = JSoupUtils.getElements(newPictHtml, "img", "", "", "attr","src",
//								"true");
//						if (imgSrcs != null && imgSrcs.size() > 0 && !imgSrcs.isEmpty()) {
//							for (int j = 0,d=imgSrcs.size(); j <d ; j++) {
//								FileUtils.downloadPicture(imgSrcs.get(j).get("value").toString(), dirName.substring(dirName.indexOf("\\]"), dirName.indexOf("P\\]")), "pict_" + j + ".jpg");
//							}
//							
//						}
//					}
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
//		}
//	}

	/**
	 * 刚进程序要先运行这个方法
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	private static void getIPsMehod() throws ParseException, IOException {
		FileUtils.writeFile(IPUtil.getIPsMehod());
		// List<Map<String, Object>> aList = JSoupUtils.getElements(firstPage,
		// "a", "", "", "attr", "href", true);
		List<Map<String, Object>> aList = null;
		System.out.println(aList);
		String maxPage = null;
		if (aList != null && aList.size() > 0) {
			for (int i = aList.size() - 1; i >= 0; i--) {
				String lastPage = aList.get(i).get("name").toString();
				System.out.println(lastPage);
				if (StringUtils.equals("尾页", lastPage)) {
					String lastPageHref = aList.get(i).get("value").toString();
					maxPage = lastPageHref.substring(0, lastPageHref.indexOf("."));
					break;
				}
			}
		}
		System.out.println(maxPage);
	}

}
