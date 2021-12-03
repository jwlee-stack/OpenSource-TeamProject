package gui.panel.minigame;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.frame.GameFrame;

public class FirstMinigamePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public FirstMinigamePanel(GameFrame gf) {
		setSize(800, 600);
		add(new JLabel("미니게임1 - 같은 그림 찾기"));
	}
}
