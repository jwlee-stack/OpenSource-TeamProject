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

public class Client {
	private Player player;
	
	private boolean isConnected;

	private Socket socket;
	private InputStream is;
	private DataInputStream dis;
	private OutputStream os;
	private DataOutputStream dos;
	
	private Thread threadGettingMsg;

	public Client() {
		this.player = new Player();
		//connectToServer("127.0.0.1", 9999);
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
			System.out.println("서버 접속 실패");
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
	
	
	//테스트용
	public static void main(String[] args) {
		new Client();
	}
}
