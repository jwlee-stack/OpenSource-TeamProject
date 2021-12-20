package gui.panel;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import data.Player;
import gui.frame.GameFrame;

/**
 * 게임이 끝나고 보여질 화면이다.
 * 승패 결과와 잠수를 보여준다. 밑의 버튼 두 개를 통해
 * 재매칭을 할 수도 있고, 메뉴로 이동할 수도 있다.
 * @author Ted
 *
 */
public class RematchingPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JLabel score_label;
	private JLabel result_label;
	
	//score : 점수 , result : 승패
	public RematchingPanel(GameFrame gf) {
		setBackground(SystemColor.info);
		setLayout(null);
		
		Player player = gf.getClient().getPlayer();
		
		JLabel finishlabel = new JLabel("게임종료");
		finishlabel.setBounds(325, 80, 150, 80);
		finishlabel.setHorizontalAlignment(SwingConstants.CENTER);
		finishlabel.setFont(new Font("굴림", Font.PLAIN, 28));
		add(finishlabel);
		
		JButton btn_rematch = new JButton("재매칭");
		btn_rematch.setBounds(200, 400, 100, 100);
		btn_rematch.addActionListener((e)->{
			gf.getClient().getPlayer().setSearchingGameNum(1);
			gf.getClient().sendMessageToServer("JoinWaitRoom1/"+player.getNickname());
			gf.changePanel("waitroom");
		});
		add(btn_rematch);
		
		JButton btn_exit = new JButton("나가기");
		btn_exit.setBounds(500, 400, 100, 100);
		btn_exit.addActionListener((e)->{
			gf.changePanel("menu");
		});
		add(btn_exit);
		
		result_label = new JLabel();
		result_label.setHorizontalAlignment(SwingConstants.CENTER);
		result_label.setBounds(300, 170, 200, 80);
		add(result_label);
		
		score_label = new JLabel();
		score_label.setHorizontalAlignment(SwingConstants.CENTER);
		score_label.setBounds(300, 270, 200, 80);
		add(score_label);
	}
	
	/**
	 * 점수와 승패 결과를 입력시켜 해당 패널 위에 출력시킨다.
	 * 
	 * @param score 더해질 점수
	 * @param result "승리", "패배"
	 */
	public void setLabelInfo(int score, String result) {
		score_label.setText("점수 : "+score+"점");
		result_label.setText(result);
	}
}