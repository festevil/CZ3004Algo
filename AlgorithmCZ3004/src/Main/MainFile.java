package Main;

import algorithms.FastestPath;
import entities.Map;
import entities.Robot;
import entities.Robot.Rotate;
import gui.GUI;
import algorithms.VisitNode;
import communications.TCPComm;

public class MainFile {

    // Set Mode here
    public static boolean isRealRun = true;

    // Shared Variables
    public static Robot robot;
    public static Map exploredMap;
    public static GUI gui;
    public static FastestPath fp;
    public static VisitNode vn;

    // Simulation Only Variables
    public static Map testMap;
    public static Thread tSimExplore;
    
	/* Real Run Only Variables */
	public static TCPComm comms;

    public static void main(String[] args) {
        // Initialize Variables
        robot = new Robot(isRealRun); // Default starting position of robot (1,1 facing East)

        exploredMap = new Map("simulationtest.txt");	// Set exploredMap (starts from an unknown state)
        gui = new GUI(robot, exploredMap);

        if (isRealRun) {
			gui.setModeColour(false);
			comms = new TCPComm();		// Initialise TCP Communication (Will wait...)
			gui.setModeColour(comms.isConnected());

			try {
				Thread.sleep(2000);		// Raspberry Pi needs time to get ready
			} catch (Exception e) {
			}
			
			/** R0. Preparation: Calibrate Robot and update GUI **/
			comms.send(TCPComm.SERIAL, "R90|L90");	// Turn South to calibrate first, then turn East
			gui.refreshGUI(MainFile.robot, MainFile.exploredMap);
			

			/** 5. SEND STRING TO SERIAL AND BLUETOOTH **/
			comms.sendFastestPath(fp.navigateSteps());
		}
        else {
        // Load testMap
        testMap = new Map("test2.txt"); // Set simulatedMap for use

        // Initialize GUI
        gui = new GUI(robot, testMap);
        }
    }

    public static void runForwardStep() {
        robot.moveForward(1);
        gui.refreshGUI(robot, testMap);
    }

    public static void rotateleft() {
        robot.rotate(Rotate.LEFT);
        gui.refreshGUI(robot, testMap);
    }

    public static void rotateright() {
        robot.rotate(Rotate.RIGHT);
        gui.refreshGUI(robot, testMap);
    }

    public static void runShowFastestPath() {
        if (tSimExplore == null || !tSimExplore.isAlive()) {
            tSimExplore = new Thread(new MovementSimulator());
            tSimExplore.start();
        } 
        else {
            tSimExplore.interrupt();
        }
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } 
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}