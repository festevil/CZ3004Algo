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
		startCoord = new Coordinate(1, 1);
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

	public void setPictureCell(Coordinate coordinate, char cellType) {
		cells[coordinate.getY()][coordinate.getX()].setCellType(cellType);
		pictureCellList.add(cells[coordinate.getY()][coordinate.getX()]);
	}

	public void setPictureCell(int y, int x, char cellType) {
		cells[y][x].setCellType(cellType);
		pictureCellList.add(cells[y][x]);
	}

	public void delPictureCell(Coordinate coordinate) {
		cells[coordinate.getY()][coordinate.getX()].setCellType('P');
		pictureCellList.add(cells[coordinate.getY()][coordinate.getX()]);
	}

	public void delPictureCell(int y, int x) {
		cells[y][x].setCellType('P');
		pictureCellList.add(cells[y][x]);
	}

	public Cell getCell(Coordinate coordinate) {
		return cells[coordinate.getY()][coordinate.getX()];
	}

	
	public ArrayList<Cell> getNeighborCell(Coordinate coordinate) {
		ArrayList<Cell> cellList = new ArrayList<>();
		for(int dy = -1; dy <= 1; dy++) {
			for(int dx = -1; dx < 1; dx++) {
				if (coordinate.getY() + dy >= 0 && coordinate.getY() + dy <= Map.maxY - 1)
					if (coordinate.getX() + dx >= 0 && coordinate.getX() + dx <= Map.maxX - 1)
						cellList.add(cells[coordinate.getY() + dy][coordinate.getX() + dx]);
			}
		}
		return cellList;
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
	
	/**
	 * Get P1 descriptors.
	 */
	public String getP1Descriptors() {
		String P1 = new String();

		P1 += "11";		// Padding sequence

		for (int y = 0; y < Map.maxY; y++) {
			for (int x = 0; x < Map.maxX; x++) {
				int cellType = this.getCell(new Coordinate(y, x)).getCellType();

				if (cellType == Cell.UNKNOWN)
					P1 += "0";
				else
					P1 += "1";
			}
		}

		P1 += "11";		// Padding sequence

		// Convert to Hexadecimal
		String hexString = new String();
		for (int i = 0; i < 304; i += 4) {
			String binOf4Bits = P1.substring(i, i + 4);
			int intOf4Bits = Integer.parseInt(binOf4Bits, 2);	// Binary String to Decimal Number
			hexString += Integer.toString(intOf4Bits, 16).toUpperCase();	// Decimal Number to Hex String
		}

		return hexString;
	}

	/**
	 * Get P2 descriptors.
	 */
	public String getP2Descriptors() {
		String P2 = new String();

		for (int y = 0; y < Map.maxY; y++) {
			for (int x = 0; x < Map.maxX; x++) {
				int cellType = this.getCell(new Coordinate(y, x)).getCellType();

				if (cellType != Cell.UNKNOWN) {
					if (cellType == Cell.WALL)
						P2 += "1";
					else
						P2 += "0";
				}
			}
		}

		// Normalise P2 Binary
		int remainder = P2.length() % 4;
		String lastBit = new String();
		String padding = new String();

		switch (remainder) {
		case 1:
			lastBit = P2.substring(P2.length() - 1);
			padding = "000";
			P2 = P2.substring(0, P2.length() - 1).concat(padding).concat(lastBit);
			break;
		case 2:
			lastBit = P2.substring(P2.length() - 2);
			padding = "00";
			P2 = P2.substring(0, P2.length() - 2).concat(padding).concat(lastBit);
			break;
		case 3:
			lastBit = P2.substring(P2.length() - 3);
			padding = "0";
			P2 = P2.substring(0, P2.length() - 3).concat(padding).concat(lastBit);
			break;
		default: // Do nothing
		}

		// Convert to Hexadecimal
		String hexString = new String();
		for (int i = 0; i < P2.length(); i += 4) {
			String binOf4Bits = P2.substring(i, i + 4);
			int intOf4Bits = Integer.parseInt(binOf4Bits, 2);	// Binary String to Decimal Number
			hexString += Integer.toString(intOf4Bits, 16).toUpperCase();	// Decimal Number to Hex String
		}

		return hexString;
	}
}