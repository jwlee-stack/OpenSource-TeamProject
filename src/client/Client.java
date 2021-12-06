package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import data.GameInfo;
import data.Player;
import gui.frame.GameFrame;
import gui.panel.minigame.FirstMinigamePanel;


public class Client{
	private Player player;
	private GameInfo gameinfo;
	private FirstMinigamePanel fp;
	
	private boolean isConnected;

	private Socket socket;
	private InputStream is;
	private DataInputStream dis;
	private OutputStream os;
	private DataOutputStream dos;
	
	private Thread threadGettingMsg;

	public Client() {
		this.player = new Player();
		this.gameinfo=new GameInfo();
		this.fp=null;
		//connectToServer("127.0.0.1", 9999);
		
	}
	
	public void setPanel(FirstMinigamePanel Fp)
	{
		this.fp= Fp;
		System.out.println(fp);
	}
	public boolean connectToServer(String ip, int port) {
		try {
			socket = new Socket(ip, port);

			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

			System.out.println(ip + ":" + port + "주소로 연결 완료");

		} catch (UnknownHostException e) {
			//e.printStackTrace();
			System.out.println("알 수 없는 주소");
			return false;
		} catch (IOException e) {
			System.out.println("서버 접속 실패");
			return false;
		}
		
		if(socket != null) {
			runThreadToGetMessage();
		}
		
		return (socket != null);
	}
	
	public void runThreadToGetMessage() {
		threadGettingMsg = new Thread(() -> {
			while (true) {
				try {
					String msg = dis.readUTF();
					inMessageFromServer(msg);
				} catch (IOException e) {
					//e.printStackTrace();
					disconnect();
					break;
				}
			}
		});
		
		threadGettingMsg.start();
	}
	
	public void disconnect() {
		try {
			dis.close();
			dos.close();
			socket.close();
			
			System.out.println("서버와의 연결 끊김");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void inMessageFromServer(String msg) {
		System.out.println("받은 메세지 : "+msg);
		
		StringTokenizer st = new StringTokenizer(msg, "/");
		
		String protocol = st.nextToken();
		String data = st.nextToken();
		
		if(protocol.equals("ShowGame1")) {
			player.setPlayGameNum(1);
			
			if(data.equals("CreateGame")) //최초 접속자->게임 만듦
			{
				player.setStatus(true); //초기값이 true면 게임 생성도 하고 먼저 게임을 시작함
				//sendMessageToServer("CreateGame/null");
			}
			if(data.equals("GetGame")) //두 번째 접속자->서버에서 게임 받아옴
			{
				player.setStatus(false);
			}
					
		}
		else if(protocol.equals("SendRoomName")) {
			player.setRoomName(data);
		}
		else if(protocol.equals("SetGame")) {
			String row=data;
			String col=st.nextToken();
			String value=st.nextToken();
			fp.modifyAnswer(row,col,value);
			System.out.println("row:"+row+"col:"+col+"value:"+value);
			
		}
		else if(protocol.equals("CreateGame")) {
			//fp.addLayout();
		}
		else if(protocol.equals("StartGame")) {
			fp.showAnswer();
		}
		else if (protocol.equals("Chatting")) {
			
			if(!(data.equals(player.getNickname()))) //서버에서 받은 아이디랑 내 아이디랑 다름
			{
				String btn = st.nextToken();
				JButton b;
				System.out.println(data+"가 누른 버튼:"+btn); //상대가 누른 버튼
				b=fp.CheckButton(btn);
				fp.ClickingBtnSituation(b,null);
			}
			
		}
		else if (protocol.equals("ChangePlayer")) {
			if(player.getStatus()==false)
			{player.setStatus(true);
			System.out.println("나는 게임중임!");}
			else
			{player.setStatus(false);
			System.out.println("나는 게임중이 아님!");}
		}
		else if(protocol.equals("gameover"))
		{
			String winner=data;
			String scoreA=st.nextToken();
			String scoreB=st.nextToken();
			String highscore;
			String lowscore;
			if(Integer.parseInt(scoreA)>Integer.parseInt(scoreB))
			{
				highscore=scoreA;
				lowscore=scoreB;
			}
			else if(Integer.parseInt(scoreA)<Integer.parseInt(scoreB))
			{
				highscore=scoreB;
				lowscore=scoreA;
			}
			else //동점
			{
				highscore=scoreB;
				lowscore=scoreA;
				JOptionPane.showMessageDialog(null, "내 점수: "+highscore+"\n상대방 점수"+lowscore, "Draw("+player.getNickname()+")", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(player.getNickname().equals(winner))
			{
				JOptionPane.showMessageDialog(null, "내 점수: "+highscore+"\n상대방 점수"+lowscore, "you win("+player.getNickname()+")", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "내 점수: "+lowscore+"\n상대방 점수"+highscore, "you lose("+player.getNickname()+")", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void sendMessageToServer(String msg) {
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return this.player;
	}
	public GameInfo getGameInfo() {
		return this.gameinfo;
	}

	
	
	//테스트용
	public static void main(String[] args) {
		new Client();
	}
}


