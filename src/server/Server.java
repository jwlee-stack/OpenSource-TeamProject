package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;




/**
 * 클라이언트와 소켓 통신을 하기 위한 클래스이다.
 * 
 * @author Ted
 *
 */
public class Server {
	private ServerSocket serverSocket;
	
	private Vector<ClientInfo> clientVector;
	private Vector<RoomInfo> roomVector;
	
	private Vector<ClientInfo> waitRoom1 = new Vector<ClientInfo>();
	private Vector<ClientInfo> waitRoom2 = new Vector<ClientInfo>();
	private Vector<ClientInfo> waitRoom3 = new Vector<ClientInfo>();
	private Vector<Result> resultVector = new Vector<Result>();
	
	private ClientInfo c1;
	private ClientInfo c2;
	private String roomName;
	
	class Result
	{
		String name;
		String score;
		Result()
		{
			name=null;
			score=null;
		}
	}
	
	
	int getsu;
	

	
	/**
	 * port 번호를 사용해 서버를 만들고 실행시킨다.
	 * 
	 * @param port
	 */
	public Server(int port) {
		this.clientVector = new Vector<ClientInfo>();
		this.roomVector = new Vector<>();
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
		private String nickname = "";
		private RoomInfo myroom;
		private int answer[][];
		
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
							if(this.myroom!=null) //게임 중인 방이 있음
							{
								ClientInfo another_c=AnotherClient();

								another_c.myroom=null;
								another_c.sendMessageToClient("GameOut/null"); //상대방도 게임 나가게 함

							}
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
		public void setRoomInfo(RoomInfo room)
		{
			this.myroom=room;
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
			
			if(protocol.equals("Login")) {
				this.nickname = data;
			}
			else if(protocol.equals("JoinWaitRoom1")) {
				//menupanel.java에서 넘어옴
				waitRoom1.add(this);
				if(waitRoom1.size() == 1)
				{	
					for(int i=0;i<clientVector.size();i++)
					{
						if(this.getNickname().equals(clientVector.elementAt(i).getNickname()))
						{
							c1 = clientVector.elementAt(i);
							roomName = makeRandString();
						}
					}
				}

				else if(waitRoom1.size() == 2) {
					
					for(int i=0;i<clientVector.size();i++)
					{
						if(this.getNickname().equals(clientVector.elementAt(i).getNickname()))
						{
							c2 = clientVector.elementAt(i);

						}
					}
					
					RoomInfo room = new RoomInfo(roomName, 1, c1, c2);
					roomVector.add(room);
					c1.setRoomInfo(room);
					c2.setRoomInfo(room);
					
					waitRoom1.remove(c1);
					waitRoom1.remove(c2);
					
					myroom.broadcast("SendRoomName/"+myroom.getRoomName());

					c1.sendMessageToClient("ShowGame1/CreateGame"); //c1의 status=true 설정
					c2.sendMessageToClient("ShowGame1/GetGame"); //c2의 status=false 설정
				}
			}

			else if(protocol.equals("makeArray"))
			{
				getsu=Integer.parseInt(data);
				this.answer = new int[getsu][getsu];
			}
			else if(protocol.equals("MakeGame")) {
				int row=Integer.parseInt(st.nextToken());
				int col=Integer.parseInt(st.nextToken());
				int alpha=Integer.parseInt(st.nextToken());
				this.answer[row][col]=alpha;
				
			}
			else if(protocol.equals("SetGame"))
			{

				ClientInfo first_c = AnotherClient(); //상대방 클라이언트 (첫 번째 클라이언트)
				for(int k=0;k<getsu;k++)
				{
					for(int j=0;j<getsu;j++)
					{
						this.myroom.broadcast("SetGame/"+Integer.toString(k)+"/"+Integer.toString(j)+"/"+Integer.toString(first_c.answer[k][j]));
									
					}
								
				}

				
			}
			else if(protocol.equals("StartGame"))
			{
				this.myroom.broadcast("StartGame/null");
			}
			else if(protocol.equals("ButtonClicked"))
			{
				String clicked=st.nextToken();
				System.out.println("눌린 버튼:"+clicked);

				myroom.broadcast("Chatting/"+nickname+"/"+clicked);

			}
			else if(protocol.equals("ChangePlayer"))
			{

				myroom.broadcast("ChangePlayer/null");

			}
			else if(protocol.equals("GameOver"))
			{	
				Result p=new Result();
				p.name=data;
				p.score=st.nextToken();
				resultVector.add(p);
				String winner;
				RoomInfo ri;
				System.out.println(resultVector.size());
				
				
				if(resultVector.size()==2) //두 플레이어의 값 다 받음
				{
					if(Integer.parseInt(resultVector.get(0).score)>Integer.parseInt(resultVector.get(1).score))			
					{
						
						winner=resultVector.get(0).name;
						System.out.println(winner+"가 이김1");
						
					}
					else
					{
						winner=resultVector.get(1).name;
						System.out.println(winner+"가 이김2");
						
					}
					for(int i=0;i<roomVector.size();i++) {
						ri = (RoomInfo)roomVector.elementAt(i);
						
						if(ri.roomName.equals(myroom.getRoomName())) {
							myroom.broadcast("gameover/"+winner+"/"+resultVector.get(0).score+"/"+resultVector.get(1).score);
							roomVector.remove(i);
							AnotherClient().myroom=null;
							myroom=null;
							resultVector.clear();
							
							break;
						}

											
					}
					
					
				}
			}
		}
		private ClientInfo AnotherClient()
		{
			ClientInfo another_c=null;
			for(int i=0;i<clientVector.size();i++)
			{
				if(this.myroom.equals(clientVector.elementAt(i).myroom)) //클라이언트들의 방 이름이 일치
				{
					if(!(this.getNickname().equals(clientVector.elementAt(i).getNickname()))) //방에 있는 클라이언트 중 내가 아님(상대방)
					{
						another_c = clientVector.elementAt(i); //상대방 클라이언트
						
					}
				}
			}
			return another_c;
		}
		
		//public void broadcast(String msg) {
			//for(ClientInfo ui : clientVector) {
				//ui.sendMessageToClient(msg);
			//}
		//}
		
		public String getNickname() {
			return this.nickname;
		}
		
		private String makeRandString()
		{
			int leftLimit = 48; // numeral '0'
			int rightLimit = 122; // letter 'z'
			int targetStringLength = 10;
			Random random = new Random();

			String generatedString = random.ints(leftLimit,rightLimit + 1)
			  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			  .limit(targetStringLength)
			  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			  .toString();

			System.out.println("방 이름:	"+generatedString);
			return generatedString;
		}
	}
	
	private class RoomInfo{
		private String roomName;
		private int gameType; // 1:같은그림찾기, 2:오목, 3:두더지잡기
		private ClientInfo c1;
		private ClientInfo c2;
		
		public RoomInfo(String roomName, int gameType, ClientInfo c1, ClientInfo c2) {
			this.roomName = roomName;
			this.gameType = gameType;
			this.c1 = c1;
			this.c2 = c2;
		}
		
		public void broadcast(String msg) {
			c1.sendMessageToClient(msg);
			c2.sendMessageToClient(msg);
		}
		
		public String getRoomName() {
			return this.roomName;
		}
		
	}
	
	//테스트용
	public static void main(String[] args) {
		new Server(9999);
	}
}
