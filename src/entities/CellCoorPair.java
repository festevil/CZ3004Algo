package entities;

public class CellCoorPair {
    private Cell cell;
    private DirectedCoor directedCoor;

    public CellCoorPair(Cell cell, DirectedCoor directedCoor) {
        this.cell = cell;
        this.directedCoor = directedCoor;
    }

    public DirectedCoor getCoor() {
		return directedCoor;
	}

    public Cell getCell() {
		return cell;
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
        CellCoorPair other = (CellCoorPair) obj;
		if (!other.cell.equals(this.cell))
			return false;
        if (!other.directedCoor.equals(this.directedCoor))
            return false;
		return true;
	}
}
