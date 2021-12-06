package gui.handler;

import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Client;
import gui.frame.GameFrame;
import gui.panel.LoginPanel;
import gui.panel.MenuPanel;
import gui.panel.WaitRoomPanel;
import gui.panel.minigame.FirstMinigamePanel;
import gui.panel.minigame.SecondMinigamePanel;
import gui.panel.minigame.ThirdMinigamePanel;

/**
 * GameFrame에 사용될 모든 Panel을 관리해주는 클래스.
 * PanelHandler는 GameFrame에 사용되는 모든 Panel을 가지고 있고, 이를 관리해주는 역할을 맡고 있다.
 */

public class PanelHandler {
	private JPanel[] panel = new JPanel[5];
	private GameFrame gf;
	private Client client1;
	public PanelHandler(GameFrame gf) {
		this.gf = gf;
		this.panel[0] = new LoginPanel(gf);
		this.panel[1] = new MenuPanel(gf);
		this.panel[2] = new JPanel(); //dummy panel, 미니게임 패널이 들어갈 공간
		this.panel[3] = new JPanel(); //dummy panel, 웨이팅 패널이 들어갈 공간
//		this.panel[4] = new JPanel(); //테스트
//		this.panel[4].add(new JLabel("테스트"));//테스트
		this.client1=gf.getClient();
	}
	
	public void makeGamePanel(int type) {
		switch(type) {
		case 1: 
			panel[2] = new FirstMinigamePanel(gf);
			((FirstMinigamePanel)panel[2]).getClient().setPanel((FirstMinigamePanel)panel[2]);
			System.out.println("패널 생성됨");
			((FirstMinigamePanel) panel[2]).addLayout();
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

	public void makeWaitRoomPanel() {
		System.out.println("웨이팅 룸 패널 생성");
		this.panel[3] = new WaitRoomPanel(gf);
	}
	
	/**
	 * 현재 가지고 있는 Panel의 수를 반환한다.
	 * @return int: 현재 보유한 Panel의 수
	 */
	public int getSize() {
		return panel.length;
	}
	
	/**
	 * index에 해당되는 Panel을 반환한다.
	 * @param index
	 * @return Panel
	 */
	public JPanel getPanel(int index) {
		return this.panel[index];
	}
}
