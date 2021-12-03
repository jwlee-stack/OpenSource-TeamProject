# OpenSource-TeamProject
오픈소스 프로젝트 - 꾸러미동산

Java version : jre-16

★ 클래스 및 메소드마다 설명 추가

# 작동 방식(클라이언트)
- Main 클래스 실행 -> GameFrame 생성 -> PanelHandler 생성 -> LoginPanel, MenuPanel 생성 -> LoginPanel 화면이 보이도록 설정
- 로그인 버튼 클릭 시, GameFrame에 존재하는 Client를 사용하여 서버 연결 시도. 성공시, MenuPanel로 화면 전환 및 서버로 'Login/ ' 메세지 전송
- MenuPanel에서 게임 버튼(같은 그림 찾기, 1) 클릭 시, WaitRoomPanel으로 화면 전환 및 서버로 'JoinWaitRoom1/(닉네임)' 메세지 전송

# 특징(클라이언트)
- 서버로부터 받은 메세지는 모두 Client 클래스에서 처리한다.
- 클라이언트의 Player정보를 변경시키며 실행 흐름을 처리한다.
