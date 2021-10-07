package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
	public static final int maxY = 20;
	public static final int maxX = 20;

	private Cell cells[][];
	private int padding = 0;
	private ArrayList<Cell> pictureCellList = new ArrayList<Cell>(); //for Fastest Hamiltonian Path calculation

	/**
	 * Map constructor. Default Grid: y=20 by x=20. No padding.
	 */
	public Map() {
		// Initialize cells
		cells = new Cell[maxY + 2 * padding][maxX + 2 * padding];
		// Initialize each cell
		for (int y = maxY - 1 + 2 * padding; y >= 0; y--) {
			for (int x = 0; x < maxX + 2 * padding; x++) {
				cells[y][x] = new Cell(y, x);
			}
		}
	}

	/**
	 * Map constructor. Default Grid: y=20 by x=20. + padding each side for virtual movement
	 */
	public Map(int padding) {
		// Initialize cells
		this.padding = padding;
		cells = new Cell[maxY + 2 * padding][maxX + 2 * padding];
		// Initialize each cell
		for (int y = maxY - 1 + 2 * padding; y >= 0; y--) {
			for (int x = 0; x < maxX + 2 * padding; x++) {
				cells[y][x] = new Cell(y, x);
			}
		}
	}

	/**
	 * Map constructor with import. Default Grid: y=20 by x=20. + padding each side for virtual movement
	 * @param fileName
	 */
	public Map(int padding, String fileName) {
		this(padding);
		this.importMap(fileName);
	}

	/**
	 * Import Map from txt file.
	 * @param fileName
	 */
	public void importMap(String fileName) {
		try {
			String filePath = new File("").getAbsolutePath();
			Scanner s = new Scanner(new BufferedReader(new FileReader(filePath.concat("/presets/" + fileName))));

			while (s.hasNext()) {
				//+ 3 each side for virtual movement, so -3 when reading maps.
				for (int y = maxY - 1; y >= 0; y--) {
					for (int x = 0; x < maxX; x++) {
						char type = s.next().charAt(0);
						cells[y + padding][x + padding].setCellType(type);
						if (type == 'A' || type == 'B' || type == 'C' || type == 'D') {
							pictureCellList.add(cells[y + padding][x + padding]);
						}
					}
				}
			}

			s.close();
		} 
		catch (IOException e) {
			System.err.format("Import Map IOException: %s%n", e);
		}
	}

	/**
	 * Set a cell to be a type. This uses Coordinate.
	 * @param coordinate
	 * @param cellType Refer to Cell for more information.
	 */
	public void setCellType(Coordinate coordinate, char cellType) {
		int y = coordinate.getY();
		int x = coordinate.getX();
		setCellType(y, x, cellType);
	}

	/**
	 * Set a cell to be a type. This uses y and x instead.
	 * @param y
	 * @param x
	 * @param cellType Refer to Cell for more information.
	 */
	public void setCellType(int y, int x, char cellType) {
		cells[y][x].setCellType(cellType);
		boolean exist = false;

		if (cellType == Cell.NORTHWALL || cellType == Cell.EASTWALL 
			|| cellType == Cell.SOUTHWALL || cellType == Cell.WESTWALL) {
			for(Cell c: pictureCellList) {
				if (c.equals(cells[y][x])) {
					exist = true;
					break;
				}
			}
			if (!exist)
				pictureCellList.add(cells[y][x]);
		}
	}

	/**
	 * Delete a picture cell using Coordinate. This command is sent from Android.
	 * @param coordinate
	 */
	public void delPictureCell(Coordinate coordinate) {
		cells[coordinate.getY()][coordinate.getX()].setCellType('P');
		pictureCellList.remove(cells[coordinate.getY()][coordinate.getX()]);
	}

	/**
	 * Delete a picture cell using y and x. This command is sent from Android.
	 * @param y
	 * @param x
	 */
	public void delPictureCell(int y, int x) {
		cells[y][x].setCellType('P');
		pictureCellList.remove(cells[y][x]);
	}

	public Cell getCell(Coordinate coordinate) {
		return cells[coordinate.getY()][coordinate.getX()];
	}

	public ArrayList<Cell> getPictureCellList() {
		return pictureCellList;
	}
}