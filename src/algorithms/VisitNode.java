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
					return;		
				case Robot.NORTH:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					return;			
				case Robot.SOUTH:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					return;						
				case Robot.WEST:
					robot.moveForward(-1);
					return;
			}
		}
		if (x < curPos.getX()) {
			switch(curDir) {
				case Robot.EAST:
					robot.moveForward(-1);
					return;
					
				case Robot.NORTH:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					return;		
					
				case Robot.SOUTH:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					return;	
					
				case Robot.WEST:
					robot.moveForward(1);
					return;	
			}
		}
		if (y > curPos.getY()) {
			switch(curDir) {
				case Robot.EAST:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					return;	
				case Robot.NORTH:
					robot.moveForward(1);
					return;	
				case Robot.SOUTH:
					robot.moveForward(-1);
					
					return;	
				case Robot.WEST:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					return;	
			}
		}
		if (y < curPos.getY()) {
			switch(curDir) {
				case Robot.EAST:
					robot.rotate(Rotate.RIGHT);
					robot.moveForward(1);
					return;					
				case Robot.NORTH:
					robot.moveForward(-1);
					return;	
				case Robot.SOUTH:
					robot.moveForward(1);
					return;	
				case Robot.WEST:
					robot.rotate(Rotate.LEFT);
					robot.moveForward(1);
					return;	
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
						robot.moveForward(1);
						return "tr";						
					case Robot.SOUTH:
						robot.rotate(Rotate.LEFT);
						robot.moveForward(1);
						return "tl";						
					case Robot.WEST:
						robot.moveForward(-1);
						return "r";
				}
			}
			if (x < curPos.getX()) {
				switch(curDir) {
					case Robot.EAST:
						robot.moveForward(-1);
						return "r";
						
					case Robot.NORTH:
						robot.rotate(Rotate.LEFT);
						robot.moveForward(1);
						return "tl";		
						
					case Robot.SOUTH:
						robot.rotate(Rotate.RIGHT);
						robot.moveForward(1);
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
						robot.moveForward(1);
						return "tl";	
					case Robot.NORTH:
						robot.moveForward(1);
						return "f";	
					case Robot.SOUTH:
						robot.moveForward(-1);
						
						return "r";	
					case Robot.WEST:
						robot.rotate(Rotate.RIGHT);
						robot.moveForward(1);
						return "tr";	
				}
			}
			if (y < curPos.getY()) {
				switch(curDir) {
					case Robot.EAST:
						robot.rotate(Rotate.RIGHT);
						robot.moveForward(1);
						return "tr";	
					case Robot.NORTH:
						robot.moveForward(-1);
						return "r";	
					case Robot.SOUTH:
						robot.moveForward(1);
						return "f";	
					case Robot.WEST:
						robot.rotate(Rotate.LEFT);
						robot.moveForward(1);
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
						return "r";
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
			return "tr";
		}
		
		public String STMRotate(int direction) {
			curDir = robot.getcurDir();
			if (direction == Robot.NORTH) {
				switch(curDir) {
					case Robot.NORTH:
						break;
					case Robot.SOUTH:
						return "xdxxxxdx";
					case Robot.EAST:
						return "xxaxx";
					case Robot.WEST:
						return "xxdxx";
				}
			}
			if (direction == Robot.EAST) {
				switch(curDir) {
					case Robot.EAST:
						break;
					case Robot.WEST:
						return "xdxxxxdx";
					case Robot.SOUTH:
						return "xxaxx";
					case Robot.NORTH:
						return "xxdxx";
				}
			}
			if (direction == Robot.SOUTH) {
				switch(curDir) {
					case Robot.SOUTH:
						break;
					case Robot.NORTH:
						return "xdxxxxdx";
					case Robot.WEST:
						robot.rotate(Rotate.LEFT);
						return "xxaxx";
					case Robot.EAST:
						robot.rotate(Rotate.RIGHT);
						return "xxdxx";
				}
			}
			if (direction == Robot.WEST) {
				switch(curDir) {
					case Robot.WEST:
						break;
					case Robot.EAST:
						return "xdxxxxdx";
					case Robot.SOUTH:
						return "xxdxx";
					case Robot.NORTH:
						return "xxaxx";
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
						return "xxdx";						
					case Robot.SOUTH:
						return "xxax";						
					case Robot.WEST:
						return "x";
				}
			}
			if (x < curPos.getX()) {
				switch(curDir) {
					case Robot.EAST:
						return "x";
						
					case Robot.NORTH:
						return "xxax";		
						
					case Robot.SOUTH:
						return "xxdx";	
						
					case Robot.WEST:
						return "w";	
				}
			}
			if (y > curPos.getY()) {
				switch(curDir) {
					case Robot.EAST:
						return "xxax";	
					case Robot.NORTH:
						return "w";	
					case Robot.SOUTH:
						return "x";	
					case Robot.WEST:
						return "xxdx";	
				}
			}
			if (y < curPos.getY()) {
				switch(curDir) {
					case Robot.EAST:
						return "xxdx";	
					case Robot.NORTH:
						return "x";	
					case Robot.SOUTH:
						return "w";	
					case Robot.WEST:
						return "xxax";	
				}
			}
			return "";
		}

	
}