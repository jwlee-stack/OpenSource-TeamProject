package data;

public class Player {
	private String uuid;
	private String nickname = "";
	private String roomName = ""; //방에 없으면 빈 문자열을 가짐. 방 이름은 랜덤 문자열은 10자리를 가짐
	private Friend[] friendList;
	private int[] score;
	private boolean status=false; //내 차례? 상대 차례?
	private int playGameNum; //1: 같은 그림 찾기, 2: 오목, 3: 두더지 잡기, 0: 메뉴화면(기본값)
	private int searchingGameNum; //1: 같은 그림 찾기, 2: 오목, 3: 두더지 잡기, 0: 메뉴화면(기본값)
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Friend[] getFriendList() {
		return friendList;
	}
	public void setFriendList(Friend[] friendList) {
		this.friendList = friendList;
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
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
