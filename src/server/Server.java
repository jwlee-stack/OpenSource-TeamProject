package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 클라이언트와 소켓 통신을 하기 위한 클래스이다.
 * 
 * @author Ted
 *
 */
public class Server {
	private ServerSocket serverSocket;
	private Vector<ClientInfo> clientVector;
	
	/**
	 * port 번호를 사용해 서버를 만들고 실행시킨다.
	 * 
	 * @param port
	 */
	public Server(int port) {
		this.clientVector = new Vector<ClientInfo>();
		
		openServer(port);
		acceptClient();
	}
	
	/**
	 * 해당 Server 클래스를 실행시킬 컴퓨터의 ip로 port번호를 사용해서
	 * 서버를 실행시킨다.
	 * 
	 * @param port - 포트 번호
	 * @return 정상적으로 열리면 true를 반환
	 */
	public boolean openServer(int port) {
		System.out.println("포트번호 " + port +"로 여는중...");
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) { //사용중인 port번호일 때
			e.printStackTrace();
		}
		
		return (serverSocket != null);
	}
	
	/**
	 * 서버로 접속을 요청하는 클라이언트를 받아들이는 과정을 대신해줄 스레드를 생성 및 실행시킨다.
	 * 접속한 클라이언트의 소켓은 새로 만든 ClientInfo객체 안의 socket멤버 필드에 저장해두며
	 * 해당 ClientInfo객체를 clientVector에 추가하게 된다.
	 */
	public void acceptClient() {
		new Thread(()->{
			while(true) {
				try {
					System.out.println("클라이언트의 접속 요청을 기다리는 중...");
					Socket socket = serverSocket.accept();
					
					ClientInfo ci = new ClientInfo(socket);
					clientVector.add(ci);
					
					System.out.println("클라이언트"+socket.getInetAddress().toString()+"의 접속 요청 수신");
				} catch (IOException e) { //서버가 종료될 시
					//e.printStackTrace();
					closeServer();
				}
			}
		}).start();
	}
	
	public void closeServer() {
		try {
			serverSocket.close();
			clientVector.clear();
			
			System.out.println("서버 종료");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 서버로부터 접속한 클라이언트의 소켓과 연결된 입출력 스트림 등 클라이언트의 정보가
	 * 들어 있는 클래스이다. Server클래스 내에서만 사용되는 클래스이며, 클라이언트와 통신하기 위해 만들어 졌다.
	 * 
	 * @author Ted
	 *
	 */
	private class ClientInfo{
		
		private Socket socket;
		private InputStream is;
		private DataInputStream dis;
		private OutputStream os;
		private DataOutputStream dos;
		
		private Thread threadGettingMsg;

		public ClientInfo(Socket socket) { 
			this.socket = socket;
			
			connect();
		}
		
		public void connect() {
			try {
				is = socket.getInputStream();
				dis = new DataInputStream(is);
				os = socket.getOutputStream();
				dos = new DataOutputStream(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			runThreadToGetMessage();
		}
		
		public void runThreadToGetMessage() {
			threadGettingMsg = new Thread(() -> {
				while (true) {
					try {
						String msg = dis.readUTF();
						inMessageFromClient(msg);
					} catch (IOException e) { //클라이언트로 연결이 끊길 시
						//e.printStackTrace();
						try {
							clientVector.remove(this);
							dis.close();
							dos.close();
							socket.close();
							System.out.println("클라이언트 연결 끊김");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					}
				}
			});
			
			threadGettingMsg.start();
		}
		
		public void sendMessageToClient(String msg) {
			try {
				dos.writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void inMessageFromClient(String msg) {
			System.out.println("클라이언트로부터 메세지 : "+msg);
			
			StringTokenizer st = new StringTokenizer(msg, "/");
			
			String protocol = st.nextToken();
			String data = st.nextToken();
		}
		
		public void broadcast(String msg) {
			for(ClientInfo ui : clientVector) {
				ui.sendMessageToClient(msg);
			}
		}
	}
	
	//테스트용
	public static void main(String[] args) {
		new Server(9999);
	}
}
