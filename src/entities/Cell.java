package entities;

public class Cell extends Coordinate {
	public static final char WALL = 'W', START = 'S', GOAL = 'G', PATH = 'P',	//other cells
		NORTHWALL = 'A', EASTWALL = 'B', SOUTHWALL = 'C', WESTWALL = 'D';		//picture cells
	private char cellType = PATH;				// cell type listed above, default is PATH
	private boolean permanentCellType = false;	// permanent cell type, do not change after set, default is false
	

	public Cell(int y, int x) {
		super(y, x);
	}

	public Cell(int y, int x, char cellType) {
		super(y, x);
		this.cellType = cellType;
		if (cellType == START || cellType == GOAL) 
			this.permanentCellType = true;	/* START and GOAL cellTypes should be permanent */
	}

	public char getCellType() {
		return cellType;
	}

	public void setCellType(char cType) {
		/** 
		 * START and GOAL cellTypes should be permanent
		 * For cases where sensor falsely detects a wall in START or GOAL area
		*/
		if (cType == START || cType == GOAL) {
			this.permanentCellType = true;
			this.cellType = cType;
		}

		/* Can only set cellType if permanent flag is not set */
		else if (this.permanentCellType == false) {
			this.cellType = cType;
		}
	}

	public boolean isPermanentCellType() {
		return permanentCellType;
	}

	public void setPermanentCellType(boolean permanentCellType) {
		this.permanentCellType = permanentCellType;
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
		Cell other = (Cell) obj;
		if (this.getX() != other.getX())
			return false;
		if (this.getY() != other.getY())
			return false;
		if (this.cellType != other.getCellType())
			return false;
		if (this.permanentCellType != other.isPermanentCellType())
			return false;
		return true;
	}
	
	/**
	 * Object toString method.
	 */
	@Override
	public String toString() {
		return "Y: " + this.getY() + ", X: " + this.getX() + ", type: " + this.cellType;
	}

	/**
	 * Object toString method, but with padding taken into account.
	 */
	public String toStringActual(int padding) {
		int tempY = this.getY() - padding;
		int tempX = this.getY() - padding;
		return "Y: " + tempY + ", X: " + tempX + ", type: " + this.cellType;
	}
}