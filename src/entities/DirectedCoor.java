package entities;

public class DirectedCoor {
	//Coordination but with direction. Used for modified A* search.
	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

    private Coordinate curPos;
    private int dir = NORTH;		//Default is NORTH

    public DirectedCoor(Coordinate curPos, int dir) {
        this.curPos = curPos;
        this.dir = dir;
    }

    public DirectedCoor(int y, int x, int dir) {
        this.curPos = new Coordinate(y, x);
        this.dir = dir;
    }

    public Coordinate getCoor() {
		return curPos;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getY() {
		return curPos.getY();
	}

	public int getX() {
		return curPos.getX();
	}

	/**
	 * Object hash code contract for use in a HashMap.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getY();
		result = prime * result + this.getX();
		result = prime * result + this.dir;
		return result;
	}

    /**
	 * Object equals contract for use in a HashMap.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        DirectedCoor other = (DirectedCoor) obj;
		if (curPos == null) {
			if (other.getCoor() != null)
				return false;
		}
		else if (!curPos.equals(other.getCoor()))
			return false;
        if (this.dir != other.dir)
            return false;
		return true;
	}

	/**
	 * Object toString method.
	 */
	@Override
	public String toString() {
		return "Y: " + this.getY() + ", X: " + this.getX() + ", dir: " + this.dir;
	}
}
