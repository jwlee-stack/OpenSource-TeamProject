package data;

import java.util.Arrays;

/**
 * 플레이어의 상태를 저장해둔 클래스이다. 닉네임, 플레이어가 속한 방 이름, 각 미니게임의 점수, 현재 플레이중인 게임 종류, 어떠한
 * 게임에서 상대방을 찾는지에 대한 정보가 담겨있다.<br/>
 * <br/>
 * <strong>0: 메뉴화면(기본값), 1: 같은 그림 찾기, 2: 오목, 3: 두더지 잡기</strong>
 * 
 * @author Ted
 *
 */
public class Player {
	private String id = "";
	private String nickname = "";
	private String roomName = ""; // 방에 없으면 빈 문자열을 가짐. 방 이름은 랜덤 문자열은 10자리를 가짐
	private int[] score;
	private Boolean status; // 내 차례? 상대 차례?
	private int playGameNum; // 1: 같은 그림 찾기, 2: 오목, 3: 두더지 잡기, 0: 메뉴화면(기본값)
	private int searchingGameNum; // 1: 같은 그림 찾기, 2: 오목, 3: 두더지 잡기, 0: 메뉴화면(기본값)

	private int temp_score; // 현재 게임의 점수를 저장해둔 변수. Client의 gameover 프로토콜에서 결정됨
	private String temp_result; // 현재 게임의 승패를 저장해둔 변수. Client의 gameover 프로토콜에서 결정됨

	public Player() {
		this.status = null;
	}

	public Player(String id, String nickname, int[] score) {
		this.id = id;
		this.nickname = nickname;
		this.score = score;
		this.status = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int[] getScore() {
		return score;
	}

	public void setScore(int[] score) {
		this.score = score;
	}

	public int getPlayGameNum() {
		return playGameNum;
	}

	public void setPlayGameNum(int playGameNum) {
		this.playGameNum = playGameNum;
	}

	public int getSearchingGameNum() {
		return searchingGameNum;
	}

	public void setSearchingGameNum(int searchingGameNum) {
		this.searchingGameNum = searchingGameNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getTempScore() {
		return temp_score;
	}

	public void setTempScore(int temp_score) {
		this.temp_score = temp_score;
	}

	public String getTempResult() {
		return temp_result;
	}

	public void setTempResult(String temp_result) {
		this.temp_result = temp_result;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", nickname=" + nickname + ", score=" + Arrays.toString(score) + "]";
	}
}
