package gui.panel;

import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import data.Player;
import data.handler.Database;
import data.handler.Database.PlayerScore;
import gui.frame.GameFrame;

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
	private JPanel[] panel = new JPanel[2];
	private JLabel[] label = new JLabel[10];
	private JTextField[] text_Nickname = new JTextField[9];
	private JTextField[] text_Score = new JTextField[9];
	private JButton[] btn = new JButton[4];
	private String[] btntext = { "Score1", "Score2", "Score3", "총합" };
	
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
			player.setSearchingGameNum(1);
			gf.getClient().sendMessageToServer("JoinWaitRoom1/" + player.getNickname()); // 클라이언트의 상태 설정 (c1=true,
																							// c2=false), playgamenum 1로
																							// 설정
			gf.changePanel("waitroom"); // waitroom 패널 실행
										// waitroompanel.java의 loadtext()계속 실행됨
										// (경로:gameframe.java의 changepanel->panelhandler.java의
										// makewaitroompanel->waitroom.java의 생성자->loadtext())
		});
		add(btnGame1);

		JButton btnGame2 = new JButton("미구현");
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
			gf.getClient().getPlayer().setSearchingGameNum(3);
			gf.getClient().sendMessageToServer("JoinWaitRoom3/" + player.getNickname());
			gf.changePanel("waitroom");
		});
		add(btnGame3);

		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);

		init();
		score();
		updateRank(1); // 메뉴 화면에 들어오면 맨 처음에 보여질 점수판
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
	
	/**
	 * 점수판 관련 패널, 버튼, 레이블, 텍스트 초기화
	 */
	private void init() {
		for (int i = 0; i < 2; i++) {
			panel[i] = new JPanel();
			add(panel[i]);
			panel[i].setLayout(null);
		}
		for (int i = 0; i < 4; i++) {
			btn[i] = new JButton(btntext[i]);
			btn[i].setVisible(true);
			panel[0].add(btn[i]);
		}
		for (int i = 0; i < 10; i++) {
			label[i] = new JLabel();
			label[i].setVisible(true);
		}
		for (int i = 0; i < 9; i++) {
			text_Nickname[i] = new JTextField();
			text_Score[i] = new JTextField();
			text_Nickname[i].setVisible(true);
			text_Score[i].setVisible(true);
		}
	}

	/**
	 * 점수판 컨트롤러(버튼을 누르면 해당 게임만의 랭킹스코어를 볼수있다)
	 */
	private void score() {
		panel[0].setBounds(410, 170, 370, 90);
		panel[0].add(label[9]);

		panel[1].setBounds(410, 260, 370, 270);
		for (int i = 0; i <= 8; i++) {
			panel[1].add(label[i]);
			panel[1].add(text_Nickname[i]);
			panel[1].add(text_Score[i]);
		}

		label[9].setBounds(0, 0, 370, 50);
		label[9].setFont(new Font("굴림", Font.PLAIN, 24));
		label[9].setHorizontalAlignment(SwingConstants.CENTER);
		label[9].setText(btntext[0]);

		btn[0].setBounds(3, 53, 86, 33);
		btn[1].setBounds(93, 53, 90, 33);
		btn[2].setBounds(185, 53, 89, 33);
		btn[3].setBounds(277, 53, 90, 33);
		for (int i = 0; i <= 3; i++) {
			btn[i].setFont(new Font("굴림", Font.PLAIN, 14));
		}
		btn[0].addActionListener((e) -> {
			label[9].setText(btntext[0]);
			updateMyScore(1);
			updateRank(1);
		});
		btn[1].addActionListener((e) -> {
			label[9].setText(btntext[1]);
			updateMyScore(2);
			updateRank(2);
		});
		btn[2].addActionListener((e) -> {
			label[9].setText(btntext[2]);
			updateMyScore(3);
			updateRank(3);
		});
		btn[3].addActionListener((e) -> {
			label[9].setText(btntext[3]);
			updateMyScore(4);
			updateRank(4);
		});

		/**
		 * 점수판(나와 상대유저들의 랭크 및 스코어) label[0]은 따로처리
		 */
		for (int i = 0; i < 9; i++) {
			label[i].setBounds(0, 30 * i, 110, 30);
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
			text_Nickname[i].setBounds(110, 30 * i, 130, 30);
			text_Score[i].setBounds(240, 30 * i, 130, 30);
			text_Nickname[i].setHorizontalAlignment(SwingConstants.CENTER);
			text_Score[i].setHorizontalAlignment(SwingConstants.CENTER);
			text_Nickname[i].setFont(new Font("Dialog", Font.PLAIN, 16));
			text_Score[i].setFont(new Font("Dialog", Font.PLAIN, 16));
			text_Nickname[i].setEditable(false);
			text_Score[i].setEditable(false);
		}

	}
}
