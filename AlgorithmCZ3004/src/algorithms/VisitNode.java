package algorithms;

import entities.Coordinate;
import entities.Robot;
import entities.Robot.Rotate;

public class VisitNode {
	
	private Coordinate curPos;
	private int curDir;
	private Robot robot;

	
	public VisitNode(Robot robot) {
		this.robot = robot;
	}

	public void visitnodeOneStep(int x, int y) {
		curPos = robot.getcurPos();
		curDir = robot.getcurDir();
		if (x > curPos.getX()) {
			switch(curDir) {
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
		if (x < curPos.getX()) {
			switch(curDir) {
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
		if (y > curPos.getY()) {
			switch(curDir) {
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
		if (y < curPos.getY()) {
			switch(curDir) {
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
		curDir = robot.getcurDir();
		if (direction == Robot.NORTH) {
			switch(curDir) {
				case Robot.NORTH:
					break;
				case Robot.SOUTH:
					robot.rotate(Rotate.RIGHT);
					robot.rotate(Rotate.RIGHT);
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
			switch(curDir) {
				case Robot.EAST:
					break;
				case Robot.WEST:
					robot.rotate(Rotate.RIGHT);
					robot.rotate(Rotate.RIGHT);
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
			switch(curDir) {
				case Robot.SOUTH:
					break;
				case Robot.NORTH:
					robot.rotate(Rotate.RIGHT);
					robot.rotate(Rotate.RIGHT);
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
			switch(curDir) {
				case Robot.WEST:
					break;
				case Robot.EAST:
					robot.rotate(Rotate.RIGHT);
					robot.rotate(Rotate.RIGHT);
					break;
				case Robot.SOUTH:
					robot.rotate(Rotate.RIGHT);
					break;
				case Robot.NORTH:
					robot.rotate(Rotate.LEFT);
					break;
			}
		}
	}
}
