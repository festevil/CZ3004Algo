package communications;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import entities.Robot;
import entities.Map;
import entities.Coordinate;

class client {
	private static Robot realrobot;
	public static void main(String args[]) throws Exception {
		realrobot = new Robot(new Coordinate(1,1),2, true);
		boolean stop;
		
		while(true){     
			try {
			stop = true;	
				
			String str2 = "test";
			
			Socket s=new Socket("192.168.15.15", 5001);  // 5001 port number
			
			Scanner myObj = new Scanner(System.in);  // Create a Scanner object
			
			BufferedReader handshake = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));
			
			str2 = handshake.readLine();
			
			System.out.println("Message from Server : " + str2); // Receive from Android ( ADD, UID, (X, Y)) FOR OBSTACLE
                        // OR if add wrongly then remove with (SUB, UID, (X, Y))
			// ADD FACE (FACE, UID, DIR)
			while (str2 == null)
			{
				handshake.close();
				break;

			}
			
		    System.out.println("Enter Message to client  : "); // Replace this line with algo output
                    // Send intial position (ROBOT, X, Y, DIR), obtained from Robot class
                    // Then send commands e.g. turn right = tr
		    while (stop){
		    String msg = myObj.nextLine();  // Read
		    output.write(msg);  // Send to STM
		    if (msg == "STM, s") {
		    	stop = false;
		    }
		    output.flush(); 
		    }
		    // Send to STM, 'STM, test' commands: w forward, a left, d right, x backward, s stop
		    // Send to Android, 'AD, test'
		     
		    output.close();  
			
			s.close(); } 
			catch(Exception e)
			{continue;}
			}
	}
	}
		//catch(Exception e){System.out.println(e);