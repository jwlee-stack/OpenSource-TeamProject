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
import data.handler.Database;
import gui.frame.GameFrame;
import javax.swing.JPasswordField;
import javax.swing.JEditorPane;
import javax.swing.JTextField;


/**
 * 회원가입 화면
 * @author Ted
 *
 */
public class SignupPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JPasswordField PasswordField;
	private JTextField IDField;
	private JTextField NicknameField;
	private JPasswordField PasswordCheckField;
	
	private Database db = Database.getInstance();

	public SignupPanel(GameFrame gf) {
		setBackground(SystemColor.activeCaption);
		setLayout(null);
		setSize(800, 600);
		
		JLabel lbTitle = new JLabel("회원 가입");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);
		
		JLabel IDLabel = new JLabel("ID");
		IDLabel.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		IDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IDLabel.setBounds(150, 150, 70, 30);
		add(IDLabel);
		
		JLabel NicknameLabel_1 = new JLabel("닉네임");
		NicknameLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		NicknameLabel_1.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		NicknameLabel_1.setBounds(150, 200, 70, 30);
		add(NicknameLabel_1);
		
		JLabel PasswordLabel = new JLabel("비밀번호");
		PasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordLabel.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		PasswordLabel.setBounds(150, 250, 70, 30);
		add(PasswordLabel);
		
		JLabel PasswordCheckLabel = new JLabel("비밀번호 확인");
		PasswordCheckLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordCheckLabel.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		PasswordCheckLabel.setBounds(128, 300, 120, 30);
		add(PasswordCheckLabel);
		
		
		IDField = new JTextField();
		IDField.setBounds(300, 150, 260, 30);
		add(IDField);
		IDField.setColumns(10);
		
		JButton btnIDCheck = new JButton("ID 중복 체크");
		btnIDCheck.setBounds(570, 150, 150, 30);
		btnIDCheck.addActionListener((e)->{
			if(checkDupID()) {
				JOptionPane.showMessageDialog(getParent(), "사용할 수 있는 ID", "ID 중복 체크", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(getParent(), "사용할 수 없는 ID", "ID 중복 체크", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btnIDCheck);
		
		NicknameField = new JTextField();
		NicknameField.setColumns(10);
		NicknameField.setBounds(300, 200, 260, 30);
		add(NicknameField);
		
		JButton btnNickname = new JButton("닉네임 중복 체크");
		btnNickname.setBounds(570, 200, 150, 30);
		btnNickname.addActionListener((e)->{
			if(checkDupNickname()) {
				JOptionPane.showMessageDialog(getParent(), "사용할 수 있는 닉네임", "닉네임 중복 체크", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(getParent(), "사용할 수 없는 닉네임", "닉네임 중복 체크", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btnNickname);
		
		PasswordField = new JPasswordField();
		PasswordField.setBounds(300, 250, 260, 30);
		add(PasswordField);
		
		PasswordCheckField = new JPasswordField();
		PasswordCheckField.setBounds(300, 300, 260, 30);
		add(PasswordCheckField);
		
		JButton btnSignup = new JButton("회원가입 하기");
		btnSignup.setBounds(240, 400, 320, 80);
		add(btnSignup);
		btnSignup.addActionListener((e)->{
			//System.out.println(checkDupID());
			//System.out.println(checkEqualPW());
			if(checkDupID() && checkDupNickname() && checkEqualPW()) {
				//회원가입이 정상적으로 이루어진다면
				if(signup()) {
					JOptionPane.showMessageDialog(getParent(), "회원 가입이 정상적으로 등록되었습니다.", "회원 가입 성공", JOptionPane.INFORMATION_MESSAGE);
					gf.changePanel("login");
					clearText();
				}
			}
		});
	}
	
	/**
	 * nickname 중복과 길이를 체크한다. 중복이 아니라면 true를 반환한다.
	 * @return
	 */
	private boolean checkDupNickname() {
		String nickname = NicknameField.getText();
		if(nickname.length() < 3 || nickname.length() >= 10) {
			JOptionPane.showMessageDialog(getParent(), "닉네임의 길이는 3자 이상, 10자 미만으로만 설정할 수 있습니다.", "닉네임 길이 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		boolean result = db.isThereID(nickname);
		
		return !result;
	}
	
	/**
	 * id 중복과 최소 길이를 체크한다. 중복이 아니라면 true를 반환한다.
	 * @return
	 */
	private boolean checkDupID() {
		String id = IDField.getText();
		boolean result = db.isThereID(id);
		
		if(id.length() < 5 || id.length() >= 20) {
			JOptionPane.showMessageDialog(getParent(), "ID의 길이는 5자 이상, 20자 미만으로만 설정할 수 있습니다.", "ID 길이 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return !result;
	}
	
	/**
	 * 비밀번호를 똑같이 입력했는지 확인한다. 알맞게 입력했다면 true를 반환한다.
	 */
	private boolean checkEqualPW() {
		String pw = new String(PasswordField.getPassword());
		String pwc = new String(PasswordCheckField.getPassword());
		
		return pw.equals(pwc);
	}
	
	/**
	 * 입력한 아이디, 비밀번호, 닉네임을 기반으로 데이터 베이스에 값을 추가한다. 단, 밑의 조건을 만족해야만 한다.<br/><br/>
	 * - id 최소 5자 이상, 20자 미만<br/>
	 * - nickname 최소 3자 이상, 10자 미만<br/>
	 * - password 최소 8자 이상, 30자 미만<br/><br/>
	 * 위의 조건을 다 만족한다면 true를 반환한다.
	 */
	private boolean signup() {
		String id = IDField.getText();
		//id 최소 5자 이상, 20자 미만
		if(id.length() < 5 || id.length() >= 20) {
			JOptionPane.showMessageDialog(getParent(), "ID의 길이는 5자 이상, 20자 미만으로만 설정할 수 있습니다.", "ID 길이 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		String nickname = NicknameField.getText();
		//nickname 최소 3자 이상, 10자 미만
		if(nickname.length() < 3 || nickname.length() >= 10) {
			JOptionPane.showMessageDialog(getParent(), "닉네임의 길이는 3자 이상, 10자 미만으로만 설정할 수 있습니다.", "닉네임 길이 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		String pw = new String(PasswordField.getPassword());
		//password 최소 8자 이상, 30자 미만
		if(pw.length() < 8 || pw.length() >= 30) {
			JOptionPane.showMessageDialog(getParent(), "비밀번호의 길이는 8자 이상, 30자 미만으로만 설정할 수 있습니다.", "비밀번호 길이 오류", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		db.signup(id, nickname, pw);
		return true;
	}
	
	/**
	 * 텍스트 필드를 다 비운다.
	 * 화면이 이동되어도 입력칸에 적힌 데이터들은 그대로 존재하기 때문에 비워줘야 한다.
	 */
	private void clearText() {
		IDField.setText("");
		NicknameField.setText("");
		PasswordField.setText("");
		PasswordCheckField.setText("");
	}
}
