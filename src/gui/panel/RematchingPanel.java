package gui.panel;

import java.awt.EventQueue;

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
 * 게임이 끝나고 보여질 화면
 * @author Ted
 *
 */
public class RematchingPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public RematchingPanel(GameFrame gf) {
		setBackground(SystemColor.info);
		setLayout(null);
		
		Player player = gf.getClient().getPlayer();
		
		JLabel finishlabel = new JLabel("게임종료");
		finishlabel.setBounds(325, 80, 150, 80);
		finishlabel.setHorizontalAlignment(SwingConstants.CENTER);
		finishlabel.setFont(new Font("굴림", Font.PLAIN, 28));
		
		JButton btn_rematch = new JButton("재매칭");
		btn_rematch.setBounds(200, 400, 100, 100);
		btn_rematch.addActionListener((e)->{
			
		});
		add(btn_rematch);
		
		JButton btn_exit = new JButton("나가기");
		btn_exit.setBounds(500, 400, 100, 100);
		btn_exit.addActionListener((e)->{
			
		});
		add(btn_exit);
		
		JLabel label = new JLabel("승패");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(300, 170, 200, 80);
		
		JLabel score_label = new JLabel("점수");
		score_label.setHorizontalAlignment(SwingConstants.CENTER);
		score_label.setBounds(300, 270, 200, 80);
		
	}
	
	
	

}