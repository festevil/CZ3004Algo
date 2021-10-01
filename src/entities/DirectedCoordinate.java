package entities;

public class DirectedCoordinate extends Coordinate {
	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    private int dir;

	public DirectedCoordinate(int y, int x) {
		super(y, x);
	}

	public DirectedCoordinate(int y, int x, int dir) {
		super(y, x);
		this.dir = dir;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
}
