package gui.panel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.frame.GameFrame;

/**
 * 로그인 화면을 나타낼 Panel
 * 카카오 API와 연결하여 간편 로그인 및 회원가입을 성공한다면 MenuPanel로 이동함
 * @author phdljr
 *
 */
public class LoginPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public LoginPanel(GameFrame gf) {
		setBackground(Color.CYAN);
		add(new JLabel("로그인 화면"));
		
		JButton btn = new JButton("간편 로그인");
		btn.addActionListener((e)->{
			gf.changePanel("menu");
		});
		add(btn);
	}
}
