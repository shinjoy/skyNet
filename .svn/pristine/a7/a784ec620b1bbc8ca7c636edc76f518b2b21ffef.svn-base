package kr.nomad.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SendSms {

	public void sendSmS(String clientId, String authCode, String receiverNumber, String userName, String title, String message) throws Exception{
		
		String getURL = "http://smmunja.nomadsoft.com/api/send.go";
		getURL += "?clientId="+ clientId ;
		getURL += "&authCode="+ authCode ;
		getURL += "&title="+ title;
		getURL += "&msg="+ message ;
		getURL += "&isReservation=0" ;
		getURL += "&receiverNumber="+ receiverNumber;
		getURL += "&receiverName="+ userName;
		getURL += "&sendType=sms" ;
		
		
		//String getURL = GET_URL + "?name=" + URLEncoder.encode("zhangshan", "utf-8");
		URL getUrl = new URL(URLEncoder.encode(getURL, "UTF-8"));
		//URL getUrl = new URL(URLEncoder.encode(getURL, "utf-8"));
		// 끌어모았어 URL을 열 따라 연결할 URL 형식은 따라 할 URL.openConnection 함수, 
		// 다시 다른 URLConnection 하위 클래스 대상, 여기 URL 한 http 때문에 실제 복귀 것은 HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		// 을 연결 하지만 실제로는 get request 반xp드시 다음 구의 connection.getInputStream () 함수 중 비로소 진정한 발 까지 서버
		connection.connect();
		// 확실한 입력 및 사용 Reader 읽기
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 인코딩 설정 을 함께 했 다., 그렇지 않으면, 중국어
		System.out.println("=============================");
		System.out.println("Contents of get request");
		System.out.println("=============================");
		String lines;
		String msg = "";
		while ((lines = reader.readLine()) != null) {
			String str = new String(lines.getBytes());
			msg += URLDecoder.decode(str, "UTF-8");
			System.out.println(lines);
		}
		reader.close();
		// 연결 끊기
		connection.disconnect();
	}

}
