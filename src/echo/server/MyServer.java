/*
�ڹٸ� �̿��Ͽ� ������ ���α׷��� �ۼ��Ѵ�.
��ȭ���� ������ ���� ��ġ!
���� : ��ȭ �޴� ����, ������ Ŭ���̾�Ʈ�� ã�ƿ��⸦ ��ٸ��Ƿ�
Ŭ���̾�Ʈ�� ����� ��Ʈ��ȣ�� �����ϸ� �ȴ�.

��Ģ : ��Ʈ��ȣ�� �����Ӱ� ���� �� �ִ�.
����1. 0~1012�� �̹� �ý����� �����ϰ� �ִ� port�̹Ƿ� ���Ѵ�.
����2. ������ ���α׷����� ������ ex)Oracle 1521, mysql 3306, web 80
ServerSocket(int port) 

���� : Ŭ���̾�Ʈ�� ������ �� �ͱ�￩ ���� �Ѵ�. � �޼��� ���?
accept() 
Listens for a connection to be made to this socket and accepts it.

ä�� : ��ǲ�� �ƿ�ǲ�� ��ȯ�۾�!

Ŭ���̾�Ʈ : ��ȭ �Ŵ� ����
 */
package echo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	//��ȭ�� ������ ���� ������ �˷��ֱ� ���� ��ü!!
	//�� ���� ��ȭ�� ��������
	ServerSocket server;
	int port =8888;
	//��ȭ���� ���� �� �ִ� ������ ����°�ó�� -> �����ſ��� socket�� ������ �ȴ�.
	Socket socket;
	
	
	public MyServer(){
		try {
			
			server = new ServerSocket(port);
			System.out.println("��������");
			
			/*
			Ŭ���̾�Ʈ�� ������ ��ٸ���
			������ ���������� ���Ѵ�� �� ������ ������.
			��ġ ��Ʈ�� read() �迭�� ���� ����. ��?????????????????
			�����Ͱ� �������� Ǯ���� ���Ѵ�⿡ �����Ƿ�
			*/
			while(true){
				socket = server.accept();
				System.out.println("������ �߰�!!");
			/*
			 ������ �̿��� �����͸� �ް����ϴ� ��쿣 �Է½�Ʈ����
			 �����͸� ���������ϴ� ��쿣 ��� ��Ʈ��!
			*/
				InputStream is= socket.getInputStream(); //byte����� �Է½�Ʈ�� 1byte�� �о�帰��.
				//�Ѵܰ� ���� ���� = Reader�� ���ٴ� �ǹ�
				InputStreamReader reader=null;
				reader = new InputStreamReader(is, "utf-8"); //reader�� �ܵ����� ��ư� �� ���� ��ƸԾ�� �Ѵ�.			
				
				int data;
				
				//2. 1byte�� �����Ƿ� �����۾�����������?????????
				//���ѷ��� �ʿ�!
				while(true) {
					//data = is.read(); //1. 1byte �о����
					//System.out.print((char)data); //data�� ascii�� ���̹Ƿ� char�� ����ȯ
					//�ڳ��� ��ȭ���� �ƴϹǷ� �����ļ� �����°�x �׳� ĥ������ ����
					//3. �ѱ��� �����Ƿ� ���׷��̵� (���� �����)
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
