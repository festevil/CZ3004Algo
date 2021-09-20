package Main;

import entities.DirectedCoordinate;
import entities.Map;
import entities.Node;
import entities.Robot;
import gui.GUI;

import java.util.ArrayList;

import algorithms.FastestPath;
import algorithms.VisitNode;
import algorithms.PathFinder;

public class MovementSimulator implements Runnable {

	private GUI gui;
	private Robot robot;
	private Map testMap;
	private VisitNode vn;
	private FastestPath fp;
	private PathFinder pf = new PathFinder();

	public MovementSimulator() {
		this.gui = MainFile.gui;
		this.testMap = MainFile.testMap;

		// Reset all objects to a clean state
		robot = MainFile.robot;
		vn = MainFile.vn;
		fp = MainFile.fp;
	}

	@Override
	public void run() {
		System.out.println(":: " + getClass().getName() + " Thread Started ::");

		while (!Thread.currentThread().isInterrupted()) {
			//Compute path
			ArrayList<DirectedCoordinate> coorList = pf.findShortestPath(testMap.getPictureCellList(), robot.getcurPos(), robot.getcurDir());
			try {
				for (int i = 0; i < coorList.size() - 1; i++) {
					
					//Use A* Star to move the robot from startCoor to endCoor
					fp = new FastestPath(testMap, coorList.get(i), coorList.get(i+1));
					ArrayList<Node> fastest = fp.runAStar();
					vn = new VisitNode(robot);
					for (int j = 0; j < fastest.size(); j++) {
						Node n = fastest.get(j);
						vn.visitnodeOneStep(n.getCell().getX(), n.getCell().getY());
						gui.refreshGUI(robot, testMap);
						Thread.sleep(150);
					}
					vn.directionRotate(coorList.get(i+1).getDir());
					gui.refreshGUI(robot, testMap);
				}
				break;
			}
			catch (InterruptedException e) {
				break;
			}
		}
		System.out.println(":: " + getClass().getName() + " Thread Ended ::\n");
	}
	
}
