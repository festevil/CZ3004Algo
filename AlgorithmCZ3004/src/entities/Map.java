package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Cell;
import entities.PictureCell;

public class Map {
	public static final int maxY = 20;
	public static final int maxX = 20;

	private Cell cells[][];
	private Coordinate startCoord;
	private Coordinate checkpointCoord;
	private ArrayList<Cell> pictureCellList = new ArrayList<Cell>();

	/**
	 * Map constructor.
	 * 
	 * Default Grid: y=20 by x=20.
	 */
	public Map() {
		// Initialise cells
		cells = new Cell[maxY][maxX];
		startCoord = new Coordinate(1, 2);
		// Initialise each cell
		for (int y = maxY - 1; y >= 0; y--) {
			for (int x = 0; x < maxX; x++) {
				cells[y][x] = new Cell(y, x);
			}
		}
	}

	/**
	 * Map constructor with import.
	 * 
	 * Default Grid: y=20 by x=20.
	 * 
	 * @param fileName
	 */
	public Map(String fileName) {
		this();
		this.importMap(fileName);
	}

	/**
	 * Import Map from txt file.
	 * 
	 * @param fileName (E.g. "empty.txt")
	 */
	public void importMap(String fileName) {
		try {

			String filePath = new File("").getAbsolutePath();
			Scanner s = new Scanner(new BufferedReader(new FileReader(filePath.concat("/presets/" + fileName))));

			while (s.hasNext()) {
				for (int y = maxY - 1; y >= 0; y--) {
					for (int x = 0; x < maxX; x++) {
						char type = s.next().charAt(0);
						cells[y][x].setCellType(type);
						if (type == 'A' || type == 'B' || type == 'C' || type == 'D') {
							switch (type) {
							case 'A':
								setPictureCell(y,x,0);
								cells[y][x].setCellType(type);
								break;
							case 'B':
								setPictureCell(y,x,1);
								cells[y][x].setCellType(type);
								break;
							case 'C':
								setPictureCell(y,x,2);
								cells[y][x].setCellType(type);
								break;
							case 'D':
								setPictureCell(y,x,3);
								cells[y][x].setCellType(type);
								break;
							}

						}

					}
				}
			}

			s.close();
		} catch (IOException e) {
			System.err.format("Import Map IOException: %s%n", e);
		}
	}

	/**
	 * Returns a cell on the map.
	 * 
	 * @param coordinate
	 * @return
	 */
	public Cell getCell(Coordinate coordinate) {
		return cells[coordinate.getY()][coordinate.getX()];
	}

	/**
	 * Sets PictureCell on Map.
	 * 
	 * @param y
	 * @param x
	 * @param dir
	 */
	public void setPictureCell(int y, int x, float dir) {
		PictureCell curPictureCell = new PictureCell(y, x, dir);
		cells[y][x] = curPictureCell;
		pictureCellList.add(curPictureCell);
	}

	/**
	 * Get PictureCellList on Map.
	 */
	public ArrayList<Cell> getPictureCellList() {
		return pictureCellList;
	}

	/**
	 * Set <tt>Coordinate</tt> of the checkpoint.
	 * 
	 */
	public void setCheckpoint(Coordinate check) {
		this.cells[check.getY()][check.getX()].setCellType(Cell.CHECKPOINT);
		this.checkpointCoord = check;
	}

	/**
	 * Get <tt>Coordinate</tt> of the checkpoint.
	 * 
	 * @return
	 */
	public Coordinate getCheckpoint() {
		return this.checkpointCoord;
	}

	/**
	 * Sets start coordinates.
	 * 
	 * @param y
	 * @param x
	 */
	public void setStartCoord(int y, int x) {
		startCoord.setX(x);
		startCoord.setY(y);
	}

	/**
	 * 
	 * @return <tt>Coordinate</tt> of the start position.
	 */
	public Coordinate getStartCoord() {
		return this.startCoord;
	}
	
	public void finalPathReveal(ArrayList<Node> finalPath) {
		/* Clear any remnants of previously computed final path */
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				if (cells[y][x].getCellType() == Cell.FINAL_PATH)
					cells[y][x].setCellType(Cell.PATH);
			}
		}

		/* Mark cellType as FINAL_PATH */
		for (int i = 0; i < finalPath.size(); i++) {
			Node n = finalPath.get(i);
			Cell cell = this.cells[n.getCell().getY()][n.getCell().getX()];
			cell.setCellType(Cell.FINAL_PATH);
		}
	}

}