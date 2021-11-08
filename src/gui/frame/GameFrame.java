package gui.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.handler.PanelHandler;

public class GameFrame extends JFrame{
	private PanelHandler ph;
	private JPanel currentPanel;
	
	public GameFrame() {
		this.ph = new PanelHandler();
	}
	
	public void changePanel(int index) {
		setContentPane(ph.getPanel(index));
	}
}
