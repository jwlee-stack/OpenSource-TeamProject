package gui.handler;

import javax.swing.JPanel;

import gui.panel.LoginPanel;
import gui.panel.MenuPanel;
import gui.panel.minigame.FirstMinigamePanel;
import gui.panel.minigame.SecondMinigamePanel;
import gui.panel.minigame.ThirdMinigamePanel;

public class PanelHandler {
	private JPanel[] panel = new JPanel[5];
	
	public PanelHandler() {
		this.panel[0] = new LoginPanel();
		this.panel[1] = new MenuPanel();
		this.panel[2] = new FirstMinigamePanel();
		this.panel[3] = new SecondMinigamePanel();
		this.panel[4] = new ThirdMinigamePanel();
	}
	
	public JPanel getPanel(int index) {
		return this.panel[index];
	}
}
