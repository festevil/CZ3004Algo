package Main;

import entities.CellCoorPair;
import entities.Map;
import entities.Node;
import entities.Robot;
import entities.Robot.Rotate;

import java.util.ArrayList;

import algorithms.FastestPathV2;
import algorithms.HamiltonianPathFinder;

public class RealMovementCalculation {

	private Robot robot;
	private Map curMap;
	private FastestPathV2 fp;
	private HamiltonianPathFinder pf = new HamiltonianPathFinder();

	private String msgAndroid = new String();
	private String msgSTM = new String();

	private ArrayList<CellCoorPair> coorMap;

	public RealMovementCalculation() {
		this.curMap = Main.curMap;

		// Reset all objects to a clean state
		robot = Main.robot;
		fp = Main.fp2;
	}

	public ArrayList<String> getMessageString() {
		initialization();

		ArrayList<String> result = new ArrayList<>();
		result.add(msgSTM);
		result.add(msgAndroid);
		return result;
	}

	public ArrayList<CellCoorPair> getCoorMap() {
		return coorMap;
	}

	private void initialization() {
		for (int i = 0; i < curMap.getPictureCellList().size(); i++) {
			System.out.println("Picture cell at: " + curMap.getPictureCellList().get(i).toStringActual(Main.padding));
		}
		coorMap = pf.findShortestPath(curMap.getPictureCellList(), robot.getcurPos(), robot.getcurDir());
		StringBuilder buildAndroid = new StringBuilder(1000);
		StringBuilder buildSTM = new StringBuilder(1000);

		//buildSTM.append("c");

		for (int i = 0; i < coorMap.size() - 1; i++) {
			
			//Use A* Star to move the robot from startCoor to endCoor
			//System.out.println("Starting from: " + coorMap.get(i).getCoor().toString());
			fp = new FastestPathV2(curMap, coorMap.get(i).getCoor(), coorMap.get(i+1).getCoor(), Main.padding);
			ArrayList<Node> fastest = fp.runAStar(curMap);

			for (int j = 0; j < fastest.size(); j++) {
				Node n = fastest.get(j);
				ArrayList<String> instructions = visitNode(n.getCell().getY(), n.getCell().getX(), n.getDir());
				for (char c: instructions.get(0).toCharArray()) {
					switch(c) {
						case 'w':
							robot.moveForward(1);
							break;
						case 'x':
							robot.moveForward(-1);
							break;
						case 'd':
							robot.rotate(Rotate.RIGHT);
							break;
						case 'a':
							robot.rotate(Rotate.LEFT);
							break;
						case 's':
							break;
						default:
					}
				}
				buildSTM.append(instructions.get(1));
				buildAndroid.append(instructions.get(2));
				if (j != fastest.size() - 1 && instructions.get(2) != "") {
					buildAndroid.append(",");
				}
			}
			//System.out.println("Arrived at: " + coorMap.get(i+1).getCoor().toString());
			
			buildSTM.append("s");
			buildAndroid.append(",e");
			if (i != coorMap.size() - 2) {
				buildAndroid.append(",");
			}
		}
		
		msgAndroid = buildAndroid.toString();
		msgSTM = buildSTM.toString();
		
		msgSTM = msgSTM.replace("xxxwww","").replace("xxww","").replace("xw","");
		msgSTM = msgSTM.replace("wwwxxx","").replace("wwxx","").replace("wx","");
		msgSTM = msgSTM.replace("xxxxx","l").replace("xxxx","k").replace("xxx","j").replace("xx","1");
		msgSTM = msgSTM.replace("wwwwwwwww","9").replace("wwwwwwww","8").replace("wwwwwww","7").replace("wwwwww","6").replace("wwwww","5").replace("wwww","4").replace("www","3").replace("ww","2");

		msgAndroid = msgAndroid.replace(",x,x,x,w,w,w","").replace(",x,x,w,w","").replace(",x,w","");
		msgAndroid = msgAndroid.replace(",w,w,w,x,x,x","").replace(",w,w,x,x","").replace(",w,x","");
		msgAndroid = msgAndroid.replace("w,w,w,w,w,w,w,w,w","9").replace("w,w,w,w,w,w,w,w","8").replace("w,w,w,w,w,w,w","7");
		msgAndroid = msgAndroid.replace("w,w,w,w,w,w","6").replace("w,w,w,w,w","5").replace("w,w,w,w","4").replace("w,w,w","3").replace("w,w","2");
	}

	private ArrayList<String> visitNode(int y, int x, int dir) {
		int curY = robot.getcurPos().getY();
		int curX = robot.getcurPos().getX();
		int curDir = robot.getcurDir();

		ArrayList<String> result = new ArrayList<String>();

		if (y - curY == 2 && x - curX == 2) {
			if(curDir == Robot.NORTH) {
				result.add("wwdwwss");
				result.add("d");
				result.add("w,d,w");
			}
			if(curDir == Robot.EAST) {
				result.add("wwawwss");
				result.add("a");
				result.add("w,a,w");
			}
		}
		if (y - curY == 2 && x - curX == -2) {
			if(curDir == Robot.NORTH) {
				result.add("wwawwss");
				result.add("a");
				result.add("w,a,w");
			}
			if(curDir == Robot.WEST) {
				result.add("wwdwwss");
				result.add("d");
				result.add("w,d,w");
			}
		}
		if (y - curY == -2 && x - curX == 2) {
			if(curDir == Robot.SOUTH) {
				result.add("wwawwss");
				result.add("a");
				result.add("w,a,w");
			}
			if(curDir == Robot.EAST) {
				result.add("wwdwwss");
				result.add("d");
				result.add("w,d,w");
			}
		}
		if (y - curY == -2 && x - curX == -2) {
			if(curDir == Robot.SOUTH) {
				result.add("wwdwwss");
				result.add("d");
				result.add("w,d,w");
			}
			if(curDir == Robot.WEST) {
				result.add("wwawwss");
				result.add("a");
				result.add("w,a,w");
			}
		}
		if (y - curY == 1) {
			if(curDir == Robot.NORTH) {
				result.add("w");
				result.add("w");
				result.add("w");
			}
			if(curDir == Robot.SOUTH) {
				result.add("x");
				result.add("x");
				result.add("x");
			}
		}
		if (y - curY == -1) {
			if(curDir == Robot.NORTH) {
				result.add("x");
				result.add("x");
				result.add("x");
			}
			if(curDir == Robot.SOUTH) {
				result.add("w");
				result.add("w");
				result.add("w");
			}
		}
		if (x - curX == 1) {
			if(curDir == Robot.EAST) {
				result.add("w");
				result.add("w");
				result.add("w");
			}
			if(curDir == Robot.WEST) {
				result.add("x");
				result.add("x");
				result.add("x");
			}
		}
		if (x - curX == -1) {
			if(curDir == Robot.EAST) {
				result.add("x");
				result.add("x");
				result.add("x");
			}
			if(curDir == Robot.WEST) {
				result.add("w");
				result.add("w");
				result.add("w");
			}
		}
		
		if (result.isEmpty()) {
			result.add("");
			result.add("");
			result.add("");
		}
		return result;
	}
}

