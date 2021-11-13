package gui.handler;

import javax.swing.JPanel;

import gui.frame.GameFrame;
import gui.panel.LoginPanel;
import gui.panel.MenuPanel;
import gui.panel.minigame.FirstMinigamePanel;
import gui.panel.minigame.SecondMinigamePanel;
import gui.panel.minigame.ThirdMinigamePanel;

/**
 * GameFrame에 사용될 모든 Panel을 관리해주는 클래스.
 * PanelHandler는 GameFrame에 사용되는 모든 Panel을 가지고 있고, 이를 관리해주는 역할을 맡고 있다.
 */
public class PanelHandler {
	private JPanel[] panel = new JPanel[5];
	
	public PanelHandler(GameFrame gf) {
		this.panel[0] = new LoginPanel(gf);
		this.panel[1] = new MenuPanel(gf);
		this.panel[2] = new FirstMinigamePanel(gf);
		this.panel[3] = new SecondMinigamePanel(gf);
		this.panel[4] = new ThirdMinigamePanel(gf);
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
