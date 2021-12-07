package data;

public class KakaoAPI {
	
	private KakaoAPI kakao;
	
	public KakaoAPI getInstance() {
		if(kakao == null) {
			kakao = new KakaoAPI();
		}
		return kakao;
	}
	

}
