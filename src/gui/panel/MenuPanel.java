package gui.panel;

import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import data.Player;
import gui.frame.GameFrame;

/**
 * 메뉴 Panel. 해당 화면에서 랭크 확인 및 게임 시작을 할 수 있음
 * @author phdljr
 *
 */
public class MenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public MenuPanel(GameFrame gf) {
		setBackground(SystemColor.info);
		setLayout(null);
		
		Player player = gf.getClient().getPlayer();
		
		
		ImageIcon icon = new ImageIcon("src\\data\\img\\sameimggame.PNG");
		Image img=icon.getImage();
		Image changeimg=img.getScaledInstance(137,137,Image.SCALE_SMOOTH);
		ImageIcon changeIcon=new ImageIcon(changeimg);
		JButton btnGame1 =new JButton(changeIcon);
		JLabel lbGame1=new JLabel("<같은 그림 찾기>");
		lbGame1.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		lbGame1.setBounds(151, 292, 200, 60);
		add(lbGame1);
		btnGame1.setBounds(141, 176, 137, 137);
		btnGame1.addActionListener((e)->{
			gf.getClient().getPlayer().setSearchingGameNum(1);
			gf.getClient().sendMessageToServer("JoinWaitRoom1/"+player.getNickname()); //클라이언트의 상태 설정 (c1=true, c2=false), playgamenum 1로 설정
			gf.changePanel("waitroom"); //waitroom 패널 실행
			//waitroompanel.java의 loadtext()계속 실행됨
			//(경로:gameframe.java의 changepanel->panelhandler.java의 makewaitroompanel->waitroom.java의 생성자->loadtext())
		});
		add(btnGame1);
		
		JButton btnGame2 = new JButton("미구현");
		JLabel lbGame2=new JLabel("<오목>");
		lbGame2.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		lbGame2.setBounds(102, 451, 200, 60);
		add(lbGame2);
		btnGame2.setBounds(59, 335, 137, 137);
		btnGame2.addActionListener((e)->{
			gf.getClient().getPlayer().setSearchingGameNum(2);
			gf.getClient().sendMessageToServer("JoinWaitRoom2/"+player.getNickname());
			gf.changePanel("waitroom");
		});
		add(btnGame2);
		
		JButton btnGame3 = new JButton("미구현");
		JLabel lbGame3=new JLabel("<두더지 잡기>");
		lbGame3.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		lbGame3.setBounds(251, 451, 200, 60);
		add(lbGame3);
		btnGame3.setBounds(227, 335, 137, 137);
		btnGame3.addActionListener((e)->{
			gf.getClient().getPlayer().setSearchingGameNum(3);
			gf.getClient().sendMessageToServer("JoinWaitRoom3/"+player.getNickname());
			gf.changePanel("waitroom");
		});
		add(btnGame3);
		
		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);
	}
}
