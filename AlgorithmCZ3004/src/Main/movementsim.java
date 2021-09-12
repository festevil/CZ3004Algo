package Main;

import entities.Coordinate;
import entities.Map;
import entities.Node;
import entities.Robot;
import gui.GUI;

import java.util.ArrayList;

import Main.main;
import algorithms.FastestPath;
import algorithms.VisitNode;

public class movementsim implements Runnable {

	private GUI gui;
	private Robot robot;
	private Map testMap;
	private VisitNode vn;
	private FastestPath fp;

	public movementsim() {
		this.gui = main.gui;
		this.testMap = main.testMap;

		// Reset all objects to a clean state
		robot = main.robot;
		vn = main.vn;
		fp = main.fp;
	}
	@Override
	public void run() {
		System.out.println(":: " + getClass().getName() + " Thread Started ::");

		while (!Thread.currentThread().isInterrupted()) {
			try {
				fp = new FastestPath(testMap, new Coordinate(1, 1), new Coordinate(4, 5));
				ArrayList<Node> fastest = fp.runAStar();
				vn = new VisitNode(robot);
				for (int i = 0; i < fastest.size(); i++) {
					Node n = fastest.get(i);
					vn.visitnodeOneStep(n.getCell().getX(),n.getCell().getY());
					gui.refreshGUI(robot, testMap);
					Thread.sleep(100);
				}
				vn.directionRotate(Robot.NORTH);
				break;

				
			} catch (InterruptedException e) {
				break;
			}
		}

		System.out.println(":: " + getClass().getName() + " Thread Ended ::\n");
	}
	
}
