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
import javax.swing.JPasswordField;
import javax.swing.JEditorPane;
import javax.swing.JTextField;


public class SignupPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JPasswordField PasswordField;
	private JTextField IDField;
	private JTextField NicknameField;
	private JPasswordField PasswordCheckField;

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
		
		NicknameField = new JTextField();
		NicknameField.setColumns(10);
		NicknameField.setBounds(300, 200, 260, 30);
		add(NicknameField);
		
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
			
		});
		
		
		
		
		
		
		
	}
}
