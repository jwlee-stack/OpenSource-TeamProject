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

import data.Player;
import gui.frame.GameFrame;
import gui.panel.minigame.FirstMinigamePanel;
import gui.panel.minigame.SecondMinigamePanel;

/**
 * 서버로부터 데이터를 주고 받을 수 있는 클라이언트의 정보가 포함된 클래스이다. 해당 클래스로부터 서버로의 입출력 스트림을 통해 메세지를
 * 받아올 수 있다. 서버로부터 받은 메세지는 해당 클래스의 inMessageFromServer 메소드로부터 분석되며, 이에 따라 플레이어의
 * 상태를 변경시킬 수 있다.
 * 
 * @author Ted
 *
 */
public class Client {
	private Player player;
	private FirstMinigamePanel fp;
	private SecondMinigamePanel sp;
	private GameFrame gf;
	
//	private String serverIP = "13.124.194.183";
//	private int serverPort = 59647;
	private String serverIP = "127.0.0.1";
	private int serverPort = 9999;

	private Socket socket;
	private InputStream is;
	private DataInputStream dis; // 서버로 데이터를 보내는 스트림
	private OutputStream os;
	private DataOutputStream dos; // 서버로부터 데이터는 받는 스트림

	private Thread threadGettingMsg; // 서버로부터 메세지를 받는 역할을 행하는 스레드

	public Client(GameFrame gf) {
		this.player = new Player();
		this.fp = null;
		this.sp = null;
		this.gf = gf;
	}

	public void setPanel(FirstMinigamePanel fp) {
		this.fp = fp;

	}
	public void setPanel(SecondMinigamePanel sp) {
		this.sp = sp;		
	}

	/**
	 * 서버의 ip와 port에 연결을 시도한다. 포트 번호는 1024를 넘겨야만 한다.
	 * 
	 * @param ip
	 * @param port
	 * @return 연결 성공 시 true
	 */
	public boolean connectToServer(String ip, int port) {
		try {
			socket = new Socket(ip, port);

			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

			System.out.println(ip + ":" + port + "주소로 연결 완료");
		} catch (UnknownHostException e) {
			// e.printStackTrace();
			System.out.println("서버 접속 실패");
			return false;
		} catch (IOException e) {
			System.out.println("서버 접속 실패");
			return false;
		}

		// 연결이 정상적으로 되면 서버로부터 메세지를 받는 스레드를 실행함
		if (socket != null) {
			runThreadToGetMessage();
		}

		return (socket != null);
	}

	/**
	 * 서버로부터 메세지를 받는 역할을 행하는 스레드를 실행시킨다. 서버와의 연결이 끊기면, 연결된 소켓 및 스트림을 끊는다.
	 */
	public void runThreadToGetMessage() {
		threadGettingMsg = new Thread(() -> {
			while (true) {
				try {
					String msg = dis.readUTF();
					inMessageFromServer(msg);
				} catch (IOException e) {
					disconnect();
					System.exit(0);
					break;
				}
			}
		});

		threadGettingMsg.start();
	}

	/**
	 * 서버와 연결이 끊어질 때 실행되는 메소드. 열려있는 모든 스트림 및 소켓을 닫는다.
	 */
	public void disconnect() {
		try {
			dis.close();
			dos.close();
			socket.close();

			JOptionPane.showMessageDialog(null, "서버와의 연결이 끊겼습니다.", "연결 끊김", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 서버로부터 받은 메세지를 분해해서 의미를 파악하고 이에 대해 행하는 메소드이다. 행하는 방식은 메세지의 형태 즉, 프로토콜마다 다르며
	 * 각각의 프로토콜마다 정해진 메세지 규칙이 존재한다.<br/>
	 * 메세지의 형태는 <strong>'프로토콜/데이터'</strong>로 이루어져 있다.
	 * 
	 * @apiNote - ShowGame1/ : 플레이어의 playGameNum을 1로 설정. 이를 통해, WaitRoomPanel에서
	 *          플레이어의 화면을 미니게임1로 바꾸게 한다. <br/>
	 *          - SendRoomName/ : 플레이어가 들어간 게임방의 이름이 담긴 메세지. 해당 게임방 이름은 서버의
	 *          roomVector에서 플레이어가 속한 방을 찾는 용도로 쓰인다.<br/>
	 *
	 *          - 계속 추가될 예정
	 * 
	 * @param msg - 메세지
	 */
	public void inMessageFromServer(String msg) {
		System.out.println("받은 메세지 : " + msg);

		StringTokenizer st = new StringTokenizer(msg, "/");

		String protocol = st.nextToken();
		String data = st.nextToken();

		if (protocol.equals("ShowGame1")) { // server의 joinwaitroom 프로토콜에서 보냄
			player.setPlayGameNum(1);

			if (data.equals("CreateGame")) // 최초 접속자->게임 만듦
			{
				player.setStatus(Boolean.TRUE); // 초기값이 true면 게임 생성도 하고 먼저 게임을 시작함

			} else if (data.equals("GetGame")) // 두 번째 접속자->서버에서 게임 받아옴
			{
				player.setStatus(Boolean.FALSE);
			}
			// -menupanel.java->sendMessageToServer("JoinWaitRoom1")줄 실행완료
		} else if (protocol.equals("GameOut")) {
			JOptionPane.showMessageDialog(null, "상대방이 게임을 나갔습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			gf.setTitle("꾸러미 동산");
			player.setPlayGameNum(0);
			player.setStatus(null);
			gf.changePanel("menu");
		} else if (protocol.equals("CheckGame")) {
			if (data.equals("true")) {
				fp.setCheck(true);
			}
		} else if (protocol.equals("SetGame")) {
			String row = data;
			String col = st.nextToken();
			String value = st.nextToken();
			fp.modifyAnswer(row, col, value);
			System.out.println("row:" + row + "col:" + col + "value:" + value);
		} else if (protocol.equals("StartGame")) {
			fp.showAnswer();
		} else if (protocol.equals("ClickButton")) {

			if (!(data.equals(player.getNickname()))) // 서버에서 받은 아이디랑 내 아이디랑 다름
			{
				String btn = st.nextToken();
				JButton b;
				System.out.println(data + "가 누른 버튼:" + btn); // 상대가 누른 버튼
				b = fp.CheckButton(btn);
				fp.ClickingBtnSituation(b, null);
			}

		} else if (protocol.equals("ChangePlayer")) {
			if (player.getStatus() == false) {
				player.setStatus(true);
				gf.setTitle("내 차례");
				System.out.println("나는 게임중임!");
			} else {
				player.setStatus(false);
				gf.setTitle("상대방 차례");
				System.out.println("나는 게임중이 아님!");
			}
		} else if (protocol.equals("gameover")) {
			String winner = data;
			String scoreA = st.nextToken();
			String scoreB = st.nextToken();
			String highscore;
			String lowscore;
			if (Integer.parseInt(scoreA) > Integer.parseInt(scoreB)) {
				highscore = scoreA;
				lowscore = scoreB;
			} else if (Integer.parseInt(scoreA) < Integer.parseInt(scoreB)) {
				highscore = scoreB;
				lowscore = scoreA;
			} else // 동점. 동점은 점수 변동 없음
			{
				highscore = scoreB;
				lowscore = scoreA;
				JOptionPane.showMessageDialog(null, "내 점수: " + highscore + "\n상대방 점수" + lowscore, "Draw",
						JOptionPane.INFORMATION_MESSAGE);
				player.setTempScore(Integer.parseInt(highscore));
				player.setTempResult("무승부");
				player.setPlayGameNum(0);
				player.setStatus(null);
				gf.changePanel("rematching");
				return;
			}
			if (player.getNickname().equals(winner)) { //이기면 얻은 점수만큼 추가됨
				player.setTempScore(Integer.parseInt(highscore));
				player.setTempResult("승리");
				player.updateScore(1, Integer.parseInt(highscore));
				JOptionPane.showMessageDialog(null, "내 점수: " + highscore + "\n상대방 점수" + lowscore, "you win",
						JOptionPane.INFORMATION_MESSAGE);
			} else { //지면 점수가 20점 까임
				player.setTempScore(-20);
				player.setTempResult("패배");
				player.updateScore(1, -20);
				JOptionPane.showMessageDialog(null, "내 점수: " + lowscore + "\n상대방 점수" + highscore, "you lose",
						JOptionPane.INFORMATION_MESSAGE);
			}
			//player.setPlayGameNum(0);
			player.setStatus(null);
			gf.changePanel("rematching");
		}
		
		/////////////////////////////////////////////////////// 게임2
		/////////////////////////////////////////////////////// 게임2
		
		else if (protocol.equals("ShowGame2")) { // server의 joinwaitroom 프로토콜에서 보냄
			
			if (data.equals("CreateGame")) // 최초 접속자->게임 만듦
			{
				player.setStatus(Boolean.TRUE); // 초기값이 true면 게임 생성도 하고 먼저 게임을 시작함
			} else if (data.equals("GetGame")) // 두 번째 접속자->서버에서 게임 받아옴
			{
				player.setStatus(Boolean.FALSE);
			}
			
			player.setPlayGameNum(2);
		}
		else if (protocol.equals("LetStone")) {
			String row=data;
			String col=st.nextToken();
			sp.Rival_PaintStone(row,col);
		}
		else if (protocol.equals("GameOver2")) {
			String winner=data;
			String winnerNickname = st.nextToken();
			if(player.getNickname().equals(winnerNickname)) { //내가 이겼을 때 +100점
				player.setTempScore(100);
				player.setTempResult("승리");
				player.updateScore(2, 100);
			}
			else { //내가 졌을 때 -30점
				player.setTempScore(-30);
				player.setTempResult("패배");
				player.updateScore(2, -30);
			}
			JOptionPane.showMessageDialog(null, winner, "게임 끝", JOptionPane.PLAIN_MESSAGE);
			//player.setPlayGameNum(0);
			player.setStatus(null);
			gf.changePanel("rematching");
		}
	}

	/**
	 * 서버로 메세지를 보내는 메소드. 이를 통해 서버 및 서버에 접속한 클라이언트와 데이터를 주고 받을 수 있다.
	 * 
	 * @param msg
	 */
	public void sendMessageToServer(String msg) {
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 해당 클라이언트의 Player 정보를 얻는다. 
	 * @return
	 */
	public Player getPlayer() {
		return this.player;
	}

	public void setgameframe(GameFrame gf) {
		this.gf = gf;
	}
	
	/**
	 * 서버 IP를 얻어온다.
	 * @return
	 */
	public String getServerIP() {
		return serverIP;
	}
	
	/**
	 * 서버 port 번호를 얻어온다.
	 * @return
	 */
	public int getServerPort() {
		return serverPort;
	}
}
