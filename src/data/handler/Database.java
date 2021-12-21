package data.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JOptionPane;

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
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String IP = "13.124.194.183";
	private static final String PORT = "58175";
	private static final String DATABASE = "userdb";
	private static final String ID = "root";
	private static final String PW = "mysql1234";
	private static final String URL = "jdbc:mysql://"+IP+":"+PORT+"/"+DATABASE;
	
	private static Connection con;
	
	private Database() {
    	try {
    		Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, ID, PW);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Database 객체를 얻어오는 메소드
	 * @return
	 */
	public static Database getInstance() {
		if(dh == null) {
			dh = new Database();
		}
		return dh;
	}
	
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
			JOptionPane.showMessageDialog(null, "로그인 쿼리 오류", "오류", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "플레이어 정보 쿼리 오류", "오류", JOptionPane.ERROR_MESSAGE);
		}
		
		return new Player(user_id, user_nickname, user_score);
	}
	
	/**
	 * getUsersScoreTop8 메소드에서 쓰일 클래스(구조체).
	 * 닉네임과 점수를 동시에 보낼 수 있도록 만들어 졌다.
	 * @author phdljr
	 *
	 */
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
			JOptionPane.showMessageDialog(null, "랭크 데이터 쿼리 오류", "오류", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "회원 가입 쿼리 오류", "오류", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "ID 중복 체크 쿼리 오류", "오류", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "닉네임 중복 체크 쿼리 오류", "오류", JOptionPane.ERROR_MESSAGE);
		}
		
		//DB에 값이 존재하면 result에 값이 채워짐.
		return !result.isEmpty();
	}
	
	/**
	 * 현재 플레이어의 점수를 업데이트 시킨다. 데이터베이스로 바로 업데이트 된다.
	 * 
	 * @param player 아이디 가져오는 용도
	 * @param type 1,2,3
	 */
	public void updateScore(Player player, int type) {
		String id = player.getId();
		int updateScore = player.getScore()[type-1];
		
		try {
			Statement st = con.createStatement();
			System.out.println("추가될 점수 : "+updateScore);
			int rs = st.executeUpdate("update user set user_score_"+type+" = "+updateScore+" where user_id = '"+id+"'");
			if(rs == 0) { // 실패했을 때
				System.out.println("점수 업데이트 실패");
			}
			else {
				System.out.println("점수 업데이트 성공");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "점수 업데이트 쿼리 오류", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}
}
