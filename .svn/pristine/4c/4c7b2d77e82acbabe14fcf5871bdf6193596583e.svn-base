package kr.nomad.util.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class PushNotification {
	public void sendPushServer(PushMsg pushMsg) {

		try {
			String msgType = pushMsg.getMsgType();
			String msgKey = pushMsg.getMsgKey();
			
			String msg = URLEncoder.encode(pushMsg.getMsg(), "UTF-8");
			String pushList = URLEncoder.encode(pushMsg.pushList, "UTF-8");;
			
			String urlstr = "http://localhost:9080/sendPush.do?msg="
					+ msg
					+"&msgType="
					+ msgType 
					+"&msgKey="
					+ msgKey 
					+"&pushList="
					+ pushList
					+ "&siteId="
					+ Constant.SITEID
					;
			
			
			HttpURLConnection conn;
			BufferedReader rd;
			String line;
			String result = "";
			try {
				URL url = new URL(urlstr);
			    conn = (HttpURLConnection) url.openConnection();
			    conn.setRequestMethod("GET");
			    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = rd.readLine()) != null) {
			    	result += line;
			    }
			    rd.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
