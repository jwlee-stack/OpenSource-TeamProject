package gui.frame;

import java.awt.CardLayout;

import javax.swing.JFrame;

import client.Client;
import data.Player;
import gui.handler.PanelHandler;

/**
 * 게임 창 클래스이다. 해당 클래스를 통해 사용자의 화면(패널)을 전환시킬 수 있다.
 * 그리고, 플레이어의 Client 객체 또한 가지고 있다. 모든 패널에 존재하는 Client 래퍼런스는
 * 해당 클래스에 존재하는 Client 객체를 가리키고 있다.
 * 
 * @author phdljr
 *
 */
public class GameFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private PanelHandler ph;
	private CardLayout cl;
	
	private Client client;
	
	public GameFrame() {
		this.client = new Client(); //서버 접속은 connectToserver()를 호출해야 접속함
		this.ph = new PanelHandler(this);
		this.cl = new CardLayout();
		
		initGUI("꾸러미 동산", 800, 600);
	}
	
	/**
	 * 기본 설정
	 * @param title
	 * @param width
	 * @param height
	 */
	private void initGUI(String title, int width, int height) {
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
	}
	
	/**
	 * 현재 Panel을 바꿔줌. login, menu, game1, game2, game3, waitroom이 존재
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
			default:
				break;
		}
		cl.show(this.getContentPane(), panel);
	}
	
	/**
	 * 플레이어의 Client를 반환
	 * @return
	 */
	public Client getClient() {
		return this.client;
	}
}
