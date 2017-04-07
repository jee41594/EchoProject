/*
자바를 이용하여 서버측 프로그램을 작성한다.
전화기의 원리랑 완전 일치!
서버 : 전화 받는 입장, 서버는 클라이언트가 찾아오기를 기다리므로
클라이언트와 약속한 포트번호만 보유하면 된다.

원칙 : 포트번호는 자유롭게 정할 수 있다.
예외1. 0~1012은 이미 시스템이 점유하고 있는 port이므로 피한다.
예외2. 유명한 프로그램들은 피하자 ex)Oracle 1521, mysql 3306, web 80
ServerSocket(int port) 

가동 : 클라이언트가 들어오는 걸 귀기울여 들어야 한다. 어떤 메서드 사용?
accept() 
Listens for a connection to be made to this socket and accepts it.

채팅 : 인풋과 아웃풋의 교환작업!

클라이언트 : 전화 거는 입장
 */
package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	//대화를 나누기 전에 접속을 알려주기 위한 객체!!
	//즉 아직 대화는 못나눈다
	ServerSocket server;
	int port =8888;
	//전화오면 받을 수 있는 종이컵 만드는것처럼 -> 종이컵에서 socket을 뽑으면 된다.
	Socket socket;
	
	
	public MyServer(){
		try {
			
			server = new ServerSocket(port);
			System.out.println("서버생성");
			
			/*
			클라이언트가 접속을 기다린다
			접속이 있을때까지 무한대기 즉 지연에 빠진다.
			마치 스트림 read() 계열과 거의 같다. 왜?????????????????
			데이터가 있을때만 풀리고 무한대기에 빠지므로
			*/
			while(true){
				socket = server.accept();
				System.out.println("접속자 발견!!");
			/*
			 소켓을 이용해 데이터를 받고자하는 경우엔 입력스트림을
			 데이터를 보내고자하는 경우엔 출력 스트림!
			*/
				InputStream is= socket.getInputStream(); //byte기반의 입력스트림 1byte씩 읽어드린다.
				//한단계 위로 간다 = Reader로 간다는 의미
				InputStreamReader reader=null;
				reader = new InputStreamReader(is, "utf-8"); //reader는 단독으로 살아갈 수 없고 잡아먹어야 한다.			
				
				int data;
				
				//2. 1byte씩 읽으므로 여러글씨를읽으려면?????????
				//무한루프 필요!
				while(true) {
					//data = is.read(); //1. 1byte 읽어들임
					//System.out.print((char)data); //data는 ascii로 보이므로 char로 형변환
					//텔넷은 대화용이 아니므로 엔터쳐서 나오는게x 그냥 칠떄마다 나옴
					//3. 한글은 깨지므로 업그레이드 (빨대 끼운다)
					data = reader.read();
					System.out.println((char)data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MyServer();
	}
}
