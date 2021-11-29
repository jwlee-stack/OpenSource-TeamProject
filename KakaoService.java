package api2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.net.URISyntaxException; 
import java.awt.Desktop;
import java.net.URI;




import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//api연습
//1517b1e1f1e567714855a28d91fa05b6
//https://localhost:5500/examples/html/kakao_login.html


public class KakaoService {
	
	public static String getAuthorize_Code() {
		String code="";
		String reqURL = "https://kauth.kakao.com/oauth/authorize?client_id=1517b1e1f1e567714855a28d91fa05b6&redirect_uri=https://localhost:5500/examples/html/kakao_login.html&response_type=code";
		try { 
			
			Desktop.getDesktop().browse(new URI("https://kauth.kakao.com/oauth/authorize?client_id=1517b1e1f1e567714855a28d91fa05b6&redirect_uri=https://localhost:5500/examples/html/kakao_login.html&response_type=code")); 
			
		} 
		catch (IOException e) {
			e.printStackTrace(); 
		} 
		catch (URISyntaxException e) {
			e.printStackTrace(); 
		}

		

		
		return code;
	}

	public static String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=1517b1e1f1e567714855a28d91fa05b6");
			sb.append("&redirect_uri=https://localhost:5500/examples/html/kakao_login.html"); 
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		
		return access_Token;
	}
	
	
	public static String getAccessTokenAgain(String refresh_Token) {
		String access_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=refresh_token");
			sb.append("&client_id=1517b1e1f1e567714855a28d91fa05b6");
			sb.append("&refresh_token=" + refresh_Token);
			bw.write(sb.toString());
			bw.flush();

			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		
		return access_Token;
	}

	

	public static HashMap<String, Object> getUserInfo(String access_Token) {

		
		HashMap<String, Object> userInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			long id = element.getAsJsonObject().get("id").getAsLong();
			//JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

			
			//boolean email_needs_agreement = kakao_account.getAsJsonObject().get("email_needs_agreement").getAsBoolean();

			
//			String email = "이메일 동의 항목에 사용자 동의 필요";
//			if (email_needs_agreement == false) {
//				email = kakao_account.getAsJsonObject().get("email").getAsString();
//			}

			
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//			String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
//			String thumbnail_image = properties.getAsJsonObject().get("thumbnail_image").getAsString();

			userInfo.put("id", id);
			userInfo.put("nickname", nickname);
//			userInfo.put("email", email);
//			userInfo.put("profile_image", profile_image);
//			userInfo.put("thumbnail_image", thumbnail_image);


		} catch (IOException e) {
			
			e.printStackTrace();
		}

		
		return userInfo;
	}
	
	public static HashMap<String, Object> getFriend(String access_Token){
		HashMap<String, Object> friendInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v1/api/talk/friends";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);
			
			
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);
			
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
		
			
			JsonObject elements = element.getAsJsonObject().get("elements").getAsJsonObject();
			int total_count = element.getAsJsonObject().get("total_count").getAsInt();
			
			
//			long id = elements.getAsJsonObject().get("id").getAsLong();
//			String uuid = elements.getAsJsonObject().get("uuid").getAsString();
//			String profile_nickname = elements.getAsJsonObject().get("profile_nickname").getAsString();
//			

//			friendInfo.put("id", id);
//			friendInfo.put("uuid", uuid);
//			friendInfo.put("profile_nickname", profile_nickname);
			
			friendInfo.put("total_count", total_count);
			
			

		}
		catch (IOException e) {
			
			e.printStackTrace();
		}
		 return friendInfo;
	}
	
	public static void main(String[] args) {
		String a ="dXCww7c-I-RJ0p4tGaL15g6bswExvzvAFDdLFDsO8pt-tl1mRKVY_A3bIGMvix33bnOmwQo9dNoAAAF9aZVN5A";
		String code = getAuthorize_Code();
		String access_token = "QG5IiSdgJ-EIT75CPwp-LQ2aQTnXyMqDP0l8Igo9cuoAAAF9aZWmxw";//getAccessToken(a);
		HashMap<String, Object> userInfo = getUserInfo(access_token);
		System.out.println("id : " + userInfo.get("id"));
		System.out.println("nickname : " + userInfo.get("nickname"));
		
		
		
	}
	
	
}
