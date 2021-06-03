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
					
					// 選擇服務項目
					RequestType2 choiceService = choiceService();
					// 發送服務類型
					RequestPackage2 request = new RequestPackage2(choiceService) ;
					outputToSeverO.writeObject(request);
					System.out.println("Client : 已發送選擇之服務項目" + request);
					
					// 兩種服務
					if (choiceService == RequestType2.DEFINE_SCORE_CLASS) {
						
						System.out.println("輸入給伺服器訊息(評價分數 : 1~100 )");
						String toServerMessage = input.nextLine();
						outputToSever.writeUTF(toServerMessage);
						System.out.println("Client : 已傳送訊息給伺服器");
						
						String serverMessage = inputFromSever.readUTF() ;
						System.out.println("Client : 已收到回復訊息");
						System.out.println("Client : 受到伺服器訊息 : " + serverMessage);
						
					} else if (choiceService == RequestType2.ASK_FOR_SEVERDATA) {
						// read ServerData
						try {
							UserData serverData = (UserData) inputFromSeverO.readObject();
							System.out.println("Client : 已接收ServerData");
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
			System.out.println("請選擇服務項目 : 1.分數評鑑 2. 取得Server(聊天對象訊息)");
			int n = input.nextInt() ;
			if (n == 2) {
				choice = RequestType2.ASK_FOR_SEVERDATA ;
			}
			
		} catch (Exception e) {
			System.out.println("請輸入 1 或 2");
		}
		
		return choice ;
	}
}


