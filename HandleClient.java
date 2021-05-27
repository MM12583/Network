package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandleClient extends Thread {
	
	private Socket socket ;
	
	// 連接Client端
	public HandleClient(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			
			DataInputStream inputFromClient = new DataInputStream(socket.getInputStream()) ;
			DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream()) ;
			
			while(true){
				
				String clientMessage = inputFromClient.readUTF() ;
				System.out.println("客戶端訊息 : " + clientMessage) ;
				
				String toClientMessage = defineClass(clientMessage) ;
				
				outputToClient.writeUTF(toClientMessage) ;
				System.out.println("已傳送訊息給客戶端") ;
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 評價方式
	public static String defineClass(String message){
		
		boolean isNumber = message.matches("\\d+") ;
		String returnMessage = "" ;
		
		if (isNumber) {
			double score = Double.parseDouble(message);
			int level = (int) (score/20) ;
			
			if (score > 100 || score < 0) {
				returnMessage = "The number your enter is over limit.";
			}else {
				switch (level) {
					case 1:
					case 2:
					case 3:
						returnMessage = "Fail" ;
						break;
					case 4:
						returnMessage = "Good" ;
						break;
					case 5:
						returnMessage = "Perfect" ;
						break;
				}
			}
			
		}else {
			returnMessage = ("Please Enter number.");
		}
		
		return returnMessage ;
		
	}
	
}
