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
	private Coordinate robpos;

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
					robpos = robot.getCurrPos();;
					if(testMap.getCell(new Coordinate(robpos.getY()+3,robpos.getX())).getCellType() == Cell.SOUTHWALL) {
						vn.directionRotate(Robot.NORTH);
						System.out.println("Rotate North");
						gui.refreshGUI(robot, testMap);
						Thread.sleep(200);
						
					}
					else if(testMap.getCell(new Coordinate(robpos.getY(),robpos.getX()+3)).getCellType() == Cell.WESTWALL) {
						vn.directionRotate(Robot.EAST);
						System.out.println("Rotate EAST");
						gui.refreshGUI(robot, testMap);
						Thread.sleep(200);
					}
					else if(testMap.getCell(new Coordinate(robpos.getY(),robpos.getX()-3)).getCellType() == Cell.EASTWALL) {
						vn.directionRotate(Robot.WEST);
						System.out.println("Rotate WEST");
						gui.refreshGUI(robot, testMap);
						Thread.sleep(200);
					}
					else if(testMap.getCell(new Coordinate(robpos.getY()-3,robpos.getX())).getCellType() == Cell.NORTHWALL) {
						vn.directionRotate(Robot.SOUTH);
						System.out.println("Rotate SOUTH");
						gui.refreshGUI(robot, testMap);
						Thread.sleep(200);
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
