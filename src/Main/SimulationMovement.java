package Main;

import entities.DirectedCoor;
import entities.CellCoorPair;
import entities.Map;
import entities.Node;
import entities.Robot;
import entities.Robot.Rotate;
import gui.GUI;

import java.util.ArrayList;

import algorithms.FastestPathV2;
import algorithms.VisitNode;
import algorithms.HamiltonianPathFinder;

public class SimulationMovement implements Runnable {

	private GUI gui;
	private Robot robot;
	private Map curMap;
	private VisitNode vn;
	private FastestPathV2 fp;
	private HamiltonianPathFinder hpf = new HamiltonianPathFinder();

	public SimulationMovement() {
		this.gui = Main.gui;
		this.curMap = Main.curMap;

		// Reset all objects to a clean state
		robot = Main.robot;
		vn = Main.vn;
		fp = Main.fp2;
	}

	/**
	 * Implement the run() method.
	 */
	@Override
	public void run() {
		System.out.println(":: " + getClass().getName() + " Thread Started ::");

		while (!Thread.currentThread().isInterrupted()) {
			// Compute path
			for (int i = 0; i < curMap.getPictureCellList().size(); i++) {
				System.out.println("Picture cell at: " + curMap.getPictureCellList().get(i).toString());
			}
			ArrayList<CellCoorPair> coorMap = hpf.findShortestPath(curMap.getPictureCellList(), robot.getcurPos(), robot.getcurDir());

			try {
				for (int i = 0; i < coorMap.size() - 1; i++) {
					// Use A* Star to find the path from startCoor to endCoor
					System.out.println("Starting from: " + coorMap.get(i).getCoor().toString());
					fp = new FastestPathV2(curMap, coorMap.get(i).getCoor(), coorMap.get(i+1).getCoor());
					ArrayList<Node> fastest = fp.runAStar(curMap);

					// System.out.println("Fastest path: ");
					// for (Node n: fastest)
					// System.out.println(n.toString());
					vn = new VisitNode(robot);
					for (int j = 0; j < fastest.size(); j++) {
						Node n = fastest.get(j);
						ArrayList<String> instructions = vn.visitNode(n.getCell().getY(), n.getCell().getX(), n.getDir());
						System.out.print(instructions.get(1));
						for (char c: instructions.get(0).toCharArray()) {
							switch(c) {
								case 'w':
									robot.moveForward(1);
									gui.refreshGUI(robot, curMap);
									Thread.sleep(200);
									break;
								case 'x':
									robot.moveForward(-1);
									gui.refreshGUI(robot, curMap);
									Thread.sleep(200);
									break;
								case 'd':
									robot.rotate(Rotate.RIGHT);
									gui.refreshGUI(robot, curMap);
									Thread.sleep(200);
									break;
								case 'a':
									robot.rotate(Rotate.LEFT);
									gui.refreshGUI(robot, curMap);
									Thread.sleep(200);
									break;
								case 's':
									Thread.sleep(200);
									break;
								default:
							}
						}
					}
					System.out.print("e");
					System.out.println();
					System.out.println("Arrived at: " + coorMap.get(i+1).getCoor().toString());
					Thread.sleep(1000);
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
