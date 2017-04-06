/*
에코 프로그램이란?
클라이언트의 메세지를 그대로 다시 전달하는 방식의 서버!!
채팅 기초 1단계

--------------------------------
한번 엔터치고 또 치려면 오류가 난다
서버가 무한반복되도록해야한다.

 */

package echo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	ServerSocket server;
	
	int port = 7777;
	
	public EchoServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("서버 생성!!");
			
			Socket socket = server.accept(); //접속자가 있을때까지 무한대기!!
			
			//IP를 찾자 접속정보는 socket이 가지고 있다 socket.get..
			InetAddress inet = socket.getInetAddress();
			String ip = inet.getHostAddress();
			
			//아무리 밑에 sysout 친다해도 항상 보이란 보장x
			System.out.println(ip+"님 발견");
			
			//클라이언트의 데이터를 받기 위해 입력 스트림 얻기!
			//byte --> 문자 --> 버퍼기반
			//3단계 한줄로가기!
			BufferedReader buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter buffw= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			
			//클라이언트의 말 듣기!!
			String msg;
			
			//대화는 계속 가능하지만 실행부가 아래의 while문 안에 갇혀있으므로
			//더이상 추가 접속자에 대한 접속 허용은 불가하다.
			//결론 : 1인용 서버가 되었다. (최초 가장빨리 들어온 사람용)
			
			while(true) {
				msg = buffr.readLine(); //클라이언트의 한줄의 말이 들어있음!
				System.out.println("클라이언트가 보낸 말:"+msg); //클라이언트로가서 말할 수 있도록 개선시키자
				//받은 메세지 다시 보내기
				buffw.write(msg+"\n");
				buffw.flush();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoServer();
	}
}
