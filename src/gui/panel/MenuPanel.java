package gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import data.Player;
import data.handler.Database;
import data.handler.Database.PlayerScore;
import gui.frame.GameFrame;
import gui.component.RoundedButton;

/**
 * 메뉴 Panel. 해당 화면에서 랭크 확인 및 게임 시작을 할 수 있음
 * 
 * @author phdljr
 *
 */
public class MenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GameFrame gf;

	/**
	 * panel label : label[9] - 스코어 컨트롤, label[0] - 나의 등수 그외 - 등수(1등, 2등...)
	 * text_Nickname : 랭킹 유저들의 닉네임([0] - 나의 닉네임) text_Score : 랭킹 유저들의 점수([0] - 나의
	 * 점수) btn : label[9]를 컨트롤할수 있는 버튼
	 */
	private JPanel[] panel = new JPanel[10];
	private JTextField[] label = new JTextField[10];
	private JTextField[] text_Nickname = new JTextField[9];
	private JTextField[] text_Score = new JTextField[9];
	private RoundedButton[] btn = new RoundedButton[4];
	private JLabel control= new JLabel();
	private String[] btntext = { "같은 그림 찾기", "오목", "두더지 잡기", "총합" };
	
	private PlayerScore[] ps;

	public MenuPanel(GameFrame gf) {
		this.gf = gf;
		setBackground(SystemColor.info);
		setLayout(null);

		Player player = gf.getClient().getPlayer();

		URL url = getClass().getClassLoader().getResource("sameimggame.PNG");
		ImageIcon image = new ImageIcon(url);

		Image img = image.getImage();
		Image changeimg = img.getScaledInstance(137, 137, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeimg);
		JButton btnGame1 = new JButton(changeIcon);
		JLabel lbGame1 = new JLabel("<같은 그림 찾기>");
		lbGame1.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		lbGame1.setBounds(151, 292, 200, 60);
		add(lbGame1);
		btnGame1.setBounds(141, 176, 137, 137);
		btnGame1.addActionListener((e) -> {
			gf.getClient().getPlayer().setSearchingGameNum(1);
			gf.getClient().sendMessageToServer("JoinWaitRoom1/" + player.getNickname()); // 클라이언트의 상태 설정 (c1=true,
																							// c2=false), playgamenum 1로
																							// 설정
			gf.changePanel("waitroom"); // waitroom 패널 실행
										// waitroompanel.java의 loadtext()계속 실행됨
										// (경로:gameframe.java의 changepanel->panelhandler.java의
										// makewaitroompanel->waitroom.java의 생성자->loadtext())
		});
		add(btnGame1);

		
		URL url1 = getClass().getClassLoader().getResource("omok.PNG");
		ImageIcon image1= new ImageIcon(url1);

		Image img1 = image1.getImage();
		Image changeimg1 = img1.getScaledInstance(137, 137, Image.SCALE_SMOOTH);
		ImageIcon changeIcon1 = new ImageIcon(changeimg1);
		JButton btnGame2 = new JButton(changeIcon1);
		JLabel lbGame2 = new JLabel("<오목>");
		lbGame2.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		lbGame2.setBounds(102, 451, 200, 60);
		add(lbGame2);
		btnGame2.setBounds(59, 335, 137, 137);
		btnGame2.addActionListener((e) -> {
			gf.getClient().getPlayer().setSearchingGameNum(2);
			gf.getClient().sendMessageToServer("JoinWaitRoom2/" + player.getNickname());
			gf.changePanel("waitroom");
		});
		add(btnGame2);

		JButton btnGame3 = new JButton("미구현");
		JLabel lbGame3 = new JLabel("<두더지 잡기>");
		lbGame3.setFont(new Font("한컴 윤체 B", Font.PLAIN, 15));
		lbGame3.setBounds(251, 451, 200, 60);
		add(lbGame3);
		btnGame3.setBounds(227, 335, 137, 137);
		btnGame3.addActionListener((e) -> {
			JOptionPane.showMessageDialog(null, "추후 업데이트 될 예정입니다.", "기대해주세요!", JOptionPane.ERROR_MESSAGE);
		});
		add(btnGame3);

		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);

		init();
		score();
		updateRank(1);
	}
	
	/**
	 * 내 닉네임과 점수를 점수판에 출력시킨다.
	 * type:1,2,3 미니게임 / 4 총합
	 * @param type
	 */
	public void updateMyScore(int type) {
		Player player = gf.getClient().getPlayer();
		int score = 0;
		
		if(type == 4) {
			for(int i=0;i<3;i++) {
				score += player.getScore()[i];
			}
		}
		else {
			score = player.getScore()[type-1];
		}
		
		text_Nickname[0].setText(player.getNickname());
		text_Score[0].setText(Integer.toString(score));
	}

	/**
	 * 유저의 닉네임과 type유형에 해당하는 점수를 PlayerScore 클래스를 사용해 가져온다.
	 * PlayerScore 클래스는 nickname과 score를 담고 있다.
	 * type:1,2,3 미니게임 / 4 총합
	 * @param type
	 */
	public void updateUsersInfo(int type) {
		 Database db = Database.getInstance();
		 ps = db.getUsersScoreTop8(type);
	}
	
	/**
	 * 점수판을 업데이트 시킨다.
	 * type:1,2,3 미니게임 / 4 총합
	 * @param type
	 */
	public void updateRank(int type) {
		updateUsersInfo(type);
		for(int i=1;i<9;i++) {
			if(ps[i-1] == null) {
				break;
			}
			text_Nickname[i].setText(ps[i-1].nickname);
			text_Score[i].setText(Integer.toString(ps[i-1].score));
		}
	}
	
	private void init() {
		for (int i = 0; i < 10; i++) {
			panel[i] = new JPanel();
			add(panel[i]);
			panel[i].setLayout(null);
		}
		for (int i = 0; i < 4; i++) {
			btn[i] = new RoundedButton(btntext[i]);
			btn[i].setVisible(true);
			panel[9].add(btn[i]);
		}
		for (int i = 0; i < 10; i++) {
			label[i] = new JTextField();
			label[i].setEditable(false);
			label[i].setVisible(true);
		}
		for (int i = 0; i < 9; i++) {
			text_Nickname[i] = new JTextField();
			text_Score[i] = new JTextField();
			text_Nickname[i].setVisible(true);
			text_Score[i].setVisible(true);
			text_Nickname[i].setEditable(false);
			text_Score[i].setEditable(false);
		}
	}

	/**
	 * 점수판 컨트롤러(버튼을 누르면 해당 게임만의 랭킹스코어를 볼수있다)
	 */
	private void score() {
		panel[9].setBounds(410, 170, 370, 90);
		panel[9].add(control);
		panel[9].setBackground(new Color(255, 153, 80));
		
		for (int i = 0; i <= 8; i++) {
			panel[i].setBounds(410, 260 + 30*i, 370, 30);
			panel[i].add(label[i]);
			panel[i].add(text_Nickname[i]);
			panel[i].add(text_Score[i]);
		}

		control.setBounds(0, 0, 370, 50);
		control.setFont(new Font("굴림", Font.PLAIN, 24));
		control.setHorizontalAlignment(SwingConstants.CENTER);
		control.setText(btntext[0]);
		control.setBackground(new Color(255, 153, 80));
		
		btn[0].setBounds(3, 53, 86, 33);
		btn[1].setBounds(93, 53, 90, 33);
		btn[2].setBounds(185, 53, 89, 33);
		btn[3].setBounds(277, 53, 90, 33);
		for (int i = 0; i <= 3; i++) {
			btn[i].setFont(new Font("굴림", Font.PLAIN, 12));
		}
		btn[0].addActionListener((e) -> {
			control.setText(btntext[0]);
			updateMyScore(1);
			updateRank(1);
		});
		btn[1].addActionListener((e) -> {
			control.setText(btntext[1]);
			updateMyScore(2);
			updateRank(2);
		});
		btn[2].addActionListener((e) -> {
			control.setText(btntext[2]);
			updateMyScore(3);
			updateRank(3);
		});
		btn[3].addActionListener((e) -> {
			control.setText(btntext[3]);
			updateMyScore(4);
			updateRank(4);
		});

		/**
		 * 점수판(나와 상대유저들의 랭크 및 스코어) label[0]은 따로처리
		 */
		for (int i = 0; i < 9; i++) {
			if(i>=4) {
			label[i].setBounds(0, 0, 110, 30);
			}
			else {
				label[i].setBounds(1, 2, 109, 26);
			}
		}
		for (int i = 0; i < 9; i++) {
			label[i].setFont(new Font("Dialog", Font.PLAIN, 16));
			label[i].setHorizontalAlignment(SwingConstants.CENTER);
			if (i != 0) {
				label[i].setText(Integer.toString(i) + "등");
			} else {
				label[i].setText("내 점수");
			}
		}

		for (int i = 0; i < 9; i++) {
			if(i>=4) {
			text_Nickname[i].setBounds(110, 0, 130, 30);
			text_Score[i].setBounds(240, 0, 130, 30);
			}
			else {
				text_Nickname[i].setBounds(110, 2, 130, 26);
				text_Score[i].setBounds(240, 2, 129, 26);
			}
			text_Nickname[i].setHorizontalAlignment(SwingConstants.CENTER);
			text_Score[i].setHorizontalAlignment(SwingConstants.CENTER);
			text_Nickname[i].setFont(new Font("Dialog", Font.PLAIN, 16));
			text_Score[i].setFont(new Font("Dialog", Font.PLAIN, 16));
		}
		
		panel[1].setBackground(Color.YELLOW);
		panel[2].setBackground(Color.GRAY);
		panel[3].setBackground(new Color(200, 0, 0));
		
	}
}
