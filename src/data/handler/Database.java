package data.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import data.Player;

/**
 * 데이터베이스에 접근할 수 있는 클래스이다.<br/>
 * Singleton 패턴을 따르므로, 해당 프로그램 내에선 객체가 단 하나밖에 존재하지 않는다.
 * 
 * @author Ted
 *
 */
public class Database {
	
	private static Database dh;
	//private String ip = "13.124.202.64"; //계속 바뀜
	private String ip ="13.124.194.183";
	//private String port = "50277"; //계속 바뀜
	private String port = "58175";
	private String db = "userdb";
	private String id = "root";
	private String pw = "mysql1234";
	
	private static Connection con;
	
	private Database() {
    	try {
    		//Class.forName("con.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+db, id, pw);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 객체를 얻어오는 메소드
	 * @return
	 */
	public static Database getInstance() {
		if(dh == null) {
			dh = new Database();
		}
		return dh;
	}
	
//	//TODO 테스트
//	public String getScore() {
//		String result = "asd";
//		try {
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery("select * from user where user_id = '"+id+"' and user_pw = '"+pw+"'");
//			while(rs.next()) {
//				result += rs.getString("id") + 
//						rs.getString("user_id") + 
//						rs.getString("user_pw") + 
//						rs.getString("user_score_1") + 
//						rs.getString("user_score_2") +
//						rs.getString("user_score_3");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	/**
	 * 주어진 id와 password로 로그인을 시도한다.
	 * 로그인에 성공하면 true를 반환한다.
	 * 
	 * @param id
	 * @param pw
	 * @return
	 */
	public boolean login(String id, String pw) {
		String user_id = "";
		String user_pw = "";
		boolean result = false;
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where user_id = '"+id+"' and user_pw = '"+pw+"'");
			if(rs.next()) {
				user_id = rs.getString("user_id");
				user_pw = rs.getString("user_pw");
			}
			//System.out.println(user_id);
			//System.out.println(user_pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(user_id.equals(id) && user_pw.equals(pw)) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * 입력한 id와 password를 가진 유저의 데이터를 가져온다. 가져올 데이터는 id, nickname, socre[] 이다.
	 * 
	 * @param id
	 * @param pw
	 * @return
	 */
	public Player getPlayerInfo(String id, String pw) {
		String user_id = "";
		String user_nickname = "";
		int[] user_score = {0, 0, 0};
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where user_id = '"+id+"' and user_pw = '"+pw+"'");
			if(rs.next())  {
				user_id = rs.getString("user_id");
				user_nickname = rs.getString("user_nickname");
				for(int i=0;i<3;i++) {
					user_score[i] = rs.getInt(i+5);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new Player(user_id, user_nickname, user_score);
	}
	
	
	public class PlayerScore{
		public String nickname;
		public int score;
		
		PlayerScore(String nickname, int score) {
			this.nickname = nickname;
			this.score = score;
		}
	}
	
	/**
	 * 유저의 닉네임과 점수를 불러온다.
	 * type에 따라 가져올 점수가 결정된다.
	 * 1: 같은그림찾기, 4: 총합
	 * @param type
	 * @return
	 */
	public PlayerScore[] getUsersScoreTop8(int type) {
		PlayerScore[] ps = new PlayerScore[8];
		String nickname = "";
		int score = 0;
		
		String query = "";
		if(type != 4){
			query = "select * from user order by user_score_"+type+" desc limit 8";
		}
		else {
			query = "select user_nickname, user_score_1+user_score_2+user_score_3 as total from user order by total desc limit 8";
		}
		
		int i = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next())  {
				nickname = rs.getString("user_nickname");
				//System.out.println(nickname);
				if(type == 4) {
					score = rs.getInt("total");
				}
				else {
					score = rs.getInt("user_score_"+type);
					//System.out.println(score);
				}
				ps[i++] = new PlayerScore(nickname, score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("ps 생성 및 반환");
		return ps;
	}
	
	/**
	 * 입력한 정보를 토대로 데이터베이스에 등록시킨다.
	 * @param id
	 * @param nickname
	 * @param pw
	 */
	public void signup(String id, String nickname, String pw) {
		String query = "insert into user values(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setNull(1, Types.INTEGER);
			pstm.setString(2, id);
			pstm.setString(3, pw);
			pstm.setString(4, nickname);
			pstm.setInt(5, 0);
			pstm.setInt(6, 0);
			pstm.setInt(7, 0);
			pstm.executeUpdate();
			System.out.println("update 완료");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 데이터베이스에 id값과 같은 user_id를 가진 튜플이 있는지 확인한다. 만약 이미 존재한다면 true를 반환한다.
	 * 
	 * @param id
	 * @return
	 */
	public boolean isThereID(String id) {
		String result = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where user_id = '"+id+"'");
			if(rs.next()) {
				System.out.println("이미 ID 존재");
				result += rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//DB에 값이 존재하면 result에 값이 채워짐.
		return !result.isEmpty();
	}
	
	/**
	 * 데이터베이스에 ninkname값과 같은 user_nickname을 가진 튜플이 있는지 확인한다. 만약 이미 존재한다면 true를 반환한다.
	 * 
	 * @param nickname
	 * @return
	 */
	public boolean isThereNickname(String nickname) {
		String result = "";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where user_id = '"+nickname+"'");
			if(rs.next()) {
				System.out.println("이미 ID 존재");
				result += rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//DB에 값이 존재하면 result에 값이 채워짐.
		return !result.isEmpty();
	}
}
