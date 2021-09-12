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
	private Robot robot;
	public VisitNode(Robot robot) {
		state = '1';//Initial state.
		this.robot = robot;
	}
	public void visitnodeOneStep(int x, int y) {
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
				robot.rotate(Rotate.RIGHT);
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
				break;
			}
		}
		if(x<CurrentPos.getX()) {
			switch(CurrentDir) {
			case Robot.EAST:
				robot.rotate(Rotate.RIGHT);
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
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
				robot.rotate(Rotate.RIGHT);
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
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
				robot.rotate(Rotate.RIGHT);
				robot.rotate(Rotate.RIGHT);
				robot.moveForward(1);
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
	public void directionRotate(int direction) {
		CurrentDir = robot.getCurrDir();
		if (direction == Robot.NORTH) {
			switch(CurrentDir) {
			case Robot.NORTH:
				break;
			case Robot.EAST:
				robot.rotate(Rotate.LEFT);
				break;
			case Robot.WEST:
				robot.rotate(Rotate.RIGHT);
				break;
			}
		}
		if (direction == Robot.EAST) {
			switch(CurrentDir) {
			case Robot.EAST:
				break;
			case Robot.SOUTH:
				robot.rotate(Rotate.LEFT);
				break;
			case Robot.NORTH:
				robot.rotate(Rotate.RIGHT);
				break;
			}
		}
		if (direction == Robot.SOUTH) {
			switch(CurrentDir) {
			case Robot.SOUTH:
				break;
			case Robot.WEST:
				robot.rotate(Rotate.LEFT);
				break;
			case Robot.EAST:
				robot.rotate(Rotate.RIGHT);
				break;
			}
		}
		if (direction == Robot.WEST) {
			switch(CurrentDir) {
			case Robot.WEST:
				break;
			case Robot.SOUTH:
				robot.rotate(Rotate.LEFT);
				break;
			case Robot.NORTH:
				robot.rotate(Rotate.RIGHT);
				break;
			}
		}
	}

}
