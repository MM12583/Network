package network.muilple_thread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//Server Thread
//use Client02 class
public class MulitpleThreadServer extends Thread {
	
	// define clientCount
	private int clientNO = 0 ;
	
	public static void main(String[] args) {
		MulitpleThreadServer mulitpleThreadServer = new MulitpleThreadServer() ;
		mulitpleThreadServer.start();
	}
	
	@Override
	public void run() { 
		
		try {	
			ServerSocket serverSocket = new ServerSocket(52020) ;
			System.out.println("Server IP : " + serverSocket.getInetAddress() + 
					" Port : " + serverSocket.getLocalPort()) ;
				
			// * wait for connection and create handle thread
			while(true){
				Socket socket = serverSocket.accept() ;
				
				// clientCount
				clientNO ++ ;
				System.out.println("Connected client number : " + clientNO);
				
				// Client info
				System.out.println("Server : 已經與 Client 連線: " 
						+ socket.getRemoteSocketAddress());
					
				// process Client request
				new Thread(new HandleClient(socket)).start() ;	
			}
		} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}


