package gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

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
		
		JButton btnGame1 = new JButton("같은 그림 찾기");
		btnGame1.setBounds(141, 176, 137, 137);
		btnGame1.addActionListener((e)->{
			gf.getClient().getPlayer().setSearchingGameNum(1);
			gf.getClient().sendMessageToServer("JoinWaitRoom1/"+player.getNickname());
			gf.changePanel("waitroom");
		});
		add(btnGame1);
		
		JButton btnGame2 = new JButton("오목");
		btnGame2.setBounds(59, 335, 137, 137);
		btnGame2.addActionListener((e)->{
			gf.getClient().getPlayer().setSearchingGameNum(2);
			gf.getClient().sendMessageToServer("JoinWaitRoom2/"+player.getNickname());
			gf.changePanel("waitroom");
		});
		add(btnGame2);
		
		JButton btnGame3 = new JButton("두더지잡기");
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
