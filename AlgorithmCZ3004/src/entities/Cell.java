package entities;

public class Cell extends Coordinate {
	public static final char WALL = 'W', START = 'S', CHECKPOINT = 'O', GOAL = 'G', PATH = 'P', FINAL_PATH = 'F',
			NORTHWALL = 'A', EASTWALL = 'B', SOUTHWALL='C',WESTWALL='D';
	private char cellType; // Cell type listed above
	private boolean permanentCellType; // Permanent cell type, do not change after set
	

	/**
	 * <tt>Cell</tt> constructor which extends <tt>Coordinate</tt>.
	 * 
	 * @param y
	 * @param x
	 */
	public Cell(int y, int x) {
		super(y, x); // Coordinate constructor to set Y and X
		this.cellType = PATH; // Default to PATH
		this.permanentCellType = false; // Assume not permanent yet
	}

	/**
	 * Get the current cellType.
	 * 
	 * @return
	 */
	public char getCellType() {
		return cellType;
	}

	/**
	 * Set the cellType.
	 * 
	 * @param cellType
	 */
	public void setCellType(char cellType) {
		if (cellType == FINAL_PATH) {
			this.cellType = cellType;
		}

		/* START and GOAL cellTypes should be permanent */
		// For cases where sensor falsely detects a wall in START, CHECKPOINT or GOAL area
		else if (cellType == START || cellType == GOAL || cellType == CHECKPOINT) {
			this.permanentCellType = true;
			this.cellType = cellType;
		}

		/* Can only set cellType if permanent flag is not set */
		else if (this.permanentCellType == false) {
			this.cellType = cellType;
		}
	}
	public boolean isPermanentCellType() {
		return permanentCellType;
	}

	public void setPermanentCellType(boolean permanentCellType) {
		this.permanentCellType = permanentCellType;
	}
}