package Main;

import algorithms.FastestPath;
import entities.Map;
import entities.Robot;
import entities.Robot.Rotate;
import gui.GUI;
import algorithms.VisitNode;

public class MainFile {

    // Set Mode here
    public static boolean isRealRun = false;

    // Shared Variables
    public static Robot robot;
    public static Map exploredMap;
    public static GUI gui;
    public static FastestPath fp;
    public static VisitNode vn;

    // Simulation Only Variables
    public static Map testMap;
    public static Thread tSimExplore;

    public static void main(String[] args) {
        // Initialize Variables
        robot = new Robot(isRealRun); // Default starting position of robot (1,1 facing East)

        // Load testMap
        testMap = new Map("simulationtest.txt"); // Set simulatedMap for use

        // Initialize GUI
        gui = new GUI(robot, testMap);
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