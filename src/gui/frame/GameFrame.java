package gui.frame;

import java.awt.CardLayout;

import javax.swing.JFrame;

import gui.handler.PanelHandler;

/**
 * 게임 창
 * @author phdljr
 *
 */
public class GameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private PanelHandler ph;
	private CardLayout cl;
	
	public GameFrame() {
		setMyFrame("꾸러미 동산", 800, 600);
	}
	
	/**
	 * 현재 Panel을 바꿔줌. login, menu, game1, game2, game3이 존재
	 * @param panel
	 */
	public void changePanel(String panel) {
		cl.show(this.getContentPane(), panel);
	}
	
	/**
	 * 기본 설정
	 * @param title
	 * @param width
	 * @param height
	 */
	private void setMyFrame(String title, int width, int height) {
		this.ph = new PanelHandler(this);
		this.cl = new CardLayout();
		
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(cl);
		setVisible(true);
		
		add(ph.getPanel(0), "login");
		add(ph.getPanel(1), "menu");
		add(ph.getPanel(2), "game1");
		add(ph.getPanel(3), "game2");
		add(ph.getPanel(4), "game3");
	}
}
