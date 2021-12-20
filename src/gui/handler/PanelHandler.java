package gui.handler;

import javax.swing.JPanel;

import data.Player;
import gui.frame.GameFrame;
import gui.panel.LoginPanel;
import gui.panel.MenuPanel;
import gui.panel.RematchingPanel;
import gui.panel.SignupPanel;
import gui.panel.WaitRoomPanel;
import gui.panel.minigame.FirstMinigamePanel;
import gui.panel.minigame.SecondMinigamePanel;
import gui.panel.minigame.ThirdMinigamePanel;

/**
 * GameFrame에 사용될 모든 Panel을 관리해주는 클래스.
 * PanelHandler는 GameFrame에 사용되는 모든 Panel을 가지고 있고, 이를 관리해주는 역할을 맡고 있다.
 */
public class PanelHandler {
	private JPanel[] panel = new JPanel[6]; //모든 패널을 담고 있는 배열
	private GameFrame gf;
	
	public PanelHandler(GameFrame gf) {
		this.gf = gf;
		this.panel[0] = new LoginPanel(gf);
		this.panel[1] = new MenuPanel(gf);
		this.panel[2] = new JPanel(); //dummy panel, 미니게임 패널이 들어갈 공간
		this.panel[3] = new JPanel(); //dummy panel, 웨이팅 패널이 들어갈 공간
		this.panel[4] = new SignupPanel(gf);
		this.panel[5] = new RematchingPanel(gf);
	}
	
	/**
	 * 미니게임 패널을 새로 만드는 메소드이다. 미니게임 패널은 재활용이 불가능하기 때문에,
	 * 게임이 끝나고 나면 새로 만들어 줘야한다.
	 * 
	 * @param type
	 */
	public void makeGamePanel(int type) {
		switch(type) {
		case 1: 
			panel[2] = new FirstMinigamePanel(gf);
			break;
		case 2: 
			panel[2] = new SecondMinigamePanel(gf);
			break;
		case 3: 
			panel[2] = new ThirdMinigamePanel(gf);
			break;
		default:
			System.out.println("존재하지 않는 미니게임 패널");
		}
	}
	
	/**
	 * 웨이팅 룸 패널을 새로 만드는 메소드이다. 웨이팅 룸 패널은 재활용이 불가능하기 때문에,
	 * 필요할 때 새로 만들어 줘야한다.
	 */
	public void makeWaitRoomPanel() {
		System.out.println("웨이팅 룸 패널 생성");
		this.panel[3] = new WaitRoomPanel(gf);
	}
	
	/**
	 * 리매칭 패널에 사용자의 승패와 점수를 나타내어 주는 메소드이다.
	 * 화면이 바뀔때마다 호출되도록 설계돼었다.
	 */
	public void updateRematchingPanel() {
		System.out.println("리메치 패널 생성");
		Player player = gf.getClient().getPlayer();
		((RematchingPanel)(panel[5])).setLabelInfo(player.getTempScore(), player.getTempResult());
	}
	
	/**
	 * 현재 가지고 있는 Panel의 수를 반환한다.
	 * @return int: 현재 보유한 Panel의 수
	 */
	public int getSize() {
		return panel.length;
	}
	
	/**
	 * index에 해당되는 Panel을 반환한다.<br/><br/>
	 * <strong>0:로그인 패널, 1: 메뉴 패널, 2: 미니게임 패널, 3: 웨이팅 룸 패널, 4: 회원 가입 패널, 5: 리매칭 패널</strong>
	 * @param index
	 * @return Panel
	 */
	public JPanel getPanel(int index) {
		return this.panel[index];
	}
}
