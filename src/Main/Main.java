package Main;

import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.nio.charset.StandardCharsets;

import entities.Cell;
import entities.CellCoorPair;
import entities.Map;
import entities.Robot;
import entities.Robot.Rotate;
import entities.Coordinate;

import gui.GUI;

import algorithms.VisitNode;
import algorithms.FastestPath;
import algorithms.FastestPathV2;

public class Main {

    // Set Mode here
    public static boolean isRealRun = false;

    // Shared Variables
    public static Robot robot;
    public static Map curMap;
    public static GUI gui;
    public static FastestPath fp;
    public static FastestPathV2 fp2;
    public static VisitNode vn;

    // Simulation Only Variables
    public static Thread tSimExplore;
    public static void main(String[] args) throws Exception {
        // Initialize Variables & GUI
        robot = new Robot(new Coordinate(1, 1), Robot.NORTH, isRealRun);    // Default starting position of robot (1,1 facing East)
        curMap = new Map();                                                 // Set map (starts from an unknown state)
        gui = new GUI(robot, curMap);
        RealMovementCalculation calculation = new RealMovementCalculation();
        int curCellIndex = 1;

        HashMap<String, Character> cellDirMap = new HashMap<>();
        cellDirMap.put("N", Cell.NORTHWALL);
        cellDirMap.put("E", Cell.EASTWALL);
        cellDirMap.put("S", Cell.SOUTHWALL);
        cellDirMap.put("W", Cell.WESTWALL);
        
        HashMap<Character, String> cellDirMapReverse = new HashMap<>();
        cellDirMapReverse.put(Cell.NORTHWALL, "N");
        cellDirMapReverse.put(Cell.EASTWALL, "E");
        cellDirMapReverse.put(Cell.SOUTHWALL, "S");
        cellDirMapReverse.put(Cell.WESTWALL, "W");

        HashMap<String, Integer> robotDirMap = new HashMap<>();
        robotDirMap.put("N", Robot.NORTH);
        robotDirMap.put("E", Robot.EAST);
        robotDirMap.put("S", Robot.SOUTH);
        robotDirMap.put("W", Robot.WEST);

        //Real run mode
        if (isRealRun) {
            boolean started = false;
            boolean stop = false;

            Scanner myObj = new Scanner(System.in);
            while (!stop) {
                try {
                    Socket s = new Socket("192.168.15.15", 5001); // port 5001
                    System.out.println("Connecting");
                    String receive = "";

                    BufferedReader handshake = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));

                    while (receive != "stop") {
                        //System.out.println("Reading Line...");
                        receive = handshake.readLine();
                        //receive = "hi";
                        System.out.println("Message from Server: " + receive);

                        while (receive == null) {
                            receive = handshake.readLine();
                        }
                        
                        String receiveList[] = receive.split(",");

                        switch (receiveList[0]) {
                            case "ADD":
                                curMap.setCellType(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]), Cell.WALL);
                                break;
                            case "SUB":
                                curMap.delPictureCell(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]));
                                break;
                            case "FACE":
                                curMap.setCellType(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]), cellDirMap.get(receiveList[3]));
                                break;
                            case "ROBOT":
                                robot.setCurPos(Map.maxY - 1 - Integer.parseInt(receiveList[2]), Integer.parseInt(receiveList[1]));
                                robot.setCurDir(robotDirMap.get(receiveList[3]).intValue());
                                break;
                            case "TARGET":
                                int imageId = Integer.parseInt(receiveList[1]);
                                // Cannot call TARGET before START, or else error. Need to figure this out later...
                                ArrayList<CellCoorPair> coorMap = calculation.getCoorMap();
                                try {
                                    String toSend = "TARGET," + coorMap.get(curCellIndex).getCell().getX() + "," 
                                        + (Map.maxY - 1 - coorMap.get(curCellIndex).getCell().getY()) + "," + imageId + ","
                                        + cellDirMapReverse.get(coorMap.get(curCellIndex).getCell().getCellType());
                                    System.out.println("Sent to Android: " + toSend);
                                    output.write("AD:" + toSend);
                                    output.flush();
                                    curCellIndex++;
                                }
                                catch (Exception e) {
                                    System.out.println("Error! Resetting curCellIndex...");
                                    curCellIndex = 1;
                                }
                                break;
                            case "START":
                                if(!started) {
                                    started = true;
                                    ArrayList<String> messages = calculation.getMessageString();
                                    System.out.println("Sent to STM: " + messages.get(0));
                                    output.write("STM:" + messages.get(0));
                                    output.flush();
                                    System.out.println("Sent to Android: " + messages.get(1));
                                    output.write("AD:" + messages.get(1));
                                    output.flush();
                                }
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
                    output.close();
                    s.close();
                }
                catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Error encountered. Restarting connection...");
                }
            }  
        }

        //Simulation mode
        else {
            curMap = new Map("test6.txt");
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