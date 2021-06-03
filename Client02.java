package network.muilple_thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.Scanner;

import netwok.RequestPackage;
import netwok.UserData;

public class Client02 {

	public static void main(String[] args) {
		try {
			startClient();
		} catch (SocketException e) {
			e.getMessage();
		}
	}

	public static void startClient() throws SocketException {

		try {
			Socket socket = new Socket("localhost", 52020);
			// Server info
			System.out.println("Connection Server IP : " + socket.getInetAddress() + 
					" Port : " + socket.getPort());
			try (
				DataInputStream inputFromSever = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToSever = new DataOutputStream(socket.getOutputStream());
				ObjectOutputStream outputToSeverO = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inputFromSeverO = new ObjectInputStream(socket.getInputStream());
				Scanner input = new Scanner(System.in);
				) {
				while (true) {
					
					// ��ܪA�ȶ���
					RequestType2 choiceService = choiceService();
					// �o�e�A������
					RequestPackage2 request = new RequestPackage2(choiceService) ;
					outputToSeverO.writeObject(request);
					System.out.println("Client : �w�o�e��ܤ��A�ȶ���" + request);
					
					// ��تA��
					if (choiceService == RequestType2.DEFINE_SCORE_CLASS) {
						
						System.out.println("��J�����A���T��(�������� : 1~100 )");
						String toServerMessage = input.nextLine();
						outputToSever.writeUTF(toServerMessage);
						System.out.println("Client : �w�ǰe�T�������A��");
						
						String serverMessage = inputFromSever.readUTF() ;
						System.out.println("Client : �w����^�_�T��");
						System.out.println("Client : ������A���T�� : " + serverMessage);
						
					} else if (choiceService == RequestType2.ASK_FOR_SEVERDATA) {
						// read ServerData
						try {
							UserData serverData = (UserData) inputFromSeverO.readObject();
							System.out.println("Client : �w����ServerData");
							System.out.println(serverData);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static RequestType2 choiceService(){
		
		RequestType2 choice = RequestType2.DEFINE_SCORE_CLASS ;
		
		try {
			Scanner input = new Scanner(System.in) ;
			System.out.println("�п�ܪA�ȶ��� : 1.���Ƶ�Ų 2. ���oServer(��ѹ�H�T��)");
			int n = input.nextInt() ;
			if (n == 2) {
				choice = RequestType2.ASK_FOR_SEVERDATA ;
			}
			
		} catch (Exception e) {
			System.out.println("�п�J 1 �� 2");
		}
		
		return choice ;
	}
}


