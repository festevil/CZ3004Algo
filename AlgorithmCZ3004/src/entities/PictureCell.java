package entities;

public class PictureCell extends Cell {
	public float direction;
	/**
	 * constructor for the PictureCell Class. Used to identify blocks with images on them, 
	 * and the direction they are facing.
	 * Direction = 0 means they are facing north, and Direction = 90 means they are
	 * facing south. 
	 * @param x
	 * @param y
	 */
	public PictureCell(int y, int x) {
		super(y,x);
		direction = 0;
	}
	public PictureCell(int y, int x, float direct) {
		super(y,x);
		direction = direct;
	}
	/**
	 * Set the direction of PictureCell.
	 * @param direct
	 */
	public void setdirection(float direct) {
		direction = direct;
	}
	/**
	 * Get the direction of PictureCell.
	 * @return
	 */
	public float getdirection() {
		return direction;
	}

}
