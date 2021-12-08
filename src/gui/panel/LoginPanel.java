package gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;
import gui.frame.GameFrame;

/**
 * 로그인 화면을 나타낼 Panel 카카오 API와 연결하여 간편 로그인 및 회원가입을 성공한다면 MenuPanel로 이동함
 * 
 * @author phdljr
 *
 */
public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton btnLogin;
	private JButton btnSignUp;

	public LoginPanel(GameFrame gf) {
		setBackground(SystemColor.activeCaption);
		setLayout(null);
		setSize(800, 600);

		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);

		JTextField idField = new JTextField();
		idField.setBounds(240, 212, 320, 30);
		add(idField);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(240, 260, 320, 30);
		add(passwordField);

		btnLogin = new JButton("로그인");
		btnLogin.setBounds(240, 350, 320, 40);
		btnLogin.addActionListener((e) -> {
			Client client = gf.getClient();
			boolean state = client.connectToServer("127.0.0.1", 9999); // 접속 성공 시, true값 반환

			if (state == true) {
				boolean isLogin = false;
				// TODO 카카오 API를 적용해, 닉네임을 넣어 줄 부분

//						////////////
//						login();
//						while(!isLogin) { //로그인을 할 때 까지 반복
//							sendQuery("select * from user where nickname = 123");
//						}
//						////////////

				client.getPlayer().setNickname("user" + new Random().nextInt(10000));
				client.sendMessageToServer("Login/" + client.getPlayer().getNickname());
				gf.changePanel("menu");
			} else {
				JOptionPane.showMessageDialog(null, "서버 접속에 실패했습니다.", "접속 실패", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btnLogin);

		btnSignUp = new JButton("회원 가입");
		btnSignUp.setBounds(240, 400, 320, 40);
		btnSignUp.addActionListener((e)->{
			gf.changePanel("signup");
		});
		add(btnSignUp);

	}
}
