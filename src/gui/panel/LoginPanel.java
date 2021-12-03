package gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import client.Client;
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
		setBackground(SystemColor.activeCaption);
		setLayout(null);
		setSize(800, 600);
		
		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);
		
		JButton btnKakaoLogin = new JButton("카카오 간편 로그인");
		btnKakaoLogin.setBounds(238, 291, 332, 106);
		add(btnKakaoLogin);
		
		//버튼 클릭 시, 실행 될 코드
		btnKakaoLogin.addActionListener((e)->{
			Client client = gf.getClient();
			boolean state = client.connectToServer("127.0.0.1", 9999); //접속 성공 시, true값 반환
			
			if(state == true) {
				//TODO 카카오 API를 적용해, 닉네임을 넣어 줄 부분
				client.getPlayer().setNickname("user"+new Random().nextInt(10000));
				client.sendMessageToServer("Login/" + client.getPlayer().getNickname());
				gf.changePanel("menu");
			}
			else {
				JOptionPane.showMessageDialog(null, "서버 접속에 실패했습니다.", "접속 실패", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btnKakaoLogin);
	}
}
