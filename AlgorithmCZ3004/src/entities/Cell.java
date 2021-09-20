package entities;

public class Cell extends Coordinate {
	public static final char 
		WALL = 'W', START = 'S', GOAL = 'G', PATH = 'P', //other cells
		NORTHWALL = 'A', EASTWALL = 'B', SOUTHWALL = 'C', WESTWALL = 'D'; //picture cells
	private char cellType = PATH; // cell type listed above, default is PATH
	private boolean permanentCellType = false; // permanent cell type, do not change after set, default is false
	

	public Cell(int y, int x) {
		super(y, x);
	}

	public Cell(int y, int x, char cellType) {
		super(y, x);
		this.cellType = cellType;
		if (cellType == START || cellType == GOAL) 
			this.permanentCellType = true; /* START and GOAL cellTypes should be permanent */
	}

	public char getCellType() {
		return cellType;
	}

	public void setCellType(char cellType) {
		/* START and GOAL cellTypes should be permanent */
		// For cases where sensor falsely detects a wall in START or GOAL area
		if (cellType == START || cellType == GOAL) {
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