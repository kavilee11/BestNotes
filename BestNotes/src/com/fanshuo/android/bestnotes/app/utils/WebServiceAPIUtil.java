package com.fanshuo.android.bestnotes.app.utils;

import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fanshuo.android.bestnotes.app.model.DistanceData;

/**
 * @author shuo
 * @date 2013-1-21 上午10:32:33
 * @version V1.0
 */
public class WebServiceAPIUtil {
	
	/**
	 * 
	 * @param origin 一个或多个地址和/或文本纬度/经度值（以竖线字符 (|) 进行分隔），作为起点以计算距离和时间。如：Bobcaygeon+ON|41.43206,-81.38992
	 * @param destination  一个或多个地址和/或文本纬度/经度值（以竖线字符 (|) 进行分隔），作为终点以计算距离和时间。如：Darling+Harbour+NSW+Australia|24+Sussex+Drive+Ottawa+ON|Capitola+CA
	 */
	public static DistanceData getDistance(String origin, String destination) {
		DistanceData data = new DistanceData();
		String systemLanguage = Locale.getDefault().getLanguage();
		String language;
		if(systemLanguage.equals("zh")){
			language = "zh-CN";
		}else{
			language = "en";
		}
		String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin + "&destinations=" + destination + "&sensor=false&mode=walking&language="+language;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		try {
		HttpResponse response = httpClient.execute(get);
		String resultString = EntityUtils.toString(response.getEntity());
		JSONObject jsonresult = new JSONObject(resultString);
		if (jsonresult.optJSONArray("rows") != null) {
			JSONArray elements = jsonresult.optJSONArray("rows")
					.optJSONObject(0).optJSONArray("elements");
			String text = elements.optJSONObject(0).optJSONObject("distance").optString("text");
			String value = elements.optJSONObject(0).optJSONObject("distance").optString("value");
			data.setDistanceText(text);
			data.setDistanceValue(value);
			return data;
		}
		else{
			return null;
		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
