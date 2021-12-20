package gui.panel.minigame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.Client;
import data.Player;
import gui.frame.GameFrame;

/**
 * 같은 그림 찾기 - 작업중
 * 
 * @author phdljr
 *
 */
public class FirstMinigamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Client client;
	private Player player;
	private GameFrame gf;

	//private int getsu = new Random().nextInt(3)+3; //3*3 ~ 5*5
	private int getsu = 4;
	private JButton[][] btn = new JButton[getsu][getsu];
	private int[][] answer = new int[getsu][getsu];
	//private JLabel[] imglb = new JLabel[8];

	private JButton firstClick = null;
	private int firstRow = 0, firstCol = 0;
	private int score = 0;
	private int checkEnd = 0;

	private Boolean check = false;

	public FirstMinigamePanel(GameFrame gf) {
		this.gf = gf;
		this.client = gf.getClient();
		this.player = gf.getClient().getPlayer();
		this.client.setPanel(this);
		
		setSize(800, 600);
		
		for (int i = 0; i < getsu; i++) {
			for (int j = 0; j < getsu; j++) {
				btn[i][j] = new JButton();
			}
		}
		
		addLayout();
		
		if(player.getStatus() == true) {
			gf.setTitle("내 차례");
		}
		else {
			gf.setTitle("상대방 차례");
		}
	}

	private void addLayout() {
		setLayout(new GridLayout(getsu, getsu));
		for (int i = 0; i < getsu; i++) {
			for (int j = 0; j < getsu; j++) {
				add(btn[i][j]);
				// 문자를 '0'
				answer[i][j] = '0';

				btn[i][j].addActionListener((e) -> {
					JButton b = (JButton) e.getSource();
					System.out.println("status? " + player.getStatus());
					if (player.getStatus() == true) {
						ClickingBtnSituation(b, e);
					}
					else {
					}
				});
			}
		}
		setSize(700, 700);
		setVisible(true);

		if (player.getStatus() == Boolean.TRUE) // 최초 접속자
		{
			initChar(); // 여기서 makegame
		} else if (player.getStatus() == Boolean.FALSE) // 두 번째 접속자
		{

			while (true) {
				try {
					Thread.sleep(200); //0.2초 간격으로 보내기
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				client.sendMessageToServer("CheckGame/null");

				if (check == true)
					break;
			}

			client.sendMessageToServer("SetGame/null");
			client.sendMessageToServer("StartGame/null");
		}
	}

	public JButton CheckButton(String msg) // 상대방이 누른 버튼의 좌표 반환 (0번 버튼->[0][0])
	{
		int num = Integer.parseInt(msg);
		return btn[num / getsu][num % getsu];
	}

	private int CheckImgNum(int getsu, int row, int col) {
		int imgNum = 0;
		for (int i = 0; i < getsu; i++) {
			for (int j = 0; j < getsu; j++) {
				if (i == row && j == col)
					return imgNum;
				else
					imgNum++;
			}
		}
		return imgNum;
	}

	public void ClickingBtnSituation(JButton b, ActionEvent e) {
		int imgnum;
		String msg;
		for (int i = 0; i < getsu; i++) {
			for (int j = 0; j < getsu; j++) {
				if (b == btn[i][j]) {
					if (firstClick == null) { // 첫번째 선택시
						firstClick = b;
						firstRow = i;
						firstCol = j;
						URL url = getClass().getClassLoader().getResource("b" + answer[firstRow][firstCol] + ".png");
						ImageIcon image = new ImageIcon(url);
						firstClick.setIcon(image);
						if (firstClick.getBackground() == Color.gray) // 이미 맞춘 이미지임
						{
							firstClick = null;
						}
					} else { // 두번째 선택시
						if (i == firstRow && j == firstCol) { // 첫번째로 선택한 이미지임
							continue;
						}
						if (b.getBackground() == Color.gray) // 이미 맞춘 이미지임
						{
							continue;
						}
						if (answer[i][j] == answer[firstRow][firstCol]) { // 그림 일치
							firstClick.setBackground(Color.gray);
							b.setBackground(Color.gray);
							URL url = getClass().getClassLoader()
									.getResource("b" + answer[firstRow][firstCol] + ".png");
							ImageIcon image = new ImageIcon(url);
							b.setIcon(image);
							if (e != null)
								score += 10;
							checkEnd += 1;
						} else { // 그림 불일치
							firstClick.setIcon(null);
						}
						firstClick = null;
					}
					if (e != null) // 버튼 눌림 이벤트 발생했을 때
					{
						imgnum = CheckImgNum(getsu, i, j); // 몇 번 버튼 클릭됐는지
						msg = Integer.toString(imgnum); // int to string
						client.sendMessageToServer("ButtonClicked/" + msg); // 서버에 전송

						if (answer[i][j] != answer[firstRow][firstCol]) // 버튼 이벤트인데 그림이 불일치일 때
						{
							client.sendMessageToServer("ChangePlayer/null");
						}
					}
					if (checkEnd == 8) {
						client.sendMessageToServer(
								"GameOver/" + player.getNickname() + "/" + Integer.toString(score));
					}
				}
			}
		}
	}

	void initChar() {
		int alpha = 0;
		client.sendMessageToServer("makeArray/" + Integer.toString(getsu));
		DASI: for (int i = 0; i < getsu * getsu;) {
			// 임의의 알파벳을 만들기
			if (i % 2 == 0) {
				alpha = (int) (Math.random() * 8);

				// 기존의 알파벳과 동일한걸 발견하면
				for (int r = 0; r < getsu; r++) {
					for (int c = 0; c < getsu; c++) {
						if (answer[r][c] == alpha) {

							continue DASI;
						}
					}
				} // end of 기존 알파벳
			}

			// 임의의 위치에 지정
			boolean ok = false;
			do {
				int row = (int) (Math.random() * getsu);
				int col = (int) (Math.random() * getsu);
				if (answer[row][col] == '0') {
					answer[row][col] = alpha;
					client.sendMessageToServer("MakeGame/" + IntToStr(getsu) + "/" + IntToStr(row) + "/"
							+ IntToStr(col) + "/" + IntToStr(alpha));
					i++;
					ok = true;
				}
			} while (!ok);
		}
	}

	public void showAnswer() {

		// 그림을 버튼에 지정하기
		for (int i = 0; i < getsu; i++) {
			for (int j = 0; j < getsu; j++) {
				URL url = getClass().getClassLoader().getResource("b" + answer[i][j] + ".png");
				ImageIcon image = new ImageIcon(url);
				btn[i][j].setIcon(image);
			}
		}

		// 버튼에서 그림을 2초후에 지우기
		try {
			System.out.println("정상실행됨!");
			Thread.sleep(2000);
		} 
		catch (Exception ex) {}

		for (int i = 0; i < getsu; i++) {
			for (int j = 0; j < getsu; j++) {
				btn[i][j].setIcon(null);
			}
		} // end of 2초후에 지우기
	}

	public void modifyAnswer(String row1, String col1, String value1) {
		int row = StrToInt(row1);
		int col = StrToInt(col1);
		int value = StrToInt(value1);

		answer[row][col] = value;
	}

	public String IntToStr(int num) {
		return Integer.toString(num);
	}

	public int StrToInt(String str) {
		return Integer.parseInt(str);
	}

	//public Client getClient() {
		//return client;
	//}
	
	public int getScore() {
		return score;
	}
	
	public void setCheck(Boolean check) {
		this.check = check;
	}

}