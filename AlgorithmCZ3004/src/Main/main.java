package Main;

import java.util.ArrayList;

import algorithms.FastestPath;
import entities.Coordinate;
import entities.Map;
import entities.Node;
import entities.Robot;
import entities.Robot.Rotate;
import gui.GUI;
import Main.movementsim;
import algorithms.VisitNode;


public class main {

	/** Set Mode here **/
	public static boolean isRealRun = false;

	/* Shared Variables */
	public static Robot robot;
	public static Map exploredMap;
	public static GUI gui;
	public static FastestPath fp;
	public static VisitNode vn;


	/* Simulation Only Variables */
	public static Map testMap;
	public static Thread tSimExplore;



	/**
	 * Main program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/* 1. Initialise Variables */
		robot = new Robot(isRealRun);			// Default starting position of robot (1,1 facing East)

		


		

		/* ======================== SIMULATION MODE ======================== */
		
			// Load testMap
			testMap = new Map("simulationtest.txt");	// Set simulatedMap for use
			/* 2. Initialise GUI */
			gui = new GUI(robot, testMap);
			
	}
	public static void runForwardStep() {		

		/* Move forward for one step */
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
			tSimExplore = new Thread(new movementsim());
			tSimExplore.start();
		} else {
			tSimExplore.interrupt();
		}
		
	}
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}

}