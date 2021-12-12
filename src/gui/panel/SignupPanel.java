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
				signup();
				JOptionPane.showMessageDialog(getParent(), "회원 가입이 정상적으로 등록되었습니다.", "회원 가입 성공", JOptionPane.INFORMATION_MESSAGE);
				gf.changePanel("login");
				clearText();
			}
		});
		
		JButton btnExit = new JButton("뒤로가기");
		btnExit.setBounds(355, 535, 97, 23);
		btnExit.addActionListener((e)->{
			gf.changePanel("login");
		});
		add(btnExit);
		
	}
	
	
	/**
	 * nickname 중복을 체크한다. 중복이 아니라면 true를 반환한다.
	 * @return
	 */
	private boolean checkDupNickname() {
		String nickname = NicknameField.getText();
		boolean result = db.isThereID(nickname);
		
		return !result;
	}
	
	/**
	 * id 중복을 체크한다. 중복이 아니라면 true를 반환한다.
	 * @return
	 */
	private boolean checkDupID() {
		String id = IDField.getText();
		boolean result = db.isThereID(id);
		
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
	 * 입력한 아이디, 비밀번호, 닉네임을 기반으로 데이터 베이스에 값을 추가한다.
	 */
	private void signup() {
		String id = IDField.getText();
		String nickname = NicknameField.getText();
		String pw = new String(PasswordField.getPassword());
		
		db.signup(id, nickname, pw);
	}
	
	/**
	 * 텍스트 필드를 다 비운다.
	 */
	private void clearText() {
		IDField.setText("");
		NicknameField.setText("");
		PasswordField.setText("");
		PasswordCheckField.setText("");
	}
}