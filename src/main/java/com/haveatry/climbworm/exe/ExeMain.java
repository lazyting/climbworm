package com.haveatry.climbworm.exe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.haveatry.climbworm.utils.FileUtils;
import com.haveatry.climbworm.utils.GetPageUtils;
import com.haveatry.climbworm.utils.JsoupUtils;

public class ExeMain {
	private static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {
		// System.out.println(System.getProperty("user.dir"));
		getPicts();
	}

	private static void getPicts() {
		for (int i = 1; i <= 10; i++) {
			final int index = i;
			threadPoolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					String ipPrefix = "https://www.68ps.com";
					String fullIp = ipPrefix + "/enjoy/?page=" + index;
					String html = GetPageUtils.getPageFullMethod(fullIp);
					List<String> pictNewAdds = JsoupUtils.getElements(html, "target", "_blank", "attr", "href", "");
					if (pictNewAdds != null && pictNewAdds.size() > 0 && !pictNewAdds.isEmpty()) {
						for (int j = 0; j < pictNewAdds.size(); j++) {
							String html1 = GetPageUtils.getPageFullMethod(ipPrefix + pictNewAdds.get(j));
							List<Map<String, Object>> fileName = (List<Map<String, Object>>) JsoupUtils
									.getElements(html1, "h1", "", "", "", "", true);
							List<Map<String, Object>> pictNewAddss = (List<Map<String, Object>>) JsoupUtils
									.getElements(html1, "img", "", "", "attr", "src", true);
							if (pictNewAddss != null && pictNewAddss.size() > 0 && !pictNewAddss.isEmpty()) {
								for (int k = 0; k < pictNewAddss.size(); k++) {
									if (!pictNewAddss.get(k).get("value").toString().contains("ztjc_btn")
											&& !pictNewAddss.get(k).get("value").toString().contains("beian")) {
										System.out.println(pictNewAddss.get(k).get("value"));
										FileUtils.downloadPicture(pictNewAddss.get(k).get("value").toString(),
												fileName.get(0).get("value").toString(), "pict_" + k + ".jpg");
									}
								}
								//
							}
						}
					}
				}
			});
		}
	}
}
