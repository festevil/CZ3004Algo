package Main;

import entities.DirectedCoordinate;
import entities.Map;
import entities.Node;
import entities.Robot;
import gui.GUI;

import java.util.ArrayList;

import algorithms.FastestPath;
import algorithms.VisitNode;
import algorithms.HamiltonianPathFinder;

public class SimulationMovement implements Runnable {

	private GUI gui;
	private Robot robot;
	private Map testMap;
	private VisitNode vn;
	private FastestPath fp;
	private HamiltonianPathFinder pf = new HamiltonianPathFinder();

	public SimulationMovement() {
		this.gui = Main.gui;
		this.testMap = Main.curMap;

		// Reset all objects to a clean state
		robot = Main.robot;
		vn = Main.vn;
		fp = Main.fp;
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
					Thread.sleep(300);
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
