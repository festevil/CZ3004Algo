package Main;

import entities.DirectedCoordinate;
import entities.Map;
import entities.Node;
import entities.Robot;

import java.util.ArrayList;

import algorithms.FastestPath;
import algorithms.VisitNode;
import algorithms.HamiltonianPathFinder;

public class RealMovementCalculation {

	private Robot robot;
	private Map testMap;
	private VisitNode vn;
	private FastestPath fp;
	private HamiltonianPathFinder pf = new HamiltonianPathFinder();

	private String msgAndroid = new String();
	private String msgSTM = new String();

	public RealMovementCalculation() {
		this.testMap = Main.curMap;

		// Reset all objects to a clean state
		robot = Main.robot;
		vn = Main.vn;
		fp = Main.fp;
	}

	public String AndroidString() {
		initializeString();
		return msgAndroid;
	}

	public String STMString() {
		initializeString();
		return msgSTM;
	}
	
	private void initializeString() {
		ArrayList<DirectedCoordinate> coorList = pf.findShortestPath(testMap.getPictureCellList(), robot.getcurPos(), robot.getcurDir());
		StringBuilder buildAndroid = new StringBuilder(1000);
		StringBuilder buildSTM = new StringBuilder(1000);
		for (int i = 0; i < coorList.size() - 1; i++) {
			
			//Use A* Star to move the robot from startCoor to endCoor
			fp = new FastestPath(testMap, coorList.get(i), coorList.get(i+1));
			ArrayList<Node> fastest = fp.runAStar();
			vn = new VisitNode(robot);

			for (int j = 0; j < fastest.size(); j++) {
				Node n = fastest.get(j);
				String STMchar = vn.OneStepSTM(n.getCell().getX(), n.getCell().getY());
				buildSTM.append(STMchar);
				buildAndroid.append(vn.OneStepAndroid(n.getCell().getX(), n.getCell().getY()));
			}
			
			buildSTM.append(vn.STMRotate(coorList.get(i+1).getDir()));
			buildSTM.append('s');
			buildAndroid.append(vn.AndroidRotate(coorList.get(i+1).getDir()));
		}
		msgAndroid = buildAndroid.toString();
		msgSTM = buildSTM.toString();
		
		msgSTM = msgSTM.replace("xxxwww","").replace("xxww","").replace("xw","");
		msgSTM = msgSTM.replace("wwwxxx","").replace("wwxx","").replace("wx","");
		msgSTM = msgSTM.replace("xx","1");
		msgSTM = msgSTM.replace("wwww","4").replace("www","3").replace("www","2");
	}
}

