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
	private Coordinate startCoord;
	private ArrayList<Cell> pictureCellList = new ArrayList<Cell>(); //for Fastest Hamiltonian Path calculation

	//Map constructor. Default Grid: y=20 by x=20.
	public Map() {
		// Initialize cells
		cells = new Cell[maxY][maxX];
		startCoord = new Coordinate(1, 2);
		// Initialize each cell
		for (int y = maxY - 1; y >= 0; y--) {
			for (int x = 0; x < maxX; x++) {
				cells[y][x] = new Cell(y, x);
			}
		}
	}

	//Map constructor with import. Default Grid: y=20 by x=20.
	public Map(String fileName) {
		this();
		this.importMap(fileName);
	}

	//Import Map from txt file.
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
							pictureCellList.add(cells[y][x]);
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

	public Cell getCell(Coordinate coordinate) {
		return cells[coordinate.getY()][coordinate.getX()];
	}

	public ArrayList<Cell> getPictureCellList() {
		return pictureCellList;
	}

	public Coordinate getStartCoord() {
		return this.startCoord;
	}

	public void setStartCoord(int y, int x) {
		startCoord.setX(x);
		startCoord.setY(y);
	}
}