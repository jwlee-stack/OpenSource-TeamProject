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
import data.Player;
import data.handler.Database;
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
	
	private GameFrame gf;

	public LoginPanel(GameFrame gf) {
		this.gf = gf;
		
		setBackground(SystemColor.activeCaption);
		setLayout(null);
		setSize(800, 600);

		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);
		
		JLabel lbID = new JLabel("ID");
		lbID.setFont(new Font("한컴 윤체 B", Font.PLAIN, 30));
		lbID.setBounds(170, 212, 50, 30);
		add(lbID);
		
		JTextField idField = new JTextField();
		idField.setBounds(240, 212, 320, 30);
		add(idField);

		JLabel lbPW = new JLabel("PW");
		lbPW.setFont(new Font("한컴 윤체 B", Font.PLAIN, 30));
		lbPW.setBounds(170, 260, 50, 30);
		add(lbPW);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(240, 260, 320, 30);
		add(passwordField);

		btnLogin = new JButton("로그인");
		btnLogin.setBounds(240, 350, 320, 40);
		btnLogin.addActionListener((e) -> {
			String id = idField.getText();
			String pw = new String(passwordField.getPassword());
			
			//로그인 성공 시, 서버에 연결한 후 메뉴로 이동한다.
			if(login(id, pw)) {
				connectToServer();
				gf.changePanel("menu");
				//TODO 테스트
				System.out.println(gf.getClient().getPlayer());
			}
			else {
				JOptionPane.showMessageDialog(getParent(), "사용자 정보가 일치하지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
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
	
	/**
	 * 로그인을 시도한다. 성공하면 true를 반환한다.
	 * @return
	 */
	private boolean login(String id, String pw) {
		boolean result = Database.getInstance().login(id, pw);
		
		if(result == true){
			setPlayerInfo(id, pw);
		}
		
		return result;
	}

	/**
	 * DB에서 유저의 정보를 가져와서 설정해주는 메소드이다.<br/>
	 * 매개변수로 입력받은 id와 password를 기반으로 데이터베이스에서 데이터를 가져온다.<br/>
	 * 가져올 데이터는 id, nickname, score[]이다.
	 */
	private void setPlayerInfo(String id, String pw) {
		Player player = gf.getClient().getPlayer();
		Player db_player = Database.getInstance().getPlayerInfo(id, pw);
		
		player.setId(db_player.getId());
		player.setNickname(db_player.getNickname());
		player.setScore(db_player.getScore());
	}
	
	/**
	 * 서버에 연결한다.
	 * @param gf
	 */
	private void connectToServer() {
		Client client = gf.getClient();
		boolean state = client.connectToServer("127.0.0.1", 9999); // 접속 성공 시, true값 반환

		if (state == true) {
			client.getPlayer().setNickname(client.getPlayer().getNickname());
			client.sendMessageToServer("Login/" + client.getPlayer().getNickname());
		} else {
			JOptionPane.showMessageDialog(null, "서버 접속에 실패했습니다.", "접속 실패", JOptionPane.ERROR_MESSAGE);
		}
	}
}
