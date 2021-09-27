package communications;

import java.net.*;
import java.util.Scanner;

import algorithms.FastestPath;
import algorithms.VisitNode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import entities.Robot;
import entities.Map;
import entities.Coordinate;

class client {
	public static Robot realrobot;
	public static Map testmap;
	public static FastestPath fp;
    public static VisitNode vn;
	public static void main(String args[]) throws Exception {
		realrobot = new Robot(new Coordinate(1,1),2, true);
		boolean stop;
		testmap = new Map("simulationtest.txt");
		while(true){     
			try {
			stop = true;	
			System.out.println("Connecting");	
			String str2 = "test";
			
			Socket s=new Socket("192.168.15.15", 5001);  // 5001 port number
			
			Scanner myObj = new Scanner(System.in);  // Create a Scanner object
			
			BufferedReader handshake = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));
			System.out.println("Reading Line...");
			//str2 = handshake.readLine();
			str2 = "hi";
			System.out.println("Getting Line...");
			System.out.println("Message from Server : " + str2); // Receive from Android ( ADD, UID, (X, Y)) FOR OBSTACLE
                        // OR if add wrongly then remove with (SUB, UID, (X, Y))
			
			// ADD FACE (FACE, UID, DIR)
			while (str2 == null)
			{
				handshake.close();
				break;

			}
			MoveThread thread = new MoveThread();
			String msg2 = thread.AndroidString();
		    System.out.println("Enter Message to client  : "); // Replace this line with algo output
                    // Send intial position (ROBOT, X, Y, DIR), obtained from Robot class
                    // Then send commands e.g. turn right = tr, turn left = tl, forward = f, reverse = r
		    String msg3 = thread.STMString();
		    output.write(msg2);
		    output.flush();
		
		    // Send to STM, 'STM, test' commands: w forward, a left, d right, x backward, s stop
		    // Send to Android, 'AD, test'
		     
		    output.close();  
			
			s.close(); } 
			catch(Exception e)
			{System.out.println("Could not connect. Trying again...");
				continue;}
			}
	}
	}
		//catch(Exception e){System.out.println(e);