package com.fanshuo.android.bestnotes.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.fanshuo.android.bestnotes.Constants;
import com.fanshuo.android.bestnotes.app.model.BestNotesVersionInfoModel;
import com.fanshuo.android.bestnotes.app.utils.Debug;

import android.content.SyncContext;
import android.util.Log;

/**
 * @author shuo
 * @date 2013-1-14 下午3:47:15
 * @version V1.0
 */
public class BestNotesNetUtil {

	public static enum HttpMethod {
		GET, POST
	}

	public BestNotesNetUtil() {

	}

	public String syncConnect(final String url,
			final Map<String, String> params, final HttpMethod method) {
		String json = null;
		BufferedReader reader = null;

		try {
			HttpClient client = new DefaultHttpClient();
			HttpUriRequest request = getRequest(url, params, method);
			Debug.d("syncConnect-[REQUEST]:[URL]=>" + request.getURI()
					+ "[PARAMS]=>" + request.getParams().toString());
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuilder sb = new StringBuilder();
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					sb.append(s);
				}

				json = sb.toString();
				Debug.d("syncConnect-[RESPONSE]:" + json);
			} else {
				Debug.d("syncConnect-[ERROR]:"
						+ response.getStatusLine().toString());
			}

		} catch (ClientProtocolException e) {
			Debug.e( e.getMessage(), e);
		} catch (IOException e) {
			Debug.e(e.getMessage(), e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// ignore me
			}
		}
		return json;
	}

	private HttpUriRequest getRequest(String url, Map<String, String> params,
			HttpMethod method) {

		if (method.equals(HttpMethod.POST)) {
			List<NameValuePair> listParams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String name : params.keySet()) {
					listParams.add(new BasicNameValuePair(name, params
							.get(name)));
				}
			}
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						listParams);
				HttpPost request = new HttpPost(url);
				request.setEntity(entity);
				return request;
			} catch (UnsupportedEncodingException e) {
				// Should not come here, ignore me.
				throw new java.lang.RuntimeException(e.getMessage(), e);
			}
		} else {
			if (url.indexOf("?") < 0) {
				url += "?";
			}
			if (params != null) {
				for (int i = 0; i < params.keySet().size(); i++) {
					String name = (String) params.keySet().toArray()[i];
					if (i == 0) {
						url += name + "=" + URLEncoder.encode(params.get(name));
					} else {
						url += "&" + name + "="
								+ URLEncoder.encode(params.get(name));
					}
				}
			}
			HttpGet request = new HttpGet(url);
			return request;
		}
	}
	
	/**
	 * 检查更新，返回服务器上的版本信息
	 * @return
	 */
	public BestNotesVersionInfoModel checkUpdate(){
		BestNotesVersionInfoModel info = new BestNotesVersionInfoModel();
		Map<String, String> params = new HashMap<String, String>();
		params.put("msgid", "checkupdate");
		String json = syncConnect(Constants.API_URLS.API_CHECK_UPDATE, params, HttpMethod.POST);
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			info.setVersionCode(obj.optInt("versioncode"));
			info.setVersionName(obj.optString("versionname"));
			info.setUpdateInfo(obj.optString("versioninfo"));
			info.setImportant(obj.optBoolean("important"));
			info.setLink(obj.optString("link"));
		} catch (JSONException e) {
			Debug.e("JSONException", e);
			return null;
		}
		return info;
		
	}

}
