package data;

/**
 * 오목 게임에서 쓰이는 예외 클래스. 이미 돌이 올려진 곳에 돌을 놓을려고 시도할 시 발생
 * @author phdljr
 *
 */
public class AlreadyExist extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExist() {
		super("이미 돌이 존재합니다.");
	}
}