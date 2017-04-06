
package echo.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EchoClient extends JFrame {
	
	JTextArea area;
	JScrollPane scroll;
	JPanel p_south;
	JTextField t_input;
	JButton bt_connect;
	
	//대화를 나눌 수 있는 소켓!
	//말걸 때 - 소켓으로부터 출력 스트림 뽑아내고! OutputStream
	//청취할 때 - 소켓으로부터 입력스트림 InputStream
	Socket socket;
	BufferedReader buffr; //청취용!
	BufferedWriter buffw; //말걸기용
	
	public EchoClient(){
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_south = new JPanel();
		t_input = new JTextField(15);
		bt_connect = new JButton("연결");
		
		p_south.add(t_input);
		p_south.add(bt_connect);
		
		add(scroll);
		add(p_south, BorderLayout.SOUTH);
		
		//내부익명으로 가보자!
		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();				
			}
		});
		
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER) {
					//클라이언트는 말하고 들어야하므로
					send();
				}
			}
		});
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//서버에 말 보내기
	public void send() {
		//말보내려면 출력! 소켓으로부터 출력 을 뽑자
		//소켓이 메모리에 올라오는 경우는? ㅉ접속버튼눌렀을 때 -> 이때 가만히 있지말고 입력, 출력 스트림 뽑아두자
		//텍스트 상자의 메세지 값 얻이
		String msg = t_input.getText();
		
		try {
			buffw.write(msg+"\n"); //스트림을 통해 출력!!
			//즉 서버측의 소켓에 데이터 전송!
			//buffw.write(msg); 지금 이렇게 하면 안된다. 한줄의 끝을 알려주지 않았으므로,,계속 기다리고 있다.
			//buffer라는 것의 의미는 줄의 끝이 있어야 한다.
			//buffer에 남아있을지도 모를 데이터를 대상으로 모두 출력시킨다.
			//물 확 비어놓듯이 buffrer에도 같이 써줘야함
			buffw.flush();
			
			//서버에서 날아온 메세지를 area에 출력해보자!!
			String data = buffr.readLine();
			area.append(data+"\n");
			t_input.setText("");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//서버에 접속하는 메서드!!.
	//Socket(String host, int port) 
	//host : 인터넷에 연결되어있는 모든 서버 - 주소
	public void connect() {
		try {
			socket = new Socket("localhost", 7777);
			//접속이 완료되는 시점 입력, 출력 스트림얻어놓자
			//왜 ? 대화 나누려고!
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new EchoClient();
	}
}
