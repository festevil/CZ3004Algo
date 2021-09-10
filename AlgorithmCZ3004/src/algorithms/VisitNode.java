package algorithms;

import entities.Cell;
import entities.Coordinate;
import entities.Map;
import entities.Robot;
import entities.Robot.Rotate;
import Main.main;

public class VisitNode {
	private char state;
	/**
	 * Constructor for VisitNode. Prepares state.
	 */
	private Coordinate CurrentPos;
	private int CurrentDir;
	public VisitNode() {
		state = '1';//Initial state.
	}
	public void visitnodeOneStep(int x, int y, Robot robot) {
		CurrentPos = robot.getCurrPos();
		CurrentDir = robot.getCurrDir();
		if(x>CurrentPos.getX()) {
			switch(CurrentDir) {
			case Robot.EAST:
				robot.moveForward(1);
				break;
			case Robot.NORTH:
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
				break;
			case Robot.SOUTH:
				robot.rotate(Rotate.LEFT);
				robot.moveForward(1);
				break;
			case Robot.WEST:
				robot.moveForward(-1);
				break;
			}
		}
		if(x<CurrentPos.getX()) {
			switch(CurrentDir) {
			case Robot.EAST:
				robot.moveForward(-1);
				break;
			case Robot.NORTH:
				robot.rotate(Rotate.LEFT);
				robot.moveForward(1);
				break;
			case Robot.SOUTH:
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
				break;
			case Robot.WEST:
				robot.moveForward(1);
				break;
			}
		}
		if(y>CurrentPos.getY()) {
			switch(CurrentDir) {
			case Robot.EAST:
				robot.rotate(Rotate.LEFT);
				robot.moveForward(1);
				break;
			case Robot.NORTH:
				robot.moveForward(1);
				break;
			case Robot.SOUTH:
				robot.moveForward(-1);
				break;
			case Robot.WEST:
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
				break;
			}
		}
		if(y<CurrentPos.getY()) {
			switch(CurrentDir) {
			case Robot.EAST:
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
				break;
			case Robot.NORTH:
				robot.moveForward(-1);
				break;
			case Robot.SOUTH:
				robot.moveForward(1);
				break;
			case Robot.WEST:
				robot.rotate(Rotate.LEFT);
				robot.moveForward(1);
				break;
			}
		}	
	}

}
