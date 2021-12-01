package gui.panel;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import data.Player;

import gui.frame.GameFrame;

public class WaitRoomPanel extends JPanel{
	
	private String loadText = "상대방을 찾는 중";
	private GameFrame gf;
	private JLabel lbLoading;
	private boolean isSearch;
	private Player player;
	
	private Thread threadRotatingText;
	
	public WaitRoomPanel(GameFrame gf) {
		this.gf = gf;
		this.isSearch = true;
		this.player = gf.getClient().getPlayer();
		
		setMyPanel();
		loadText();
	}
	
	private void loadText() {
		threadRotatingText = new Thread(()->{
			int count = 0;
			while(isSearch) {
				try {
					Thread.sleep(500);
					if(++count > 2) {
						count = 0;
						lbLoading.setText(loadText);
					}
					lbLoading.setText(lbLoading.getText() + ".");
					
					if(player.getPlayGameNum() != 0) {
						isSearch = false;
						//gf.changePanel("test");
						gf.changePanel("game"+player.getPlayGameNum());
						System.out.println("game"+player.getPlayGameNum());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		threadRotatingText.start();
	}
	
	private void setMyPanel() {
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
		
		JButton btnExit = new JButton("뒤로가기");
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