package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client02 {

	public static void main(String[] args) {
		
		try {
			startClient() ;
		} catch (SocketException e) {
			e.getMessage() ;
		}
	}
	
	public static void startClient() throws SocketException{
		
		try	{
			
			Socket socket = new Socket("localhost",52020) ;
					
			System.out.println("連接伺服器IP : " + socket.getInetAddress() + " 埠號 : " + socket.getPort());
			
			DataInputStream inputFromSever = new DataInputStream(socket.getInputStream()) ;
			DataOutputStream outputToSever = new DataOutputStream(socket.getOutputStream()) ;
			
			while(true){
				
				Scanner input = new Scanner(System.in) ;
				
				System.out.println("輸入給伺服器訊息(評價分數 : 1~100 )") ;
				String toServerMessage = input.nextLine() ;
				outputToSever.writeUTF(toServerMessage) ;
				System.out.println("已傳送訊息給伺服器") ;
				
				String serverMessage = inputFromSever.readUTF() ;
				System.out.println("已收到回復訊息") ;
				System.out.println("伺服器訊息 : " + serverMessage) ;
				
//				input.close(); // 中止不讓輸入
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		
	}

}
