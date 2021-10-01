package communications;

import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

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
		realrobot = new Robot(new Coordinate(1, 1), Robot.EAST, true);
		boolean stop = false;
		testmap = new Map("simulationtest.txt");

		Scanner myObj = new Scanner(System.in);

		Socket s = new Socket("192.168.15.15", 5001); // port 5001

		while (!stop) {
			try {
				System.out.println("Connecting");
				String receive = "";

				BufferedReader handshake = new BufferedReader(
						new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
				BufferedWriter output = new BufferedWriter(
						new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));

				while (receive != "stop") {
					System.out.println("Reading Line...");
					// receive = handshake.readLine();
					receive = "hi";
					System.out.println("Message from Server : " + receive);
					String receiveList[] = receive.split(",");

					MoveThread thread = new MoveThread();
					ArrayList<String> sendSTM = thread.STMString();
					for (int i = 0; i < sendSTM.size(); i++) {
						String sendS = sendSTM.get(i).replace("wx","");
						String sendSn = sendS.replace("wx","");
						String sendSnn = sendSn.replace("xw","");
						String sendS4 = sendSnn.replace("wwww","4");
						String sendS3 = sendS4.replace("www","3");
						String sendS2 = sendS3.replace("www","3");
						System.out.println(sendS2);
						receive = myObj.nextLine();
						output.write("STM:" + sendS2);
						output.flush();
					}

					switch (receiveList[0]) {
					case "ADD":
						testmap.setPictureCell(Integer.parseInt(receiveList[1]), Integer.parseInt(receiveList[2]), 'A');
						break;
					case "SUB":
						testmap.delPictureCell(Integer.parseInt(receiveList[1]), Integer.parseInt(receiveList[2]));
						break;
					case "FACE":
						testmap.setPictureCell(Integer.parseInt(receiveList[1]), Integer.parseInt(receiveList[2]),
								receiveList[3].charAt(0));
						break;
					case "START":
						// String msg2 = thread.AndroidString();
						// String msg3 = thread.STMString();
						// output.write(msg2);
						// output.flush();
						break;
					case "STOP":
						stop = true;
						output.close();
						s.close();
						myObj.close();
						break;
					default:
					}
				}
			} catch (Exception e) {
				System.out.println("Could not connect. Trying again...");
				continue;
			}
		}
	}

}
