package data;

public class Player {
	private String uuid;
	private String nickname;
	private Friend[] friendList;
	private int[] score;
	private boolean isPlaying;
	private boolean isSearching;
	
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
	public boolean isPlaying() {
		return isPlaying;
	}
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	public boolean isSearching() {
		return isSearching;
	}
	public void setSearching(boolean isSearching) {
		this.isSearching = isSearching;
	}
}
