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
		this.client.setgameframe(this);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(cl);
		setVisible(true);
		
		add(ph.getPanel(0), "login");
		add(ph.getPanel(1), "menu");
		add(ph.getPanel(2), "game");
		add(ph.getPanel(3), "waitroom");
//		add("login", ph.getPanel(0));
//		add("menu", ph.getPanel(1));
//		add("game", ph.getPanel(2));
//		add("waitroom", ph.getPanel(3));
		//add(ph.getPanel(4), "test");
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
				add(ph.getPanel(2), "game1");
				break;
			case "game2":
				remove(ph.getPanel(2));
				ph.makeGamePanel(2);
				add(ph.getPanel(2), "game2");
				break;
			case "game3":
				remove(ph.getPanel(2));
				ph.makeGamePanel(3);
				add(ph.getPanel(2), "game3");
				break;
			case "waitroom":
				remove(ph.getPanel(3));
				ph.makeWaitRoomPanel();
				add(ph.getPanel(3), "waitroom");
				break;
//			case "waitroom":
//				remove(ph.getPanel(3));
//				ph.makeGamePanel(3);
//				add(ph.getPanel(2));
//				break;
			default:
				break;
		}
		cl.show(this.getContentPane(), panel);
	}
	
	public Client getClient() {
		return this.client;
	}
}
