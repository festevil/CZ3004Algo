package communications;

import entities.DirectedCoordinate;
import entities.Map;
import entities.Node;
import entities.Robot;

import java.util.ArrayList;

import algorithms.FastestPath;
import algorithms.VisitNode;
import algorithms.HamiltonianPathFinder;

public class MoveThread {

	private Robot robot;
	private Map testMap;
	private VisitNode vn;
	private FastestPath fp;
	private HamiltonianPathFinder pf = new HamiltonianPathFinder();

	private ArrayList<String> msgAndroid = new ArrayList<>();
	private ArrayList<String> msgSTM = new ArrayList<>();

	public MoveThread() {
		this.testMap = client.testmap;

		// Reset all objects to a clean state
		robot = client.realrobot;
		vn = client.vn;
		fp = client.fp;
	}

	public ArrayList<String> AndroidString() {
		if (msgAndroid.isEmpty()) 
			initializeString();
		return msgAndroid;
	}

	public ArrayList<String> STMString() {
		if (msgSTM.isEmpty()) 
			initializeString();
		return msgSTM;
	}
	
	private void initializeString() {
		ArrayList<DirectedCoordinate> coorList = pf.findShortestPath(testMap.getPictureCellList(), robot.getcurPos(), robot.getcurDir());
		StringBuilder buildAndroid = new StringBuilder();
		StringBuilder buildSTM = new StringBuilder();
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
			buildAndroid.append(vn.AndroidRotate(coorList.get(i+1).getDir()));
			buildAndroid.append(vn.AndroidRotate(coorList.get(i+1).getDir()));
			msgAndroid.add(buildAndroid.toString());
			msgSTM.add(buildSTM.toString());
			buildSTM.setLength(0);
			buildAndroid.setLength(0);
		}
	}
}

