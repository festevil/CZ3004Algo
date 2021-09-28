package communications;

import entities.Coordinate;
import entities.DirectedCoordinate;
import entities.Map;
import entities.Node;
import entities.Robot;
import gui.GUI;

import java.util.ArrayList;

import Main.MainFile;
import algorithms.FastestPath;
import algorithms.VisitNode;
import algorithms.PathFinder;

public class MoveThread {

	private Robot robot;
	private Map testMap;
	private VisitNode vn;
	private FastestPath fp;
	private PathFinder pf = new PathFinder();

	private String msg;
	private static String msg2;

	public MoveThread() {
		this.testMap = client.testmap;

		// Reset all objects to a clean state
		robot = client.realrobot;
		vn = client.vn;
		fp = client.fp;
		msg2 = "STM:";
	}
	public String AndroidString() {
		ArrayList<DirectedCoordinate> coorList = pf.findShortestPath(testMap.getPictureCellList(), robot.getcurPos(), robot.getcurDir());
		msg = "AD: ";
		for (int i = 0; i < coorList.size() - 1; i++) {
			
			//Use A* Star to move the robot from startCoor to endCoor
			fp = new FastestPath(testMap, coorList.get(i), coorList.get(i+1));
			ArrayList<Node> fastest = fp.runAStar();
			vn = new VisitNode(robot);
			for (int j = 0; j < fastest.size(); j++) {
				Node n = fastest.get(j);
				msg2 = msg2 + vn.OneStepSTM(n.getCell().getX(), n.getCell().getY());
				
				msg = msg + vn.OneStepAndroid(n.getCell().getX(), n.getCell().getY());
				msg2 = msg2 + vn.OneStepSTM(n.getCell().getX(), n.getCell().getY());
				msg = msg + vn.OneStepAndroid(n.getCell().getX(), n.getCell().getY());
			
			}
			msg2 = msg2 + vn.STMRotate(coorList.get(i+1).getDir());
			msg = msg + vn.AndroidRotate(coorList.get(i+1).getDir());
			msg2 = msg2 + "s";
		}
		System.out.println(msg);
		return msg;
			
	}
	public String STMString() {
		return msg2;
	}
}

