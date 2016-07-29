package FindTheStudent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.text.DecimalFormat;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Frame extends JFrame implements ActionListener
{
		public static ArrayList<String> studentList;
		public static String studentListLineWrap = "";

		private JButton btn;
		private JButton btnMode;
		private JButton btnReset;
		private static JLabel lb;
		private JScrollPane scrolledText;
		public static JTextArea studentListInput;
		
		private static double point = 0;
		private static int howmanytime = 0;
		private static String iDScore = "";
		
		public static int runth = 0;  //第幾次點名
		
		public static void findToday(){
			Date today = new Date();
			SimpleDateFormat todayFormat = new SimpleDateFormat("MM/dd");
			
			String dateLine = studentList.get(0);
			while (dateLine.contains("/")){
				dateLine = dateLine.substring(dateLine.indexOf("/")+1);
				runth = runth+1;
			}
			
			if(studentList.get(0).contains(todayFormat.format(today))){
				System.out.println("You had open this System today");
			}
			else{
				runth = runth + 1;
				studentList.set(0,studentList.get(0)+todayFormat.format(today)+" 　　");
				for (int i = 1 ; i < studentList.size(); i++){
					studentList.set(i,studentList.get(i)+"＿　　　　");
				}
			}
		}
		
		public static String renamed(String studentID){  //得知哪位學生須被點名,第幾次點名
			if(studentID.equals("1") ||studentID.equals("2")||studentID.equals("3")||studentID.equals("4")||studentID.equals("5")){
				return "We haven't begin Mode2";
			}
			else if (studentID.equals("0")){
				return "You can't give 0 point";
			}
			else if (studentID.equals("")){
				return "We can't find you";
			}
			
			for(int i = 0 ; i <studentList.size();i++){
				if(studentList.get(i).contains(studentID)){
					int index = studentList.get(i).indexOf(studentID);
					int replaceIndex = studentList.get(i).indexOf("＿",index+13-1+5*runth);
					String temp = studentList.get(i).substring(0,replaceIndex)+"v　"+studentList.get(i).substring(replaceIndex+1);
					studentList.set(i,temp);
					
					int j = 0;
					studentListLineWrap = "";
					for(int x=0; x<studentList.size(); x++)
					{
						studentListLineWrap = studentListLineWrap.concat(studentList.get(j));
						j++;
						if(j >= studentList.size())
							break;
						studentListLineWrap = studentListLineWrap.concat("\n");
					}
					
					textAreaReflesh();
					return "I get you";
				}
			}
			return "We can't find you";

		}
		public static void pointit(String inputPoint){
			point = point +Double.valueOf(inputPoint);
			howmanytime = howmanytime+1;
			iDScore = iDScore +inputPoint+"\n";
			double avgPoint=point/howmanytime;
			DecimalFormat pattern0dot000 = new DecimalFormat("0.000");
			lb.setText("The Average Point is "+ pattern0dot000.format(avgPoint) +" point");
			studentListInput.setText(iDScore);
			
		}
		
		private static void textAreaReflesh(){
			studentListInput.setText(studentListLineWrap);
		}

		
		public Frame()
		{
			try
			{
				studentList = new ArrayList<String>();
				
				Scanner scannerin = new Scanner(new FileInputStream("C:\\Users\\Cheng Yu Da\\Dropbox\\成功大學\\JAVA\\點名評分系統\\test.txt"));	
				
				while(scannerin.hasNextLine())
				{
					studentList.add(scannerin.nextLine());
				}
				
				findToday();
				
				scannerin.close();
				
				int i = 0;
				for(int x=0; x<studentList.size(); x++)
				{
					studentListLineWrap = studentListLineWrap.concat(studentList.get(i));
					i++;
					if(i >= studentList.size())
						break;
					studentListLineWrap = studentListLineWrap.concat("\n");
				}
				

				
				setTitle("Roll-Call System");
				setSize(800, 600);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLayout(null);
				
				btn = new JButton("Save");
				btn.addActionListener(this);
				btn.setLocation(275, 480);
				btn.setSize(100, 50);
				add(btn);
				
				btnMode = new JButton("Change Mode");
				btnMode.addActionListener(this);
				btnMode.setLocation(425,480);
				btnMode.setSize(200,50);
				add(btnMode);
				
				btnReset = new JButton("Reset");
				btnReset.setVisible(false);
				btnReset.addActionListener(this);
				btnReset.setLocation(325,480);
				btnReset.setSize(200,50);
				add(btnReset);
				
				lb = new JLabel("Please enter your StudentID in your computer.");
				lb.setLocation(270,40);
				lb.setSize(300,20);
				add(lb);
				
				studentListInput = new JTextArea(20,50);
				studentListInput.setText(studentListLineWrap.toString());
				studentListInput.setLineWrap(true);
				scrolledText = new JScrollPane(studentListInput);
				scrolledText.setLocation(125, 100);
				scrolledText.setSize(studentListInput.getPreferredSize());
				scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				add(scrolledText);
				

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			

		}
		
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Save"))
			{
				try {
					PrintWriter writer = new PrintWriter(new FileOutputStream("C:\\Users\\Cheng Yu Da\\Dropbox\\成功大學\\JAVA\\點名評分系統\\test.txt"));
					
					for(int i=0; i<studentList.size(); i++)
					{
						writer.println(studentList.get(i));
					}
					System.out.println("Saved");
					writer.flush();
					writer.close();
					
				} catch (FileNotFoundException a) {
					a.printStackTrace();
				}
				
//				System.out.println("FUCKYUDA");
			}
			else if (e.getActionCommand().equals("Change Mode")){
				Server.serverMode = false ;
				btn.setVisible(false);
				btnReset.setVisible(true);
				btnMode.setVisible(false);
				lb.setSize(600,20);
				lb.setLocation(250,40);
				lb.setFont(new Font("標楷體", Font.BOLD, 20));
				lb.setText("The Average Point is 0.0 point" );
				studentListInput.setText("");
			}
			else if (e.getActionCommand().equals("Reset")){
				point = 0.0;
				iDScore = "";
				howmanytime = 0;
				lb.setText("The Average Point is "+ point +" point");
				studentListInput.setText(iDScore);
			}
		}
		
	}


