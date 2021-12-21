package gui.panel;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import data.Player;
import gui.component.RoundedButton;
import gui.frame.GameFrame;

/**
 * 상대방을 찾는 중일 때 보여줄 패널. 상대방을 찾게 되면 미니게임 화면으로 이동하게 된다.
 * @author Ted
 *
 */
public class WaitRoomPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private String loadText = "상대방을 찾는 중";
	private GameFrame gf;
	private JLabel lbLoading;
	private boolean isSearch; //상대방을 찾는 중이라면 true
	private Player player;
	
	private Thread threadRotatingText; 
	
	public WaitRoomPanel(GameFrame gf) {
		this.gf = gf;
		this.isSearch = true;
		this.player = gf.getClient().getPlayer();
		
		initGUI();
		loadText();
	}
	
	/**
	 * 상대방을 찾을때까지 글자를 변환시켜주도록 하는 메소드.
	 * 플레이어의 playGameNum이 바뀌게 되면(=상대방을 찾은 경우)
	 * isSearch가 false로 바뀌며 스레드는 종료하게 된다.
	 */
	private void loadText() {
		threadRotatingText = new Thread(()->{
			int count = 0;
			while(isSearch) {
				//System.out.println("웨이팅룸");
				try {
					Thread.sleep(500);
					if(++count > 2) {
						count = 0;
						lbLoading.setText(loadText);
					}
					lbLoading.setText(lbLoading.getText() + ".");
					
					if(player.getPlayGameNum() != 0) { //플레이어가 게임을 가짐(서버의 joinwaitroom 프로토콜에서 waitroom.size()==2일 때, player.playgamenum 설정)
						isSearch = false;
						player.setSearchingGameNum(0);
						gf.changePanel("game"+player.getPlayGameNum()); //게임 화면으로 이동 (minigamepanel의 생성자 실행)
						System.out.println("game"+player.getPlayGameNum());
					}
				} catch (InterruptedException e) { // 예외 발생 시, 메뉴 화면으로 이동
					isSearch = false;
					gf.getClient().sendMessageToServer("ExitWaitRoom"+player.getSearchingGameNum()+"/ ");
					player.setSearchingGameNum(0);
					gf.changePanel("menu");
					JOptionPane.showMessageDialog(null, "대기 화면 로딩 오류", "오류", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		});
		threadRotatingText.start();
	}
	
	private void initGUI() {
		setBackground(SystemColor.info);
		setLayout(null);
		setSize(800, 600);
		
		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);
		
		lbLoading = new JLabel(loadText);
		lbLoading.setHorizontalAlignment(SwingConstants.CENTER);
		lbLoading.setFont(new Font("한컴 윤체 L", Font.PLAIN, 22));
		lbLoading.setBounds(157, 286, 485, 60);
		add(lbLoading);
		
		JButton btnExit = new RoundedButton("뒤로가기");
		btnExit.setBounds(355, 535, 97, 23);
		btnExit.addActionListener((e)->{
			gf.getClient().sendMessageToServer("ExitWaitRoom"+player.getSearchingGameNum()+"/ ");
			player.setSearchingGameNum(0);
			gf.changePanel("menu");
			isSearch = false;
		});
		add(btnExit);
	}
}