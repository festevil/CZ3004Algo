package entities;

public class Coordinate {
	private int y, x;

	public Coordinate(int y, int x) {
		this.y = y;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	//Object hash code contract for use in a HashMap.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.y;
		result = prime * result + this.x;
		return result;
	}

	//Object equals contract for use in a HashMap.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (y != other.getY())
			return false;
		if (x != other.getX())
			return false;
		return true;
	}

	/**
	 * Object toString method.
	 */
	@Override
	public String toString() {
		return "Y: " + this.getY() + ", X: " + this.getX();
	}

	/**
	 * Object toString method, but with padding taken into account.
	 */
	public String toStringActual(int padding) {
		int tempY = this.getY() - padding;
		int tempX = this.getX() - padding;
		return "Y: " + tempY + ", X: " + tempX + ", type: ";
	}
}


