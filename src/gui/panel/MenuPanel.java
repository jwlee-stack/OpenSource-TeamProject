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
	private JTextField Score;
	private JTextField MyScore;
	private JTextField Rnak1_Score;
	private JTextField Rnak2_Score;
	private JTextField Rnak3_Score;
	private JTextField My_Nickname;
	private JTextField Rank1_Nickname;
	private JTextField Rank2_Nickname;
	private JTextField Rank3_Nickname;

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
		
		
		/**
		 *   나의 점수
		 */
		
		JPanel My_Info = new JPanel();
		My_Info.setBounds(410, 170, 370, 90);
		add(My_Info);
		My_Info.setLayout(null);
		
		JLabel Info_label = new JLabel("");
		Info_label.setFont(new Font("굴림", Font.PLAIN, 24));
		Info_label.setHorizontalAlignment(SwingConstants.CENTER);
		Info_label.setBounds(190, 0, 180, 90);
		My_Info.add(Info_label);
		
		JButton btnNewButton = new JButton("Score1");
		btnNewButton.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 14));
		btnNewButton.setBounds(10, 10, 80, 35);
		btnNewButton.addActionListener((e)->{
			Info_label.setText("Score1 :");
		});
		My_Info.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Score2");
		btnNewButton_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 14));
		btnNewButton_1.setBounds(100, 10, 80, 35);
		btnNewButton_1.addActionListener((e)->{
			Info_label.setText("Score2 :");
		});
		My_Info.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Score3");
		btnNewButton_2.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 14));
		btnNewButton_2.setBounds(10, 45, 80, 35);
		btnNewButton_2.addActionListener((e)->{
			Info_label.setText("Score3 :");
		});
		My_Info.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Total");
		btnNewButton_3.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 14));
		btnNewButton_3.setBounds(100, 45, 80, 35);
		btnNewButton_3.addActionListener((e)->{
			Info_label.setText("Total :");
		});
		My_Info.add(btnNewButton_3);
		
		
		
		/**
		 *   나의 랭크와 점수
		 */
		JPanel Panel1 = new JPanel();
		Panel1.setLayout(null);
		Panel1.setBounds(410, 260, 370, 70);
		add(Panel1);
		
		JLabel MyRank = new JLabel("");
		MyRank.setFont(new Font("Dialog", Font.PLAIN, 16));
		MyRank.setForeground(Color.BLACK);
		MyRank.setHorizontalAlignment(SwingConstants.CENTER);
		MyRank.setBounds(0, 0, 110, 70);
		Panel1.add(MyRank);
		
		My_Nickname = new JTextField();
		My_Nickname.setHorizontalAlignment(SwingConstants.CENTER);
		My_Nickname.setFont(new Font("Dialog", Font.PLAIN, 16));
		My_Nickname.setEditable(false);
		My_Nickname.setBounds(110, 0, 130, 70);
		Panel1.add(My_Nickname);
		My_Nickname.setColumns(10);
		
		MyScore = new JTextField();
		MyScore.setFont(new Font("Dialog", Font.PLAIN, 16));
		MyScore.setHorizontalAlignment(SwingConstants.CENTER);
		MyScore.setEditable(false);
		MyScore.setBounds(240, 0, 130, 70);
		Panel1.add(MyScore);
		MyScore.setColumns(10);
		
		/**
		 *   1,2,3등의 점수
		 */
		
		JPanel Panel2 = new JPanel();
		Panel2.setLayout(null);
		Panel2.setBounds(410, 330, 370, 70);
		add(Panel2);
		
		JLabel Rank1 = new JLabel("1등");
		Rank1.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rank1.setHorizontalAlignment(SwingConstants.CENTER);
		Rank1.setBounds(0, 0, 110, 70);
		Panel2.add(Rank1);
		
		Rank1_Nickname = new JTextField();
		Rank1_Nickname.setHorizontalAlignment(SwingConstants.CENTER);
		Rank1_Nickname.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rank1_Nickname.setEditable(false);
		Rank1_Nickname.setColumns(10);
		Rank1_Nickname.setBounds(110, 0, 130, 70);
		Panel2.add(Rank1_Nickname);
		
		Rnak1_Score = new JTextField();
		Rnak1_Score.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rnak1_Score.setHorizontalAlignment(SwingConstants.CENTER);
		Rnak1_Score.setEditable(false);
		Rnak1_Score.setColumns(10);
		Rnak1_Score.setBounds(240, 0, 130, 70);
		Panel2.add(Rnak1_Score);
		
		
		JPanel Panel3 = new JPanel();
		Panel3.setLayout(null);
		Panel3.setBounds(410, 400, 370, 70);
		add(Panel3);
		
		JLabel Rank2 = new JLabel("2등");
		Rank2.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rank2.setHorizontalAlignment(SwingConstants.CENTER);
		Rank2.setBounds(0, 0, 110, 70);
		Panel3.add(Rank2);
		
		Rank2_Nickname = new JTextField();
		Rank2_Nickname.setHorizontalAlignment(SwingConstants.CENTER);
		Rank2_Nickname.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rank2_Nickname.setEditable(false);
		Rank2_Nickname.setColumns(10);
		Rank2_Nickname.setBounds(110, 0, 130, 70);
		Panel3.add(Rank2_Nickname);
		
		Rnak2_Score = new JTextField();
		Rnak2_Score.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rnak2_Score.setHorizontalAlignment(SwingConstants.CENTER);
		Rnak2_Score.setEditable(false);
		Rnak2_Score.setColumns(10);
		Rnak2_Score.setBounds(240, 0, 130, 70);
		Panel3.add(Rnak2_Score);
		
		
		JPanel Panel4 = new JPanel();
		Panel4.setLayout(null);
		Panel4.setBounds(410, 470, 370, 70);
		add(Panel4);
		
		JLabel Rank3 = new JLabel("3등");
		Rank3.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rank3.setHorizontalAlignment(SwingConstants.CENTER);
		Rank3.setBounds(0, 0, 110, 70);
		Panel4.add(Rank3);
		
		Rank3_Nickname = new JTextField();
		Rank3_Nickname.setHorizontalAlignment(SwingConstants.CENTER);
		Rank3_Nickname.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rank3_Nickname.setEditable(false);
		Rank3_Nickname.setColumns(10);
		Rank3_Nickname.setBounds(110, 0, 130, 70);
		Panel4.add(Rank3_Nickname);
		
		Rnak3_Score = new JTextField();
		Rnak3_Score.setFont(new Font("Dialog", Font.PLAIN, 16));
		Rnak3_Score.setHorizontalAlignment(SwingConstants.CENTER);
		Rnak3_Score.setEditable(false);
		Rnak3_Score.setColumns(10);
		Rnak3_Score.setBounds(240, 0, 130, 70);
		Panel4.add(Rnak3_Score);
		
	}
}
