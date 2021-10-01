package Main;

import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.nio.charset.StandardCharsets;

import entities.Cell;
import entities.Map;
import entities.Robot;
import entities.Robot.Rotate;

import gui.GUI;

import algorithms.VisitNode;
import algorithms.FastestPath;

public class Main {

    // Set Mode here
    public static boolean isRealRun = true;

    // Shared Variables
    public static Robot robot;
    public static Map curMap;
    public static GUI gui;
    public static FastestPath fp;
    public static VisitNode vn;

    // Simulation Only Variables
    public static Thread tSimExplore;
    public static void main(String[] args) throws Exception {
        // Initialize Variables & GUI
        robot = new Robot(isRealRun);       // Default starting position of robot (1,1 facing East)
        curMap = new Map();                 // Set map (starts from an unknown state)
        gui = new GUI(robot, curMap);
        HashMap<String, Character> cellDirMap = new HashMap<>();
        cellDirMap.put("N", 'A');
        cellDirMap.put("E", 'B');
        cellDirMap.put("S", 'C');
        cellDirMap.put("W", 'D');

        HashMap<String, Integer> robotDirMap = new HashMap<>();
        robotDirMap.put("N", 0);
        robotDirMap.put("E", 1);
        robotDirMap.put("S", 2);
        robotDirMap.put("W", 3);

        //Real run mode
        if (isRealRun) {
            try {
                boolean stop = false;

                Scanner myObj = new Scanner(System.in);

                Socket s = new Socket("192.168.15.15", 5001); // port 5001

                while (!stop) {
                    System.out.println("Connecting");
                    String receive = "";

                    BufferedReader handshake = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));

                    while (receive != "stop") {
                        //System.out.println("Reading Line...");
                        receive = handshake.readLine();
                        //receive = "hi";
                        System.out.println("Message from Server:" + receive);

                        while (receive == null) {
                            receive = handshake.readLine();
                        }
                        
                        String receiveList[] = receive.split(",");

                        switch (receiveList[0]) {
                            case "ADD":
                                curMap.setPictureCell(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]), Cell.NORTHWALL);
                                break;
                            case "SUB":
                                curMap.delPictureCell(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]));
                                break;
                            case "FACE":
                                curMap.setPictureCell(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]), cellDirMap.get(receiveList[3]));
                                break;
                            case "ROBOT":
                                robot.setCurPos(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]));
                                robot.setCurDir(robotDirMap.get(receiveList[3]).intValue());
                                break;
                            case "START":
                                RealMovementCalculation calculation = new RealMovementCalculation();
                                String sendSTM = calculation.STMString();
                                System.out.println(sendSTM);
                                output.write("STM:" + sendSTM);
                                output.flush();
                                break;
                            case "STOP":
                                stop = true;
                                output.close();
                                s.close();
                                myObj.close();
                                break;
                            default:
                        }
                        
                        gui.refreshGUI(robot, curMap);
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e);
                System.out.println("Could not connect. Now switching to simulation mode instead.");
                curMap = new Map("simulationtest.txt");
                gui.refreshGUI(robot, curMap);
            }
        }

        //Simulation mode
        else {
            curMap = new Map("simulationtest.txt");
            gui.refreshGUI(robot, curMap);
        }
    }

    //Simulation only.
    public static void runForwardStep() {
        robot.moveForward(1);
        gui.refreshGUI(robot, curMap);
    }

    //Simulation only.
    public static void rotateleft() {
        robot.rotate(Rotate.LEFT);
        gui.refreshGUI(robot, curMap);
    }

    //Simulation only.
    public static void rotateright() {
        robot.rotate(Rotate.RIGHT);
        gui.refreshGUI(robot, curMap);
    }

    //Simulation only.
    public static void runShowFastestPath() {
        if (tSimExplore == null || !tSimExplore.isAlive()) {
            tSimExplore = new Thread(new SimulationMovement());
            tSimExplore.start();
        } 
        else {
            tSimExplore.interrupt();
        }
    }

    //Simulation only.
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } 
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}