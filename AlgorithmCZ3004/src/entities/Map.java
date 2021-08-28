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

	/**
	 * Map constructor.
	 * 
	 * Default Grid: y=20 by x=15.
	 */
	public Map() {
		// Initialise cells
		cells = new Cell[maxY][maxX];
		startCoord = new Coordinate(1,2);
		// Initialise each cell
		for (int y = maxY - 1; y >= 0; y--) {
			for (int x = 0; x < maxX; x++) {
				cells[y][x] = new Cell(y, x);
			}
		}
	}
	
	public void setPictureCell(int y, int x, float dir) {
		cells[y][x] = new PictureCell(y,x,dir);
	}
	/**
	 * Sets start coordinates.
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
	

}