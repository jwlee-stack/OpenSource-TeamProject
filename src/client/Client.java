package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import data.Player;

/**
 * 서버로부터 데이터를 주고 받을 수 있는 클라이언트의 정보가 포함된 클래스이다.
 * 해당 클래스로부터 서버로의 입출력 스트림을 통해 메세지를 받아올 수 있다.
 * 서버로부터 받은 메세지는 해당 클래스의 inMessageFromServer 메소드로부터 분석되며,
 * 이에 따라 플레이어의 상태를 변경시킬 수 있다.
 * 
 * @author Ted
 *
 */
public class Client {
	private Player player;
	
	//서버로 연결됐다면 true값을 가짐
	private boolean isConnected;

	private Socket socket;
	private InputStream is;
	private DataInputStream dis; //서버로 데이터를 보내는 스트림
	private OutputStream os;
	private DataOutputStream dos; //서버로부터 데이터는 받는 스트림
	
	private Thread threadGettingMsg; //서버로부터 메세지를 받는 역할을 행하는 스레드

	public Client() {
		this.player = new Player();
		//connectToServer("127.0.0.1", 9999);
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
			//e.printStackTrace();
			System.out.println("서버 접속 실패");
			return false;
		} catch (IOException e) {
			System.out.println("서버 접속 실패");
			return false;
		}
		
		//연결이 정상적으로 되면 서버로부터 메세지를 받는 스레드를 실행함
		if(socket != null) {
			runThreadToGetMessage();
		}
		
		return (socket != null);
	}
	
	/**
	 * 서버로부터 메세지를 받는 역할을 행하는 스레드를 실행시킨다.
	 * 서버와의 연결이 끊기면, 연결된 소켓 및 스트림을 끊는다.
	 */
	public void runThreadToGetMessage() {
		threadGettingMsg = new Thread(() -> {
			while (true) {
				try {
					String msg = dis.readUTF();
					inMessageFromServer(msg);
				} catch (IOException e) {
					disconnect();
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
			
			System.out.println("서버와의 연결 끊김");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 서버로부터 받은 메세지를 분해해서 의미를 파악하고 이에 대해 행하는 메소드이다.
	 * 행하는 방식은 메세지의 형태 즉, 프로토콜마다 다르며 각각의 프로토콜마다 정해진 메세지 규칙이 존재한다.<br/>
	 * 메세지의 형태는 <strong>'프로토콜/데이터'</strong>로 이루어져 있다.
	 * @apiNote
	 * - ShowGame1/ : 플레이어의 playGameNum을 1로 설정. 이를 통해, WaitRoomPanel에서 플레이어의 화면을 미니게임1로 바꾸게 한다. <br/>
	 * - SendRoomName/ : 플레이어가 들어간 게임방의 이름이 담긴 메세지.
	 *  해당 게임방 이름은 서버의 roomVector에서 플레이어가 속한 방을 찾는 용도로 쓰인다.<br/>
	 *
	 * - 계속 추가될 예정
	 * 
	 * @param msg - 메세지
	 */
	public void inMessageFromServer(String msg) {
		System.out.println("받은 메세지 : "+msg);
		
		StringTokenizer st = new StringTokenizer(msg, "/");
		
		String protocol = st.nextToken();
		String data = st.nextToken();
		
		if(protocol.equals("ShowGame1")) {
			player.setPlayGameNum(1);
		}
		else if(protocol.equals("SendRoomName")) {
			player.setRoomName(data);
		}
	}
	
	/**
	 * 서버로 메세지를 보내는 메소드. 이를 통해 서버 및 서버에 접속한 클라이언트와 데이터를 주고 받을 수 있다.
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
}
