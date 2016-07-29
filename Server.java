package FindTheStudent;

import java.net.*;
import java.io.*;

public class Server {
	public static boolean serverMode = true;
	
	public static void main(String[] args){
		
		try{
			try{
				InetAddress ip = InetAddress.getLocalHost();
				System.out.println(ip);
			}
			catch(Exception e){
				System.out.println("Can't find \"IP\"");
			}
			
			ServerSocket serverSock = new ServerSocket(8000);
			System.out.println("Server started...");
			
			Frame mode0 = new Frame();
			mode0.setVisible(true);
			while(serverMode){
				Socket cSock = serverSock.accept();
				DoSomeThing thing = new DoSomeThing(cSock);
				Thread thingThread = new Thread(thing);
				thingThread.start();
			}
			
			while(!serverMode){
				Socket cSock = serverSock.accept();
				LetusChat chat = new LetusChat(cSock);
				Thread chatThread = new Thread(chat);
				chatThread.start();
			}
			serverSock.close();
		}
		catch(IOException e){
			System.out.println("Disconnetcted...");
		}
	}
}
class DoSomeThing implements Runnable{
	private Socket socket;
	public DoSomeThing(Socket socket){
		this.socket = socket;
	}
	public void run(){
		try{
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());
						
			String clientText = clientInput.readLine();
			
			String replyText = Frame.renamed(clientText);
			
			try{
				clientOutput.writeBytes(replyText+"\n");
			}
			catch(Exception e){
				System.out.println("Somebody try to fuck up");
			}
			
			if(replyText.equals("I get you")){
				System.out.println("I get the StudentID: " + clientText );
				
			}
			clientOutput.close();
			clientInput.close();
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}

class LetusChat implements Runnable{
	private Socket socket;
	public LetusChat(Socket socket){
		this.socket = socket;
	}
	public void run(){
		try{
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());
			
			String clientText = clientInput.readLine();
			
			Frame.pointit(clientText);

			String replyText = "Successful submit";
			clientOutput.writeBytes(replyText+"\n");
			
			clientOutput.close();
			clientInput.close();
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}

