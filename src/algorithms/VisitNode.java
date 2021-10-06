package algorithms;

import entities.Coordinate;
import entities.Robot;
import entities.Robot.Rotate;

import java.util.ArrayList;

public class VisitNode {
	
	private Coordinate curPos;
	private int curDir;
	private Robot robot;

	
	public VisitNode(Robot robot) {
		this.robot = robot;
	}

	public ArrayList<String> visitnodeOneStep(int x, int y) {
		curPos = robot.getcurPos();
		curDir = robot.getcurDir();
		ArrayList<String> result = new ArrayList<>();

		if (x > curPos.getX()) {
			switch(curDir) {
				case Robot.EAST:
					robot.moveForward(1);
					result.add("f");
					result.add("w");
					break;
				case Robot.NORTH:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					result.add("tr");
					result.add("xxdx");
					break;
				case Robot.SOUTH:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					result.add("tl");
					result.add("xxax");
					break;
				case Robot.WEST:
					robot.moveForward(-1);
					result.add("r");
					result.add("x");
					break;
			}
		}
		if (x < curPos.getX()) {
			switch(curDir) {
				case Robot.EAST:
					robot.moveForward(-1);
					result.add("r");
					result.add("x");
					break;
				case Robot.NORTH:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					result.add("tl");
					result.add("xxax");
					break;
				case Robot.SOUTH:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					result.add("tr");
					result.add("xxdx");
					break;
				case Robot.WEST:
					robot.moveForward(1);
					result.add("f");
					result.add("w");
					break;
			}
		}
		if (y > curPos.getY()) {
			switch(curDir) {
				case Robot.EAST:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					result.add("tl");
					result.add("xxax");
					break;
				case Robot.NORTH:
					robot.moveForward(1);
					result.add("f");
					result.add("w");
					break;
				case Robot.SOUTH:
					robot.moveForward(-1);
					result.add("r");
					result.add("x");
					break;
				case Robot.WEST:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					result.add("tr");
					result.add("xxdx");
					break;
			}
		}
		if (y < curPos.getY()) {
			switch(curDir) {
				case Robot.EAST:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					result.add("tr");
					result.add("xxdx");
					break;
				case Robot.NORTH:
					robot.moveForward(-1);
					result.add("r");
					result.add("x");
					break;
				case Robot.SOUTH:
					robot.moveForward(1);
					result.add("f");
					result.add("w");
					break;
				case Robot.WEST:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					result.add("tl");
					result.add("xxax");
					break;
			}
		}
		if (result.isEmpty())
			result.add("");
			result.add("");
		return result;
	}

	public ArrayList<String> directionRotate(int direction) {
		curDir = robot.getcurDir();
		ArrayList<String> result = new ArrayList<>();
		if (direction == Robot.NORTH) {
			switch(curDir) {
				case Robot.NORTH:
					break;
				case Robot.SOUTH:
					robot.rotate(Rotate.RIGHT);
					robot.rotate(Rotate.RIGHT);
					result.add("trtr");
					result.add("xxdxxxxdxx");
					break;
				case Robot.EAST:
					robot.rotate(Rotate.LEFT);
					result.add("tl");
					result.add("xxaxx");
					break;
				case Robot.WEST:
					robot.rotate(Rotate.RIGHT);
					result.add("tr");
					result.add("xxdxx");
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
					result.add("trtr");
					result.add("xxdxxxxdxx");
					break;
				case Robot.SOUTH:
					robot.rotate(Rotate.LEFT);
					result.add("tl");
					result.add("xxaxx");
					break;
				case Robot.NORTH:
					robot.rotate(Rotate.RIGHT);
					result.add("tr");
					result.add("xxdxx");
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
					result.add("trtr");
					result.add("xxdxxxxdxx");
					break;
				case Robot.WEST:
					robot.rotate(Rotate.LEFT);
					result.add("tl");
					result.add("xxaxx");
					break;
				case Robot.EAST:
					robot.rotate(Rotate.RIGHT);
					result.add("tr");
					result.add("xxdxx");
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
					result.add("trtr");
					result.add("xxdxxxxdxx");
					break;
				case Robot.NORTH:
					robot.rotate(Rotate.LEFT);
					result.add("tl");
					result.add("xxaxx");
					break;
				case Robot.SOUTH:
					robot.rotate(Rotate.RIGHT);
					result.add("tr");
					result.add("xxdxx");
					break;
			}
		}
		if (result.isEmpty())
			result.add("");
			result.add("");
		return result;
	}

	public ArrayList<String> visitNode(int y, int x, int dir) {
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