# OpenSource-TeamProject
오픈소스 프로젝트 - 꾸러미동산

Java version : jre-16

Server.java
  JoinWaitRoom1 프로토콜에 2명의 클라이언트 접속-> 각 클라이언트에게 SendRoomName+(방 이름) 전송 and
  첫 번째 접속한 클라이언트에겐 ShowGame1/CreateGame 나중에 접속한 클라이언트에겐 ShowGame1/GetGame 전송

Client.java
  -첫 번째 접속한 클라이언트
    플레이어의 게임을 같은 그림 찾기로 설정(setPlayGameNum(1))
    플레이어의 상태를 true로 변경 
    
  -두 번째 접속한 클라이언트
    플레이어의 게임을 같은 그림 찾기로 설정(setPlayGameNum(1))
    플레이어의 상태를 false로 변경
   
MenuPanel.java
  waitroom 패널로 변경 및 생성자 실행

Waitroom.java
  loadText에서 player의 PlayGameNum이 0이 아니라면, 해당 게임 실행 (대기실 무한루프 탈출)
  FirstMinigamePanel 패널로 변경 및 생성자 실행

FirstMinigamePanel.java
  생성자
    버튼 생성
    addLayout 함수 실행
    
  addLayout()
    값 배열 0으로 초기화
    if 현재 객체가 첫 번째 접속한 클라이언트면, 
      initChar()
    else if 현재 객체가 두 번째 접속한 클라이언트면, 
      0.5초 대기(첫 번째 클라이언트가 게임을 생성하고, 서버에 등록할 시간을 버는 것)
      서버에 메세지 보냄(SetGame)
      서버에 메세지 보냄(StartGame)
