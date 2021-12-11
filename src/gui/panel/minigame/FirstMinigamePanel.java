package gui.panel.minigame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Client;
import data.Player;
import gui.frame.GameFrame;

/**
 * 같은 그림 찾기
 * @author phdljr
 *
 */
public class FirstMinigamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Client client;
	private Player player;

	
	int getsu =4; 
	JButton [][] btn = new JButton [getsu][getsu];
	int [][] answer = new int[getsu][getsu];
	JLabel[] imglb = new JLabel[8];
	
	JButton firstClick = null;
	int firstRow=0, firstCol=0;
	private int score=0;
	private int checkEnd=0;
	private String my_room;

	public FirstMinigamePanel(GameFrame gf) {
		
		
		this.setClient(gf.getClient());
		this.player = gf.getClient().getPlayer();
		my_room=player.getRoomName();
		
		this.getClient().setPanel(this);
		
		
		setSize(800, 600);
		//add(new JLabel("미니게임1 - 같은 그림 찾기"));
		
		for(int i = 0; i<4;i++) {
			for(int j = 0;j<4;j++) {
				btn[i][j] = new JButton();
			}
		}

		this.addLayout();
	}
	
	public void addLayout() {
		
		setLayout(new GridLayout(getsu, getsu));
		for(int i = 0;i<getsu;i++) {
			for (int j = 0 ;j<getsu;j++) {
				add(btn[i][j]);
				//문자를 '0' 
				answer[i][j] = '0';
				
				
				btn[i][j].addActionListener((e)->{
					JButton b = (JButton)e.getSource();
					System.out.println("status? "+player.getStatus());
					if(player.getStatus()==true)
					ClickingBtnSituation(b,e);
				});
			}
		}
		setSize(700, 700);
		setVisible(true);
		if(player.getStatus()==true) //최초 접속자
		{
			initChar(); //여기서 makegame
		}
		else if(player.getStatus()==false) //두 번째 접속자
		{ 
			try {

				Thread.sleep(500); //0.5초 대기

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			getClient().sendMessageToServer("SetGame/null");
			getClient().sendMessageToServer("StartGame/null");
		}

	}
	

	
	public JButton CheckButton(String msg) //상대방이 누른 버튼의 좌표 반환 (0번 버튼->[0][0])
	{
		int num=Integer.parseInt(msg);
		return btn[num/getsu][num%getsu];
	}
	
	private int CheckImgNum(int getsu,int row,int col)
	{
		int imgNum=0;
		for(int i=0;i<getsu;i++)
		{

			for(int j=0;j<getsu;j++)
			{
				if(i==row&&j==col)
					return imgNum;
				else
					imgNum++;
			}
			
		}
		
		return imgNum;
	}

	public void ClickingBtnSituation(JButton b,ActionEvent e)
	{
		int imgnum;
		String msg;
		for(int i=0 ; i<getsu;i++) {
			for(int j=0;j<getsu;j++) {
				if(b == btn[i][j]) {
					
					
					if(firstClick == null) { //첫번째 선택시
						firstClick = b;
						firstRow = i;
						firstCol = j;
						firstClick.setIcon(new ImageIcon ("src\\data\\img\\b"+answer[firstRow][firstCol]+".PNG"));
						
						
					}else { //두번째 선택시 
						if(answer[i][j]==answer[firstRow][firstCol]) { //그림 일치
							firstClick.setBackground(Color.gray);
							b.setIcon(new ImageIcon ("src\\data\\img\\b"+answer[firstRow][firstCol]+".PNG"));
							if(e!=null)
								score+=10;
							checkEnd+=1;

							
						}else { //그림 불일치

							firstClick.setIcon(null);
							
						}
						if(i== firstRow && j == firstCol) {
							continue;
						}
						
						System.out.println("second");
						firstClick= null;

					
						
					}
					if(e!=null) //버튼 눌림 이벤트 발생했을 때
					{
						imgnum=CheckImgNum(getsu,i,j); //몇 번 버튼 클릭됐는지
						msg=Integer.toString(imgnum); //int to string
						getClient().sendMessageToServer("ButtonClicked/"+my_room+"/"+msg); //서버에 전송

						if(answer[i][j]!=answer[firstRow][firstCol]) //버튼 이벤트인데 그림이 불일치일 때
						{getClient().sendMessageToServer("ChangePlayer/null");}
							

					}
					if(checkEnd==8)
					{
						JOptionPane.showMessageDialog(null, "게임종료", "알림", JOptionPane.INFORMATION_MESSAGE);
						getClient().sendMessageToServer("GameOver/"+player.getNickname()+"/"+Integer.toString(score));
					}
				}
				

			}
		}

	}
	
	public void initChar() {
		int alpha =0;
		client.sendMessageToServer("makeArray/"+Integer.toString(getsu));
		DASI:
			for(int i=0;i<getsu*getsu;) {
				//임의의 알파벳을 만들기
				if(i%2==0) {
					alpha = (int)(Math.random()*8);
					
					// 기존의 알파벳과 동일한걸 발견하면 
					for(int r = 0;r<getsu;r++) {
						for (int c = 0 ;c<getsu;c++) {
							if( answer[r][c] == alpha) 
							{
								
								continue DASI;
							}
						}

					}// end of 기존 알파벳 

				}

				// 임의의 위치에 지정 
				boolean ok = false;
				do {
					int row = (int)(Math.random()*getsu);
					int col = (int)(Math.random()*getsu);
					if(answer[row][col] == '0') {
						answer[row][col]=alpha;
						
						getClient().sendMessageToServer("MakeGame/"+IntToStr(getsu)+"/"+IntToStr(row)+"/"+IntToStr(col)+"/"+IntToStr(alpha));
						i++;
						ok = true;
					}
				}while(!ok);
			}
	}
	public void showAnswer() {
		
		// 그림을 버튼에 지정하기 
		for(int i=0 ; i<getsu;i++) {
			for(int j=0;j<getsu;j++) {
				btn[i][j].setIcon(new ImageIcon ("src\\data\\img\\b"+answer[i][j]+".PNG"));
				
			}
		}

		//버튼에서 그림을 2초후에 지우기 
		try {
			System.out.println("정상실행됨!");
			Thread.sleep(2000);
		}catch(Exception ex) {}


		for(int i=0 ; i<getsu;i++) {
			for(int j=0;j<getsu;j++) {
				btn[i][j].setIcon(null);
			}

		}//end of 2초후에 지우기

	}
	public void modifyAnswer(String row1,String col1,String value1)
	{
		int row=StrToInt(row1);
		int col=StrToInt(col1);
		int value=StrToInt(value1);
		
		answer[row][col]=value;
	}
	
	String IntToStr(int num)
	{
		return Integer.toString(num);
		
	}
	int StrToInt(String str)
	{
		return Integer.parseInt(str);
	}
	public static int[][] deepCopy(int[][] original, int n) {
	    if (original == null) {
	        return null;
	    }

	    int[][] result = new int[n][n];
	    for (int i = 0; i < original.length; i++) {
	        System.arraycopy(original[i], 0, result[i], 0, original[i].length);
	    }
	    return result;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
