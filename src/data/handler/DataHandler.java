package data.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataHandler {
	
	private static DataHandler dh;
	private String ip = "13.124.82.133";
	private String port = "54830";
	private String db = "userdb";
	private String id = "root";
	private String pw = "mysql1234";
	
	private static Connection con;
	
	private DataHandler() {
    	try {
    		//Class.forName("con.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+db, id, pw);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DataHandler getInstance() {
		if(dh == null) {
			dh = new DataHandler();
		}
		return dh;
	}
	
	public static String getScore(String id, String pw) {
		String result = "asd";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where user_id = '"+id+"' and user_pw = '"+pw+"'");
			while(rs.next()) {
				result += rs.getString("id") + 
						rs.getString("user_id") + 
						rs.getString("user_pw") + 
						rs.getString("user_score_1") + 
						rs.getString("user_score_2") +
						rs.getString("user_score_3");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		DataHandler dh = DataHandler.getInstance();
		System.out.println(dh.getScore("admin", "admin"));
		System.out.println(dh.getScore("test", "test123"));
	}
}
