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

import data.Player;
import gui.frame.GameFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;

import client.Client;

import javax.swing.JTable;

/**
 * 메뉴 Panel. 해당 화면에서 랭크 확인 및 게임 시작을 할 수 있음
 * @author phdljr
 *
 */
public class MenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	
	/**
	 *   panel
	 *   label : label[9] - 스코어 컨트롤, label[0] - 나의 등수
	 *   그외 - 등수(1등, 2등...)
	 *   text_Nickname : 랭킹 유저들의 닉네임([0] - 나의 닉네임)
	 *   text_Score : 랭킹 유저들의 점수([0] - 나의 점수)
	 *   btn : label[9]를 컨트롤할수 있는 버튼
	 * */
	JPanel[] panel = new JPanel[2];
	JLabel[] label= new JLabel[10];
	JTextField[] text_Nickname = new JTextField[10];
	JTextField[] text_Score = new JTextField[10];
	JButton[] btn = new JButton[4];
	String[] btntext= {"Score1", "Score2", "Score3", "Total"};

	public MenuPanel(GameFrame gf) {
		setBackground(SystemColor.info);
		setLayout(null);
		setSize(800,600);
		
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
		
		init();
		Score();
		
		
		
	}
	/**
	 *   점수판 관련 패널, 버튼, 레이블, 텍스트 초기화
	 * */
	public void init() {
		for(int i=0; i<=1; i++) {
			panel[i] = new JPanel();
			add(panel[i]);
			panel[i].setLayout(null);
		}
		for(int i=0;i<=3;i++) {
			btn[i] = new JButton(btntext[i]);
			btn[i].setVisible(true);
			panel[0].add(btn[i]);
		}
		for(int i=0; i<=9; i++) {
			label[i] = new JLabel();
			label[i].setVisible(true);
		}
		for(int i=0; i<=9; i++) {
			text_Nickname[i] = new JTextField();
			text_Score[i] = new JTextField();
			text_Nickname[i].setVisible(true);
			text_Score[i].setVisible(true);
		}
	}
	
	/**
	 *   점수판 컨트롤러(버튼을 누르면 해당 게임만의 랭킹스코어를 볼수있다)
	 * */
	public void Score(){
		panel[0].setBounds(410, 170, 370, 90);
		panel[0].add(label[9]);
		
		panel[1].setBounds(410,260,370,270);
		for(int i=0; i<=8; i++) {
			panel[1].add(label[i]);
			panel[1].add(text_Nickname[i]);
			panel[1].add(text_Score[i]);
		}
		

		label[9].setBounds(0, 0, 370, 50);
		label[9].setFont(new Font("굴림", Font.PLAIN, 24));
		label[9].setHorizontalAlignment(SwingConstants.CENTER);
		
		btn[0].setBounds(3, 53, 86, 33);
		btn[1].setBounds(93, 53, 90, 33);
		btn[2].setBounds(185, 53, 89, 33);
		btn[3].setBounds(277, 53, 90, 33);
		for(int i=0; i<=3; i++) {
			btn[i].setFont(new Font("Franklin Gothic Book", Font.PLAIN, 14));
			
		}
		btn[0].addActionListener((e)->{
			label[9].setText(btntext[0]);
		});
		btn[1].addActionListener((e)->{
			label[9].setText(btntext[1]);
		});
		btn[2].addActionListener((e)->{
			label[9].setText(btntext[2]);
		});
		btn[3].addActionListener((e)->{
			label[9].setText(btntext[3]);
		});
		
		
		/**
		 *   점수판(나와 상대유저들의 랭크 및 스코어)
		 *   label[0]은 따로처리
		 * */
		for(int i=0; i<=8; i++) {
			label[i].setBounds(0,30*i,110,30);
		}
		for(int i=0; i<=8; i++) {
			label[i].setFont(new Font("Dialog", Font.PLAIN, 16));
			label[i].setHorizontalAlignment(SwingConstants.CENTER);
			if(i!=0) {
			label[i].setText(Integer.toString(i)+"등");
			}
			else { 
				label[i].setText("내 점수");
			}
		}
		
		for(int i=0; i<=8; i++) {
			text_Nickname[i].setBounds(110, 30*i, 130, 30);
			text_Score[i].setBounds(240, 30*i, 130, 30);
			text_Nickname[i].setHorizontalAlignment(SwingConstants.CENTER);
			text_Score[i].setHorizontalAlignment(SwingConstants.CENTER);
			text_Nickname[i].setFont(new Font("Dialog", Font.PLAIN, 16));
			text_Score[i].setFont(new Font("Dialog", Font.PLAIN, 16));
			text_Nickname[i].setEditable(false);
			text_Score[i].setEditable(false);
		}
		
		
		
	}
	
	
}
