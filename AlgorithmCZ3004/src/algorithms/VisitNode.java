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
		public String OneStepAndroid(int x, int y) {
			curPos = robot.getcurPos();
			curDir = robot.getcurDir();
			if (x > curPos.getX()) {
				switch(curDir) {
					case Robot.EAST:
						robot.moveForward(1);
						return "f";						
					case Robot.NORTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";						
					case Robot.SOUTH:
						robot.rotate(Rotate.LEFT);
						return "tl";						
					case Robot.WEST:
						robot.rotate(Rotate.RIGHT);
						return "tr";
				}
			}
			if (x < curPos.getX()) {
				switch(curDir) {
					case Robot.EAST:
						robot.rotate(Rotate.RIGHT);
						return "tr";
						
					case Robot.NORTH:
						robot.rotate(Rotate.LEFT);
						return "tl";		
						
					case Robot.SOUTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";	
						
					case Robot.WEST:
						robot.moveForward(1);
						return "f";	
				}
			}
			if (y > curPos.getY()) {
				switch(curDir) {
					case Robot.EAST:
						robot.rotate(Rotate.LEFT);
						return "tl";	
					case Robot.NORTH:
						robot.moveForward(1);
						return "f";	
					case Robot.SOUTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";	
					case Robot.WEST:
						robot.rotate(Rotate.RIGHT);
						return "tr";	
				}
			}
			if (y < curPos.getY()) {
				switch(curDir) {
					case Robot.EAST:
						robot.rotate(Rotate.RIGHT);
						return "tr";	
					case Robot.NORTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";	
					case Robot.SOUTH:
						robot.moveForward(1);
						return "f";	
					case Robot.WEST:
						robot.rotate(Rotate.LEFT);
						return "tl";	
				}
			}
			return "";
		}
		public String AndroidRotate(int direction) {
			curDir = robot.getcurDir();
			if (direction == Robot.NORTH) {
				switch(curDir) {
					case Robot.NORTH:
						break;
					case Robot.SOUTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";
					case Robot.EAST:
						robot.rotate(Rotate.LEFT);
						return "tl";
					case Robot.WEST:
						robot.rotate(Rotate.RIGHT);
						return "tr";
				}
			}
			if (direction == Robot.EAST) {
				switch(curDir) {
					case Robot.EAST:
						break;
					case Robot.WEST:
						robot.rotate(Rotate.RIGHT);
						return "tr";
					case Robot.SOUTH:
						robot.rotate(Rotate.LEFT);
						return "tl";
					case Robot.NORTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";
				}
			}
			if (direction == Robot.SOUTH) {
				switch(curDir) {
					case Robot.SOUTH:
						break;
					case Robot.NORTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";
					case Robot.WEST:
						robot.rotate(Rotate.LEFT);
						return "tl";
					case Robot.EAST:
						robot.rotate(Rotate.RIGHT);
						return "tr";
				}
			}
			if (direction == Robot.WEST) {
				switch(curDir) {
					case Robot.WEST:
						break;
					case Robot.EAST:
						robot.rotate(Rotate.RIGHT);
						return "tr";
					case Robot.SOUTH:
						robot.rotate(Rotate.RIGHT);
						return "tr";
					case Robot.NORTH:
						robot.rotate(Rotate.LEFT);
						return "tl";
				}
			}
			return "";
		}
		
		
		
		
		
		public String STMRotate(int direction) {
			curDir = robot.getcurDir();
			if (direction == Robot.NORTH) {
				switch(curDir) {
					case Robot.NORTH:
						break;
					case Robot.SOUTH:
						return "d";
					case Robot.EAST:
						return "a";
					case Robot.WEST:
						return "d";
				}
			}
			if (direction == Robot.EAST) {
				switch(curDir) {
					case Robot.EAST:
						break;
					case Robot.WEST:
						return "d";
					case Robot.SOUTH:
						return "a";
					case Robot.NORTH:
						return "d";
				}
			}
			if (direction == Robot.SOUTH) {
				switch(curDir) {
					case Robot.SOUTH:
						break;
					case Robot.NORTH:
						return "d";
					case Robot.WEST:
						robot.rotate(Rotate.LEFT);
						return "a";
					case Robot.EAST:
						robot.rotate(Rotate.RIGHT);
						return "d";
				}
			}
			if (direction == Robot.WEST) {
				switch(curDir) {
					case Robot.WEST:
						break;
					case Robot.EAST:
						return "d";
					case Robot.SOUTH:
						return "d";
					case Robot.NORTH:
						return "a";
				}
			}
			return "";
		}
		public String OneStepSTM(int x, int y) {
			curPos = robot.getcurPos();
			curDir = robot.getcurDir();
			if (x > curPos.getX()) {
				switch(curDir) {
					case Robot.EAST:
						return "w";						
					case Robot.NORTH:
						return "d";						
					case Robot.SOUTH:
						return "a";						
					case Robot.WEST:
						return "d";
				}
			}
			if (x < curPos.getX()) {
				switch(curDir) {
					case Robot.EAST:
						return "d";
						
					case Robot.NORTH:
						return "a";		
						
					case Robot.SOUTH:
						return "d";	
						
					case Robot.WEST:
						return "w";	
				}
			}
			if (y > curPos.getY()) {
				switch(curDir) {
					case Robot.EAST:
						return "a";	
					case Robot.NORTH:
						return "w";	
					case Robot.SOUTH:
						return "d";	
					case Robot.WEST:
						return "d";	
				}
			}
			if (y < curPos.getY()) {
				switch(curDir) {
					case Robot.EAST:
						return "d";	
					case Robot.NORTH:
						return "d";	
					case Robot.SOUTH:
						return "w";	
					case Robot.WEST:
						return "a";	
				}
			}
			return "";
		}

	
}
