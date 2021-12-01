package gui.frame;

import java.awt.CardLayout;

import javax.swing.JFrame;

import client.Client;
import data.Player;
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
	
	private Client client;
	private Player player;
	
	public GameFrame() {
		this.client = new Client(); //서버 접속은 connectToserver()를 호출해야 접속함 
		this.player = client.getPlayer();
		
		setMyFrame("꾸러미 동산", 800, 600);
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
		add(ph.getPanel(2), "game");
	}
	
	/**
	 * 현재 Panel을 바꿔줌. login, menu, game1, game2, game3이 존재
	 * @param panel
	 */
	public void changePanel(String panel) {
		switch(panel) {
			case "game1":
				remove(ph.getPanel(2));
				ph.makeGamePanel(1);
				add(ph.getPanel(2));
				break;
			case "game2":
				remove(ph.getPanel(2));
				ph.makeGamePanel(2);
				add(ph.getPanel(2));
				break;
			case "game3":
				remove(ph.getPanel(2));
				ph.makeGamePanel(3);
				add(ph.getPanel(2));
				break;
			default:
				break;
		}
		cl.show(this.getContentPane(), panel);
	}
	
	public Client getClient() {
		return this.client;
	}
}
