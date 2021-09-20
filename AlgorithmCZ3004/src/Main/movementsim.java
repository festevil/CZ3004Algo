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
			ArrayList<Cell> pictureCellList = testMap.getPictureCellList();
			ArrayList<Cell> totalCellList = pf.findShortestPath(pictureCellList);
			try {
				for (int i = 0; i < totalCellList.size() - 1; i++) {
					Cell startingCell = totalCellList.get(i);
					Cell endingCell = totalCellList.get(i+1);
					fp = new FastestPath(testMap, new Coordinate(startingCell.getY(), startingCell.getX()), new Coordinate(endingCell.getY(), endingCell.getX()));
					ArrayList<Node> fastest = fp.runAStar();
					vn = new VisitNode(robot);
					for (int j = 0; j < fastest.size(); j++) {
						Node n = fastest.get(j);
						vn.visitnodeOneStep(n.getCell().getX(), n.getCell().getY());
						gui.refreshGUI(robot, testMap);
						Thread.sleep(200);
					}
					switch(endingCell.getCellType()) {
						case 'A':
							vn.directionRotate(Robot.NORTH);
							break;
						
						case 'B':
							vn.directionRotate(Robot.EAST);
							break;
							
						case 'C':
							vn.directionRotate(Robot.SOUTH);
							break;
						
						case 'D':
							vn.directionRotate(Robot.WEST);
							break;
					}
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
