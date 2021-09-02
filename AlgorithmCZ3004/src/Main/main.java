package Main;

import entities.Coordinate;
import entities.Map;
import entities.Robot;
import gui.GUI;

public class main {

	/** Set Mode here **/
	public static boolean isRealRun = false;

	/* Shared Variables */
	public static Robot robot;
	public static Map exploredMap;
	public static GUI gui;


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
}