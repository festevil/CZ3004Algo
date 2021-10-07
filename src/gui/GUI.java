package gui;

import javax.swing.*;

import entities.Cell;
import entities.Coordinate;
import entities.Map;
import entities.Robot;

import Main.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

	private static final long serialVersionUID = -3014581757826180255L;

	private Container mainContainer;
	private JPanel mapPanel, ctrlPanel;

	private JLabel[][] cellsUI;
	private Robot robot;
	private Map map;

	//Constructor for GUI as a JFrame.
	public GUI(Robot robot, Map map) {
		super("Test run");

		// Initialise class variables
		this.robot = robot;
		this.map = map;

		// Initialise overall layout
		initLayout();

		this.setVisible(true);
	}

	//Ensures GUI is showing the latest instance of Robot and Map.
	public void refreshGUI(Robot robot, Map map) {
		this.robot = robot;
		this.map = map;

		mapPanel.repaint();	// Repaint mapPanel to show updated Robot position
	}

	//(Optional) Set mode of GUI visually.
	public void setModeColour(boolean connected) {
		if (connected)
			mainContainer.setBackground(new Color(115, 221, 141));
		else
			mainContainer.setBackground(new Color(198, 105, 105));
	}

	//Initialise the Container, JPanels and cellsUI.
	private void initLayout() {
		mapPanel = new MapPanel();
		mapPanel.setOpaque(false);
		mapPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		populateMapPanel();	// Populate Map Panel with cells from _map

		ctrlPanel = new JPanel();
		ctrlPanel.setOpaque(false);
		ctrlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		ctrlPanel.setLayout(new GridLayout(10, 1, 0, 10));
		populateCtrlPanel(); // Populate Control Panel with buttons

		mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout(0, 0));
		mainContainer.add(mapPanel, BorderLayout.WEST);
		mainContainer.add(ctrlPanel);
		this.setSize(900, 800); // Width, Height
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Exit mainUI on close
		
		// Launch mainUI to the right of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 1 - getSize().width / 1, dim.height / 2 - getSize().height / 2 - 20);
	}

	/**
	 * Custom class for MapPanel as a JPanel.
	 * Responsible for producing a responsive grid map.
	*/
	private class MapPanel extends JPanel {
		private static final long serialVersionUID = 3896801036058623157L;

		public MapPanel() {
			// Additional +1 row & +1 col for axis labelling
			super(new GridLayout(Map.maxY + 1 + 2 * Main.padding, Map.maxX + 1 + 2 * Main.padding, 2, 2));
		}

		/**
		 * Override the getPreferredSize() of JPanel to always ensure that MapAndRobotJPanel has a dynamic preferred
		 * size so that it maintains the original aspect ratio. In this case, it will return a JPanel that is always
		 * sized with an aspect ratio of 3:4 (15 columns by 20 rows).
		 * 
		 * Referenced from: https://stackoverflow.com/questions/21142686/making-a-robust-resizable-swing-chess-gui
		 */
		@Override
		public final Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			Component c = getParent();

			if (c == null)
				return d;

			else {
				/**
				 * Final Dimension should be 16:21 (W:H) aspect ratio for 16 cells by 21 cells
				 * (including axis labelling)
				 * Use of double for more accuracy -> then round to nearest integer
				*/
				double numOfCellsWidth = Map.maxX + 1 + 2 * Main.padding;	// 16 = 15 + 1
				double numOfCellsHeight = Map.maxY + 1 + 2 * Main.padding;	// 21 = 20 + 1

				int prefHeight = (int) c.getHeight();
				int prefWidth = (int) Math.round(prefHeight / numOfCellsHeight * numOfCellsWidth);

				return (new Dimension(prefWidth, prefHeight));
			}
		}
	}

	/**
	 * Populate cells and robot location in the mapPanel.
	 * Responsible for colouring cells by cellType and painting of cells occupied by robot.
	 */
	private void populateMapPanel() {
		cellsUI = new JLabel[Map.maxY + 2 * Main.padding][Map.maxX + 2 * Main.padding];

		// Populate Map Panel
		for (int y = Map.maxY + 2 * Main.padding; y >= 0; y--) { // Additional loop for axis labelling
			for (int x = 0; x <= Map.maxX + 2 * Main.padding; x++) {
				int actualY = y - 1;
				int actualX = x - 1;

				JLabel newCell = new JLabel("", JLabel.CENTER) {
					private static final long serialVersionUID = -4788108468649278480L;

					/**
					 * Paint robot, visited, etc. on the cells.
					 */
					@Override
					public void paintComponent(Graphics g) {
						super.paintComponent(g);

						/* Don't paint on axis labelling */
						if (actualY >= 0 && actualY < Map.maxY + 2 * Main.padding) {
							if (actualX >= 0 && actualX < Map.maxX + 2 * Main.padding) {
								Cell curCell = map.getCell(new Coordinate(actualY, actualX));
								
								paintRobot(this.getWidth(), actualY, actualX, robot.getFootprint(), g);

								setCellBG(curCell, this);
								setCellBorder(curCell, this);
								labelPermanentFlag(curCell, this);
							}
						}
					}
				};

				newCell.setPreferredSize(new Dimension(40, 40)); // Ensure cell is a square
				newCell.setOpaque(true);

				// If it's the first row / col, perform axis labelling
				if (y == 0 && x == 0) {
					newCell.setText("y/x");
					mapPanel.add(newCell);
				} 
				else if (x == 0) {
					newCell.setText(Integer.toString(actualY - Main.padding));
					mapPanel.add(newCell);
				} 
				else if (y == 0) {
					newCell.setText(Integer.toString(actualX - Main.padding));
					mapPanel.add(newCell);
				}

				// Actual Map Population
				else {
					newCell.setOpaque(true);
					newCell.setBackground(cellColour(map.getCell(new Coordinate(y - 1, x - 1))));
					
					cellsUI[actualY][actualX] = newCell;
					mapPanel.add(cellsUI[actualY][actualX]);
				}
			}
		}
	}

	//Populate Labels and Buttons in the ctrlPanel.
	private void populateCtrlPanel() {
		// Display Mode
		String mode = "Simulation";
		ctrlPanel.add(new JLabel("MODE: " + mode, JLabel.CENTER));

		// Explore (per step) button
		JButton moveStep = new JButton("MoveStep");
		moveStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.runForwardStep();
			}
		});

		JButton RotateLeft = new JButton("Rotate Left");
		RotateLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.rotateleft();
			}
		});
		
		JButton RotateRight = new JButton("Rotate Right");
		RotateRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.rotateright();
			}
		});
		
		JButton fastestPath = new JButton("Run Fastest Path");
		fastestPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.runShowFastestPath();
			}
		});
		
		ctrlPanel.add(new JLabel("=== Manual Control ===", JLabel.CENTER));
		ctrlPanel.add(moveStep);
		ctrlPanel.add(RotateLeft);
		ctrlPanel.add(RotateRight);
		ctrlPanel.add(new JLabel("=== Automatic Fastest Path ===", JLabel.CENTER));
		ctrlPanel.add(fastestPath);
	}

	//Paint Robot with provided Graphics g if actualY and actualX matches robotFootprint.
	private void paintRobot(int width, int actualY, int actualX, Coordinate[] robotFootprint, Graphics g) {
		width /= 2;	// For a responsive UI

		// Paint FRONT_LEFT of Robot
		if (actualY == robotFootprint[Robot.FRONT_LEFT].getY() && actualX == robotFootprint[Robot.FRONT_LEFT].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
			g.drawOval(width / 4, width / 4, (int) (width * 1.5), (int) (width * 1.5));	// Indicates sensor
		}

		// Paint FRONT_CENTER of Robot
		if (actualY == robotFootprint[Robot.FRONT_CENTER].getY()
				&& actualX == robotFootprint[Robot.FRONT_CENTER].getX()) {
			g.setColor(Color.DARK_GRAY);
			g.fillOval(width / 2, width / 2, width, width);
			g.drawOval(width / 4, width / 4, (int) (width * 1.5), (int) (width * 1.5));	// Indicates sensor
		}

		// Paint FRONT_RIGHT of Robot
		if (actualY == robotFootprint[Robot.FRONT_RIGHT].getY()
				&& actualX == robotFootprint[Robot.FRONT_RIGHT].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
			g.drawOval(width / 4, width / 4, (int) (width * 1.5), (int) (width * 1.5));	// Indicates sensor
		}

		// Paint MIDDLE_LEFT of Robot
		if (actualY == robotFootprint[Robot.MIDDLE_LEFT].getY()
				&& actualX == robotFootprint[Robot.MIDDLE_LEFT].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
		}

		// Paint MIDDLE_CENTER of Robot
		if (actualY == robotFootprint[Robot.MIDDLE_CENTER].getY()
				&& actualX == robotFootprint[Robot.MIDDLE_CENTER].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
		}

		// Paint MIDDLE_RIGHT of Robot
		if (actualY == robotFootprint[Robot.MIDDLE_RIGHT].getY()
				&& actualX == robotFootprint[Robot.MIDDLE_RIGHT].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
		}

		// Paint BACK_LEFT of Robot
		if (actualY == robotFootprint[Robot.BACK_LEFT].getY() && actualX == robotFootprint[Robot.BACK_LEFT].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
			g.drawOval(width / 4, width / 4, (int) (width * 1.5), (int) (width * 1.5));	// Indicates sensor
		}

		// Paint BACK_CENTER of Robot
		if (actualY == robotFootprint[Robot.BACK_CENTER].getY() && actualX == robotFootprint[Robot.BACK_CENTER].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
		}

		// Paint BACK_RIGHT of Robot
		if (actualY == robotFootprint[Robot.BACK_RIGHT].getY() && actualX == robotFootprint[Robot.BACK_RIGHT].getX()) {
			g.setColor(new Color(127, 204, 196));
			g.fillOval(width / 2, width / 2, width, width);
			g.drawOval(width / 4, width / 4, (int) (width * 1.5), (int) (width * 1.5));	// Indicates sensor
		}
	}

	

	//Set background colour of cell.
	private void setCellBG(Cell curCell, JLabel jl) {
		jl.setBackground(cellColour(curCell));
	}

	//Set matte border on cell. Used for PictureCell colouring.
	private void setCellBorder(Cell curCell, JLabel jl) {
		switch(curCell.getCellType()) {
			case Cell.NORTHWALL:
				jl.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, Color.red));
				break;
			case Cell.EASTWALL:
				jl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 10, Color.red));
				break;
			case Cell.SOUTHWALL:
				jl.setBorder(BorderFactory.createMatteBorder(0, 0, 10, 0, Color.red));
				break;
			case Cell.WESTWALL:
				jl.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red));
				break;
			case Cell.PATH:
				jl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
				break;
		}
	}

	//Cell colour rule based on cellType.
	private Color cellColour(Cell cell) {
		switch (cell.getCellType()) {
			case Cell.START:
				return Color.YELLOW;
			case Cell.GOAL:
				return Color.GREEN;
			case Cell.WALL:
				return Color.BLACK;
			case Cell.PATH:
				return Color.WHITE;
			default: // Unknown cells, or picture cells
				return Color.BLACK;
		}
	}

	//Label permanent flag as "P" on GUI.
	private void labelPermanentFlag(Cell curCell, JLabel jl) {
		/* Show isPermanent flag */
		if (curCell.isPermanentCellType()) {
			jl.setForeground(Color.GRAY);
			jl.setText("P");
		} 
		else
			jl.setText("");
	}
}
