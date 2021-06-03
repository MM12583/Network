package network.muilple_thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;

import netwok.UserData;

public class HandleClient extends Thread {
	
	private Socket socket ;
	
	// 連接Client端
	public HandleClient(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
			DataInputStream inputFromClient = new DataInputStream(socket.getInputStream()) ;
			DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream()) ;
			ObjectInputStream inputFromClientO = new ObjectInputStream(socket.getInputStream()) ;
			ObjectOutputStream outputToClientO = new ObjectOutputStream(socket.getOutputStream()) ;
			){	
			while(true){
				// Server Message
				UserData server = new UserData("MM12583", "Frank", "26", Date.valueOf("1994-09-25")) ;
				try {
					// 接收Client端選擇服務類型
					RequestPackage2 requestPackage2 = (RequestPackage2) inputFromClientO.readObject();
					System.out.println("Server : 已收到選擇之服務項目");
					// 決定提供哪項服務
					RequestType2 choiceService = requestPackage2.getType() ;
					
					if (choiceService == RequestType2.DEFINE_SCORE_CLASS) {
						
						String clientMessage = inputFromClient.readUTF() ;
						System.out.println("Server : 收到客戶端訊息 : " + clientMessage) ;
						String toClientMessage = defineClass(clientMessage) ;
						outputToClient.writeUTF(toClientMessage) ;
						System.out.println("Server : 已傳送訊息給客戶端") ;
						
					} else if (choiceService == RequestType2.ASK_FOR_SEVERDATA) {
						outputToClientO.writeObject(server);
						System.out.println("Server : 已傳送 ServerData 給客戶端");
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
					break ; // Client 切斷, 停止Thread
				}
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
			Integer score = Integer.valueOf(message);
			Integer level = (score/20) ;
			
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
					default :
						returnMessage = "Fail" ;
						break ;
				}
			}
		}else {
			returnMessage = ("Please Enter number.");
		}
		
		return returnMessage ;	
	}
}
