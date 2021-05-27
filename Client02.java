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
					
			System.out.println("�s�����A��IP : " + socket.getInetAddress() + " �� : " + socket.getPort());
			
			DataInputStream inputFromSever = new DataInputStream(socket.getInputStream()) ;
			DataOutputStream outputToSever = new DataOutputStream(socket.getOutputStream()) ;
			
			while(true){
				
				Scanner input = new Scanner(System.in) ;
				
				System.out.println("��J�����A���T��(�������� : 1~100 )") ;
				String toServerMessage = input.nextLine() ;
				outputToSever.writeUTF(toServerMessage) ;
				System.out.println("�w�ǰe�T�������A��") ;
				
				String serverMessage = inputFromSever.readUTF() ;
				System.out.println("�w����^�_�T��") ;
				System.out.println("���A���T�� : " + serverMessage) ;
				
//				input.close(); // �������J
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		
	}

}
