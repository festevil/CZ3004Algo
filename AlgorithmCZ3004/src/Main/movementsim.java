package Main;

import entities.Coordinate;
import entities.Map;
import entities.Node;
import entities.Robot;
import entities.Cell;
import gui.GUI;

import java.util.ArrayList;

import Main.main;
import algorithms.FastestPath;
import algorithms.VisitNode;
import algorithms.PathFinder;

public class movementsim implements Runnable {

	private GUI gui;
	private Robot robot;
	private Map testMap;
	private VisitNode vn;
	private FastestPath fp;
	private PathFinder pf = new PathFinder();

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
			ArrayList<Cell> cellList = testMap.getPictureCellList();
			ArrayList<Integer> nodeList = pf.findShortestPath(cellList);
			for (int i = 0; i < cellList.size() - 1; i++) {
				try {
					Coordinate startingPoint = new Coordinate(1, 1);
					if (i != 0) {
						Cell startingCell = cellList.get(nodeList.get(i) - 1);
						startingPoint = new Coordinate(startingCell.getY(), startingCell.getX());
					}
					Cell endingCell = cellList.get(nodeList.get(i+1) - 1);
					Coordinate endingPoint = new Coordinate(endingCell.getY(), endingCell.getX());
					fp = new FastestPath(testMap, startingPoint, endingPoint);
					ArrayList<Node> fastest = fp.runAStar();
					vn = new VisitNode(robot);
					for (int j = 0; j < fastest.size(); j++) {
						Node n = fastest.get(j);
						vn.visitnodeOneStep(n.getCell().getX(), n.getCell().getY());
						gui.refreshGUI(robot, testMap);
						Thread.sleep(100);
					}
					vn.directionRotate(Robot.NORTH);
					if (i == cellList.size() - 2) 
						break;
				} catch (InterruptedException e) {
					break;
				}
			}
		}

		System.out.println(":: " + getClass().getName() + " Thread Ended ::\n");
	}
	
}
