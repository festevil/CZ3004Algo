package entities;

public class Robot {

	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
	
	public static final int
		FRONT_LEFT = 0, FRONT_CENTER = 1, FRONT_RIGHT = 2,
		MIDDLE_LEFT = 3, MIDDLE_CENTER = 4, MIDDLE_RIGHT = 5,
		BACK_LEFT = 6, BACK_CENTER = 7, BACK_RIGHT = 8;

	public enum Rotate { RIGHT, LEFT };

	/* 6 sensors in total
	 * TYPE_PLACEMENT_DIRECTION */
	public static final int
		S_FL_N = 0, S_FC_N = 1, S_FR_N = 2, S_FR_E = 3,
		S_BL_W = 4, L_BL_W = 5;

	private boolean isRealRun;	// Real Run or Simulation (Whether to send movement command)
	private Coordinate curPos;	// MIDDLE_CENTER of Robot
	private int curDir;		// North, South, East, West
	

	/**
	 * Robot Constructor (Each Robot occupies 3 x 3 cells)
	 * Default Position: y=1 | x=1, Default Direction: EAST.
	 * @param isRealRun real or virtual robot
	*/
	public Robot(boolean isRealRun) {
		this.isRealRun = isRealRun;
		curPos = new Coordinate(1, 1);
		curDir = EAST;
	}

	/**
	 * Robot Constructor (Each Robot occupies 3 x 3 cells)
	 * @param startPos position of robot
	 * @param startDir direction of robot
	 * @param isRealRun real or virtual robot
	 */
	public Robot(Coordinate startPos, int startDir, boolean isRealRun) {
		this.isRealRun = isRealRun;

		int startY = startPos.getY();
		int startX = startPos.getX();
		boolean error = false;

		if (startY == 0 || startY == Map.maxY - 1)
			error = true;
		else if (startX == 0 || startX == Map.maxX - 1)
			error = true;

		if (error == true) {
			System.out.println("ERROR: Robot(y:" + startPos.getY() + " x:" + startPos.getX()
					+ ") cannot be initialised outside of map. Default position is assumed.");

			// Default starting position
			curPos = new Coordinate(1, 1);
			curDir = EAST;
		} 
		else {
			this.curPos = startPos;
			this.curDir = startDir;
		}
	}

	public Coordinate getcurPos() {
		return curPos;
	}

	public void setCurPos(Coordinate curPos) {
		this.curPos = curPos;
	}

	public void setCurPos(int y, int x) {
		this.curPos = new Coordinate(y, x);
	}

	public int getcurDir() {
		return curDir;
	}

	public void setCurDir(int curDir) {
		this.curDir = curDir;
	}

	/**
	 * Get the robot footprint.
	 */
	public Coordinate[] getFootprint() {
		Coordinate[] robotFootprint = new Coordinate[9];
		robotFootprint[MIDDLE_CENTER] = curPos;

		switch (curDir) {
			case NORTH:
				robotFootprint[FRONT_LEFT] = new Coordinate(curPos.getY() + 1, curPos.getX() - 1);
				robotFootprint[FRONT_CENTER] = new Coordinate(curPos.getY() + 1, curPos.getX());
				robotFootprint[FRONT_RIGHT] = new Coordinate(curPos.getY() + 1, curPos.getX() + 1);

				robotFootprint[MIDDLE_LEFT] = new Coordinate(curPos.getY(), curPos.getX() - 1);
				robotFootprint[MIDDLE_RIGHT] = new Coordinate(curPos.getY(), curPos.getX() + 1);

				robotFootprint[BACK_LEFT] = new Coordinate(curPos.getY() - 1, curPos.getX() - 1);
				robotFootprint[BACK_CENTER] = new Coordinate(curPos.getY() - 1, curPos.getX());
				robotFootprint[BACK_RIGHT] = new Coordinate(curPos.getY() - 1, curPos.getX() + 1);

				return robotFootprint;
			case SOUTH:
				robotFootprint[FRONT_LEFT] = new Coordinate(curPos.getY() - 1, curPos.getX() + 1);
				robotFootprint[FRONT_CENTER] = new Coordinate(curPos.getY() - 1, curPos.getX());
				robotFootprint[FRONT_RIGHT] = new Coordinate(curPos.getY() - 1, curPos.getX() - 1);

				robotFootprint[MIDDLE_LEFT] = new Coordinate(curPos.getY(), curPos.getX() + 1);
				robotFootprint[MIDDLE_RIGHT] = new Coordinate(curPos.getY(), curPos.getX() - 1);

				robotFootprint[BACK_LEFT] = new Coordinate(curPos.getY() + 1, curPos.getX() + 1);
				robotFootprint[BACK_CENTER] = new Coordinate(curPos.getY() + 1, curPos.getX());
				robotFootprint[BACK_RIGHT] = new Coordinate(curPos.getY() + 1, curPos.getX() - 1);

				return robotFootprint;
			case EAST:
				robotFootprint[FRONT_LEFT] = new Coordinate(curPos.getY() + 1, curPos.getX() + 1);
				robotFootprint[FRONT_CENTER] = new Coordinate(curPos.getY(), curPos.getX() + 1);
				robotFootprint[FRONT_RIGHT] = new Coordinate(curPos.getY() - 1, curPos.getX() + 1);

				robotFootprint[MIDDLE_LEFT] = new Coordinate(curPos.getY() + 1, curPos.getX());
				robotFootprint[MIDDLE_RIGHT] = new Coordinate(curPos.getY() - 1, curPos.getX());

				robotFootprint[BACK_LEFT] = new Coordinate(curPos.getY() + 1, curPos.getX() - 1);
				robotFootprint[BACK_CENTER] = new Coordinate(curPos.getY(), curPos.getX() - 1);
				robotFootprint[BACK_RIGHT] = new Coordinate(curPos.getY() - 1, curPos.getX() - 1);

				return robotFootprint;
			case WEST:
				robotFootprint[FRONT_LEFT] = new Coordinate(curPos.getY() - 1, curPos.getX() - 1);
				robotFootprint[FRONT_CENTER] = new Coordinate(curPos.getY(), curPos.getX() - 1);
				robotFootprint[FRONT_RIGHT] = new Coordinate(curPos.getY() + 1, curPos.getX() - 1);

				robotFootprint[MIDDLE_LEFT] = new Coordinate(curPos.getY() - 1, curPos.getX());
				robotFootprint[MIDDLE_RIGHT] = new Coordinate(curPos.getY() + 1, curPos.getX());

				robotFootprint[BACK_LEFT] = new Coordinate(curPos.getY() - 1, curPos.getX() + 1);
				robotFootprint[BACK_CENTER] = new Coordinate(curPos.getY(), curPos.getX() + 1);
				robotFootprint[BACK_RIGHT] = new Coordinate(curPos.getY() + 1, curPos.getX() + 1);

				return robotFootprint;
			default:
				return null;
		}
	}

	/**
	 * Robot forward by a specified number of steps.
	 * @param step number of steps
	 */
	public void moveForward(int steps) {
		int newPos;
		String warning = "WARNING: moveRobot() is going out of map boundary.";

		switch (curDir) {
			case NORTH:
				newPos = curPos.getY() + steps;
				// Prevents Robot from going out of map boundary
				if (newPos < Map.maxY - 1) {
					curPos.setY(newPos);
				} 
				else
					System.out.println(warning);
				break;
			case SOUTH:
				newPos = curPos.getY() - steps;
				// Prevents Robot from going out of map boundary
				if (newPos > 0) {
					curPos.setY(newPos);
				} 
				else
					System.out.println(warning);
				break;
			case EAST:
				newPos = curPos.getX() + steps;
				// Prevents Robot from going out of map boundary
				if (newPos < Map.maxX - 1) {
					curPos.setX(newPos);
				} 
				else
					System.out.println(warning);
				break;
			case WEST:
				newPos = curPos.getX() - steps;
				// Prevents Robot from going out of map boundary
				if (newPos > 0) {
					curPos.setX(newPos);
				} 
				else
					System.out.println(warning);
				break;
			default: // Do nothing
		}
	}

	/**
	 * Rotates the robot in the specified direction.
	 * @param direction RIGHT or LEFT
	 */
	public void rotate(Rotate direction) {
		switch (direction) {
			case RIGHT:	// Rotate clockwise
				float newDir2 = (curDir + 1) % 4;
				curDir = (int) newDir2;
				break;
			case LEFT:	// Rotate counter-clockwise
				float newDir = (curDir - 1) % 4;
				// Make it positive as Java will return negative modulus
				if (newDir < 0) newDir += 4;
				curDir = (int) newDir;
				break;
			default: // Do nothing
		}
	}
}
	