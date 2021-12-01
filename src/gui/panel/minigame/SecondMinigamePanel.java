package gui.panel.minigame;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.frame.GameFrame;

public class SecondMinigamePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public SecondMinigamePanel(GameFrame gf) {
		add(new JLabel("미니게임2 - 오목"));
	}
}
