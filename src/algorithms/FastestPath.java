package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

import entities.Cell;
import entities.Coordinate;
import entities.DirectedCoordinate;
import entities.Map;
import entities.Node;
import entities.Robot;

public class FastestPath {

	private HashMap<Coordinate, Node> nodes = new HashMap<Coordinate, Node>();
	private HashMap<Node, ArrayList<Node>> edges = new HashMap<Node, ArrayList<Node>>();

	private Coordinate startCoord, goalCoord;

	private ArrayList<Node> finalPath = new ArrayList<Node>();

	public FastestPath(Map curMap, Coordinate startCoord, Coordinate goalCoord) {
		this.startCoord = startCoord;
		this.goalCoord = goalCoord;
		initialization(curMap);
	}

	public FastestPath(Map curMap, DirectedCoordinate startCoord, DirectedCoordinate goalCoord) {
		this.startCoord = new Coordinate(startCoord.getY(), startCoord.getX());
		this.goalCoord = new Coordinate(goalCoord.getY(), goalCoord.getX());
		initialization(curMap);
	}

	public FastestPath(Map curMap, Cell startCell, Cell goalCell) {
		this.startCoord = new Coordinate(startCell.getY(), startCell.getX());
		this.goalCoord = new Coordinate(goalCell.getY(), goalCell.getX());
		initialization(curMap);
	}

	public void initialization(Map curMap) {
		// Step 1: Create graph of nodes with heuristic
		for (int y = 1; y <= Map.maxY - 2; y++) {
			for (int x = 1; x <= Map.maxX - 2; x++) {
				Coordinate curPos = new Coordinate(y, x);

				Robot robot = new Robot(curPos, Robot.EAST, false);
				Coordinate[] robotFootprint = robot.getFootprint();

				boolean hasWall = false;

				for (int i = 0; i < robotFootprint.length; i++) {
					ArrayList<Cell> cellList = curMap.getNeighborCell(robotFootprint[i]);
					for (Cell c: cellList) {
						if (c.getCellType() == Cell.WALL || c.getCellType() == Cell.NORTHWALL ||
						c.getCellType() == Cell.EASTWALL || c.getCellType() == Cell.SOUTHWALL ||
						c.getCellType() == Cell.WESTWALL) {
							hasWall = true;
							break;
						}
					}
				}

				if (hasWall == false) {
					int heuristic;
					int y2 = goalCoord.getY();
					int x2 = goalCoord.getX();

					heuristic = (int) Math.round(Math.sqrt(Math.pow((y2 - y), 2) + Math.pow((x2 - x), 2)));

					Node thisNode = new Node(heuristic, curMap.getCell(curPos));
					nodes.put(curPos, thisNode);
				}
			}
		}

		// Step 2: Link nodes with edges
		for (HashMap.Entry<Coordinate, Node> entry : nodes.entrySet()) {
			ArrayList<Node> neighbours = getNeighbours(entry.getValue());
			edges.put(entry.getValue(), neighbours);
		}
	}

	public ArrayList<Node> runAStar() {
		if (!this.finalPath.isEmpty()) {
			return this.finalPath;
		}
		
		PriorityQueue<Node> orderedFrontier = new PriorityQueue<Node>();	// Frontier that is sorted
		HashMap<Node, Node> childParent = new HashMap<Node, Node>();		// Child, Parent (Current, Previous)

		Node startNode = this.nodes.get(startCoord);	// Get Start Node
		Node goalNode = this.nodes.get(goalCoord);		// Get Goal Node
		startNode.setDistanceToStart(0);				// Set Start Node total cost to 0
		orderedFrontier.add(startNode);					// Add it to the frontier

		Node curNode;
		while (!orderedFrontier.isEmpty()) {
			curNode = orderedFrontier.poll();

			if (!curNode.isVisited()) {
				curNode.setVisited(true);

				// Check if it is Goal Node, BREAK!
				if (curNode.equals(goalNode)) {
					this.finalPath = genFinalPath(goalNode, childParent);
					return this.finalPath;
				}

				// Check neighbours
				ArrayList<Node> neighbours = getNeighbours(curNode);
				for (int i = 0; i < neighbours.size(); i++) {
					Node neighbourNode = neighbours.get(i);

					// Only traverse currNeighbour if it is unvisited
					if (!neighbourNode.isVisited()) {
						// Determine weight based on actual robot movement
						int weight = determineWeight(childParent.get(curNode), neighbourNode);
						int newTotalCost = curNode.getDistanceToStart() + weight + neighbourNode.getHeuristic();

						// Only traverse currNeighbour if new totalCost is lower
						if (newTotalCost < neighbourNode.getTotalCost()) {
							childParent.put(neighbourNode, curNode);

							neighbourNode.setDistanceToStart(curNode.getDistanceToStart() + weight);
							neighbourNode.setTotalCost(newTotalCost);

							orderedFrontier.add(neighbourNode);
						}
					}
				}
			}
		}
		System.err.println("FastestPath: A*Star is unable to find a path to goal node.");
		this.finalPath = new ArrayList<Node>();
		return this.finalPath;	// No paths found, return empty list
	}

	private ArrayList<Node> getNeighbours(Node curNode) {
		ArrayList<Node> toReturn = new ArrayList<Node>();

		int y = curNode.getCell().getY();
		int x = curNode.getCell().getX();

		Coordinate[] coordsToGet = new Coordinate[4];
		coordsToGet[0] = new Coordinate(y + 1, x);	// Top
		coordsToGet[1] = new Coordinate(y - 1, x);	// Bottom
		coordsToGet[2] = new Coordinate(y, x + 1);	// Right
		coordsToGet[3] = new Coordinate(y, x - 1);	// Left

		for (int i = 0; i < coordsToGet.length; i++) {
			Node nodeToGet = nodes.get(coordsToGet[i]);

			if (nodeToGet != null) {
				toReturn.add(nodeToGet);
			}
		}

		return toReturn;
	}

	//Reconstruct final path based on childParent relation
	private ArrayList<Node> genFinalPath(Node n, HashMap<Node, Node> childParent) {
		ArrayList<Node> toReturn = new ArrayList<Node>();

		while (n != null) {
			toReturn.add(n);
			n = childParent.get(n);
		}
		Collections.reverse(toReturn);

		if (toReturn.isEmpty())
			System.err.println("FastestPath: Unable to back track to start node.");

		return toReturn;
	}

	//Determine weight based on actual robot movement. That is, penalise whenever rotation is required.
	private int determineWeight(Node curNodeParent, Node neighbourNode) {
		// parent2 does not exist, default weight
		if (curNodeParent == null)
			return 1;

		Cell curCellParent = curNodeParent.getCell();
		Cell neighbourCell = neighbourNode.getCell();

		// currCellParent and neighbourCell are along same Y axis, no penalty
		if (curCellParent.getY() == neighbourCell.getY())
			return 1;

		// currCellParent and neighbourCell are along same Y axis, no penalty
		if (curCellParent.getX() == neighbourCell.getX())
			return 1;

		// currCellParent and neighbourCell are on different axis, induce penalty
		return 3;
	}
}
