package FindTheStudent;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.util.Scanner;


	public class ClientPart{
		
		public static boolean mode = true ; 
		
		public static void main(String[] args){
			
			
			
			Thread CW = new Thread(new ClientWindow());
			CW.start();
									
			Thread  feedBack = new Thread(new StudentFeedBackWindow());
			
			while(mode){
				System.out.print("");
			}
			JOptionPane.showMessageDialog(null,"點名成功");
			CW.interrupt();		
			feedBack.start();
			}	

	}
			
		
	
		
	class ClientWindow extends JFrame implements ActionListener , Runnable {
		
		static final int Width=300;
		static final int Height=150;
		private final int small_Width=200;
		private final int small_Height=100;
		
		private JTextField input;
		ClientWindow attendenceWindow ;
		//inner class of "ClientWindow"
		private class CheckOnExit implements WindowListener{
			public void windowOpened(WindowEvent e){}
			
			public void windowClosing(WindowEvent e){
				ConfirmationWindow checkers = new ConfirmationWindow();
				checkers.setVisible(true);
			}

			
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
		}//here is the end of the inner class
		
		public ClientWindow(){
			
			 setTitle("Java Attendence System");
			 setSize(Width, Height);
			 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			 addWindowListener(new CheckOnExit());
			 setLayout(new BorderLayout());
			 
			 JPanel JLabelPanel=new JPanel();
			 JLabelPanel.setLayout(new FlowLayout());
			 JLabel Label=new JLabel("Input Your Student ID here");
			 Label.setForeground(Color.BLUE);
			 JLabelPanel.add(Label);
			 add(JLabelPanel, BorderLayout.NORTH);
			 
			 JPanel JTextPanel=new JPanel();
			 JTextPanel.setLayout(new FlowLayout());
			 input=new JTextField(18);
			 input.setSize(input.getPreferredSize());
			 JTextPanel.add(input);
			 add(JTextPanel,BorderLayout.CENTER);
			 
			 JPanel Jbutton=new JPanel();
			 Jbutton.setLayout(new FlowLayout());
			 JButton ok=new JButton("OK");
			 ok.addActionListener(this);
			 Jbutton.add(ok);
			 add(Jbutton,BorderLayout.SOUTH);	
			 
		}
		
		//inner class of "ClientWindow"
		private class ConfirmationWindow extends JFrame implements ActionListener{
			public ConfirmationWindow()
			{
			
			setTitle("Attention");
			setSize(small_Width,small_Height);
			setLayout(new BorderLayout());
			JPanel LabelPanel=new JPanel();
			LabelPanel.setLayout(new FlowLayout());
			JLabel confirmationLabel=new JLabel("Are you sure to exit?");
			confirmationLabel.setForeground(Color.BLUE);
			LabelPanel.add(confirmationLabel);
			add(LabelPanel,BorderLayout.NORTH);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			JButton exitB=new JButton("Yes");
			exitB.addActionListener(this);
			buttonPanel.add(exitB);
			
			JButton cancelB=new JButton("No");
			cancelB.addActionListener(this);
			buttonPanel.add(cancelB);
			
			add(buttonPanel,BorderLayout.SOUTH);
			}
			
			public void actionPerformed(ActionEvent e) {
				String actionCommand = e.getActionCommand();
				
				if(actionCommand.equals("Yes"))
				System.exit(0);
				else if(actionCommand.equals("No"))
					dispose();
				else
					System.out.println("Unexpected in the ConfirmationWindow");
			}
			}//here is the end of the inner class
		
		
		public void actionPerformed(ActionEvent a){
			
			
			
			System.out.println(input.getText().toUpperCase()+" is your student ID");
			
			
			Internet sendStudentNumber = new Internet("192.168.208.143",8000);
			
			sendStudentNumber.sendMessage(input.getText().toUpperCase()+"\n");//never forget "\n" when using the method writeBytes
			
			String replyFromTeacher = sendStudentNumber.receiveMessage();
			System.out.println(replyFromTeacher);
			
			if(replyFromTeacher.equals("I get you")){
					
			input.setText("Succeed ! !");
			ClientPart.mode = false ;
			
			sendStudentNumber.close();
			
			}
			else {
				input.setText("fale ");
				sendStudentNumber.close();
			}
			//a simple test is below		
			/*try{
			Socket connectionSocket = new Socket("127.0.0.1",8000);
			DataOutputStream output = new DataOutputStream(connectionSocket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			output.writeBytes(input.getText()+"\n");
			
			System.out.println("before");
			String replyFromTeacher = in.readLine();
			System.out.println("after");
			input.setText(replyFromTeacher);
			
			output.close();
			in.close();
			connectionSocket.close();
			
			}catch(Exception e){System.out.println("here 96");}*/
			
			
			
		}
			
			
			

			
			
		
		public void run(){
				
			attendenceWindow = new ClientWindow();
			attendenceWindow.setVisible(true);
			
			//implement stop() method 
			try{
			while(true){Thread.sleep(1000);}
			}catch(Exception e){
			attendenceWindow.setVisible(false);	
			}
			}


			
			
		}
		
		
	class StudentFeedBackWindow extends JFrame implements ActionListener , Runnable , ItemListener{
		
		static final int Width=600;
		static final int Height=400;
		JCheckBox ch01=new JCheckBox("1");
		JCheckBox ch02=new JCheckBox("2");
		JCheckBox ch03=new JCheckBox("3");
		JCheckBox ch04=new JCheckBox("4");
		JCheckBox ch05=new JCheckBox("5");
		JButton OK = new JButton("ok");
		private String score = "0";// if student selects nothing , the default score would be "0"
		
		public StudentFeedBackWindow(){
			setSize(Width, Height);
			setLayout(null);
			JPanel panel = new JPanel();
			
			panel.setLocation(145, 140);
			panel.setSize(300,30);
			panel.setLayout(new FlowLayout());
			add(panel);
			panel.add(ch01);
			panel.add(ch02);
			panel.add(ch03);
			panel.add(ch04);
			panel.add(ch05);
			
			add(OK);
			OK.setLocation(255,180);
			OK.setSize(60,40);
			OK.addActionListener(this);
			
			ch01.addItemListener(this);
			ch02.addItemListener(this);
			ch03.addItemListener(this);
			ch04.addItemListener(this);
			ch05.addItemListener(this);
			//setContentPane(contentArea);
			
			ButtonGroup chkbox=new ButtonGroup();
			chkbox.add(ch01);
			chkbox.add(ch02);
			chkbox.add(ch03);
			chkbox.add(ch04);
			chkbox.add(ch05);
			
			
			
			JLabel jLabel01=new JLabel("Please score this class part from 1 to 5.");
			jLabel01.setLocation(180, 50);
			jLabel01.setSize(300,50);
			add(jLabel01);}
			
		
		public void actionPerformed(ActionEvent a){
			
//			System.out.println("you give teacher "+score+" point");
			Internet scoring = new Internet("192.168.208.143",8000); 
			scoring.sendMessage(score+"\n");
			String succeed = scoring.receiveMessage();
			if(succeed.equals("Successful submit"))
				JOptionPane.showMessageDialog(null,"回饋提交成功");
			scoring.close();
					
		}
		
		public void itemStateChanged(ItemEvent e) {
			
			score = e.paramString();
			score = score.substring(score.length()-23,score.length()-22);
		}
		
		public void run(){
			
		
		StudentFeedBackWindow feedBack = new StudentFeedBackWindow();
		feedBack.setVisible(true);}
		
		
			
			
		
		
	}



	class Internet{

		private Socket connectionSocket ;
		private DataOutputStream output ;
		private BufferedReader input ;
		
		public Internet(String IP,int port){

			try{
			connectionSocket = new Socket(IP,port);}catch(Exception e){}

		}

		public void sendMessage(String content){

			try{
				output = new DataOutputStream(connectionSocket.getOutputStream());

				output.writeBytes(content);

				/*output.close()*/;}catch(Exception e){System.out.println("sendMessage failed"); }

		}

		public String receiveMessage(){

			try{
				input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				
				String message = input.readLine();
				
				/*input.close();*/
				return message;
				
				}catch(Exception e){ return "receiveMessage failed";}

		}

		public void close(){
			
			try{
			
			connectionSocket.close();
			output.close();
			input.close();
			
			}catch(Exception e){}
			
		}
	

 





} 

 


 


 