package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

import entities.Cell;
import entities.Coordinate;
import entities.DirectedCoor;
import entities.Map;
import entities.Node;

public class FastestPathV2 {

	private HashMap<DirectedCoor, Node> nodeMap = new HashMap<DirectedCoor, Node>();
	private HashMap<Node, ArrayList<Node>> edges = new HashMap<Node, ArrayList<Node>>();

	private DirectedCoor startCoord, goalCoord;

	private int padding;

	private ArrayList<Node> finalPath = new ArrayList<Node>();

	/**
	 * FastestPath constructor.
	 * @param curMap
	 * @param startCoord
	 * @param goalCoord
	 */
	public FastestPathV2(Map curMap, DirectedCoor startCoord, DirectedCoor goalCoord, int padding) {
		this.startCoord = startCoord;
		this.goalCoord = goalCoord;
		this.padding = padding;
		initialization(curMap);
	}

	/**
	 * Initialization method. This method does two things:
	 * 1) Map a DirectedCoor to a Node with heuristic
	 * 2) Map a Node to a list of available next nodes.
	 * @param curMap
	 */
	private void initialization(Map curMap) {
		// Step 1: Create graph of nodes with heuristic
		for (int y = 1; y <= Map.maxY - 2 + 2 * padding; y++) {
			for (int x = 1; x <= Map.maxX - 2 + 2 * padding; x++) {
				Coordinate curPos = new Coordinate(y, x);

				if (isValidPlacement(curPos, curMap)) {
					int heuristic;
					//int y2 = goalCoord.getY();
					//int x2 = goalCoord.getX();

					// Heuristic here can be either Manhattan distance, or 0. If 0, this is Dijkstra's algorithm instead.
					// heuristic = (int) Math.round(Math.abs(y2 - y) + Math.abs(x2 - x));
					heuristic = 0;

					for (int i = 0; i < 4; i++) {
						DirectedCoor newPos = new DirectedCoor(new Coordinate(y, x), i);
						Node newNode = new Node(heuristic, curMap.getCell(curPos), i);
						nodeMap.put(newPos, newNode);

						// if(newPos.getY() == 1 && newPos.getX() == 1) {
						// 	System.out.println("newPos: " + newPos.toString() + ", newNode: " + newNode.toString());
						// 	System.out.println(newPos.equals(startCoord));
						// }
					}
				}
			}
		}

		// Step 2: Link nodes with edges (so we know which nodes are available after moving into a node)
		for (HashMap.Entry<DirectedCoor, Node> entry : nodeMap.entrySet()) {
			ArrayList<Node> neighbors = getAvailableMoves(entry.getValue());
			edges.put(entry.getValue(), neighbors);
		}
	}

	/**
	 * The A-star search for a path from startCoord to goalCoord.
	 * @param curMap
	 * @return The path from startCoord to goalCoord (with direction kept in mind)
	 */
	public ArrayList<Node> runAStar(Map curMap) {
		if (!this.finalPath.isEmpty()) {
			return this.finalPath;
		}
		
		PriorityQueue<Node> orderedFrontier = new PriorityQueue<Node>();	// Frontier that is sorted
		HashMap<Node, Node> childParent = new HashMap<Node, Node>();		// Child, Parent (Current, Previous)

		Node startNode = this.nodeMap.get(startCoord);	// Get Start Node
		//System.out.println("startNode: " + startNode.toString());
		Node goalNode = this.nodeMap.get(goalCoord);	// Get Goal Node
		//System.out.println("endNode: " + goalNode.toString());
		startNode.setDistanceToStart(0);	// Set Start Node total cost to 0
		orderedFrontier.add(startNode);		// Add it to the frontier

		Node curNode;
		//Travelling through the Priority Queue
		while (!orderedFrontier.isEmpty()) {
			curNode = orderedFrontier.poll();
			//System.out.println("Exploring curNode: " + curNode.toString());

			if (!curNode.isVisited()) {
				curNode.setVisited(true);

				// Check if it is Goal Node. If it is, call genFinalPath to trace a path back to startCoord.
				if (curNode.equals(goalNode)) {
					this.finalPath = genFinalPath(goalNode, childParent);
					return this.finalPath;
				}

				// Check available next moves
				ArrayList<Node> nextMoves = edges.get(curNode);
				if (nextMoves != null) {
					for (int i = 0; i < nextMoves.size(); i++) {
						Node neighborNode = nextMoves.get(i);
						// Only proceed if this is a valid move from curNode to neighborNode
						if(isValidMovement(curNode, neighborNode, curMap)) {
							// Only traverse to neighborNode if it is unvisited
							if (!neighborNode.isVisited()) {
								// System.out.println("Exploring neighborNode: " + neighborNode.toString());

								// Determine weight based on actual robot movement
								int weight = 1;
								if (childParent.get(curNode) != null)
									weight = determineWeight(childParent.get(curNode), neighborNode);
								int newTotalCost = curNode.getDistanceToStart() + weight + neighborNode.getHeuristic();

								// Only traverse to neighborNode if new totalCost is lower
								if (newTotalCost < neighborNode.getTotalCost()) {
									childParent.put(neighborNode, curNode);

									neighborNode.setDistanceToStart(curNode.getDistanceToStart() + weight);
									neighborNode.setTotalCost(newTotalCost);

									orderedFrontier.add(neighborNode);
								}
							}
						}
					}
				}
			}
		}
		// If the Priority Queue is exhausted and there's no path found, return empty list
		System.err.println("FastestPath: A*Star is unable to find a path to goal node.");
		this.finalPath = new ArrayList<Node>();
		return this.finalPath;
	}

	/**
	 * Reconstruct final path based on childParent relation.
	 * @param n
	 * @param childParent
	 * @return List of nodes to indicate the final path
	 */
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

	/**
	 * Determine weight based on actual robot movement. That is, penalise whenever rotation is required.
	 * @param curNode
	 * @param nextNode
	 * @return penalty of movement
	 */
	private int determineWeight(Node curNode, Node nextNode) {
		Cell curCell = curNode.getCell();
		Cell nextCell = nextNode.getCell();

		// curCell and nextCell are along same Y or X axis, no penalty
		if (curCell.getY() == nextCell.getY() || curCell.getX() == nextCell.getX())
			return 1;

		// curCell and nextCell are on different axis, induce penalty (turning)
		// How much should turning cost?
		return 5;
	}

	/**
	 * Return a list of available next nodes for a given node.
	 * For a node (Y, X, North) the available nodes are 
	 * (Y+1, X, North), (Y-1, X, North), (Y+2, X+2, East), (Y+2, X-2, West)
	 * This is to conform to actual robot movement.
	 * @param curNode
	 */
	private ArrayList<Node> getAvailableMoves(Node curNode) {
		ArrayList<Node> toReturn = new ArrayList<Node>();
		ArrayList<DirectedCoor> coorList = new ArrayList<DirectedCoor>();
		Node tempNode;

		int y = curNode.getCell().getY();
		int x = curNode.getCell().getX();
		int dir = curNode.getDir();

		switch(dir) {
			case Node.NORTH:
				coorList.add(new DirectedCoor(y - 1, x, Node.NORTH));
				coorList.add(new DirectedCoor(y + 1, x, Node.NORTH));
				coorList.add(new DirectedCoor(y + 2, x + 2, Node.EAST));
				coorList.add(new DirectedCoor(y + 2, x - 2, Node.WEST));
				break;
			case Node.SOUTH:
				coorList.add(new DirectedCoor(y - 1, x, Node.SOUTH));
				coorList.add(new DirectedCoor(y + 1, x, Node.SOUTH));
				coorList.add(new DirectedCoor(y - 2, x + 2, Node.EAST));
				coorList.add(new DirectedCoor(y - 2, x - 2, Node.WEST));
				break;
			case Node.EAST:
				coorList.add(new DirectedCoor(y, x - 1, Node.EAST));
				coorList.add(new DirectedCoor(y, x + 1, Node.EAST));
				coorList.add(new DirectedCoor(y + 2, x + 2, Node.NORTH));
				coorList.add(new DirectedCoor(y - 2, x + 2, Node.SOUTH));
				break;
			case Node.WEST:
				coorList.add(new DirectedCoor(y, x - 1, Node.WEST));
				coorList.add(new DirectedCoor(y, x + 1, Node.WEST));
				coorList.add(new DirectedCoor(y + 2, x - 2, Node.NORTH));
				coorList.add(new DirectedCoor(y - 2, x - 2, Node.SOUTH));
				break;
		}

		for(int i = 0; i < coorList.size(); i++) {
			tempNode = nodeMap.get(coorList.get(i));
			if (tempNode != null)
				toReturn.add(tempNode);
		}

		return toReturn;
	}

	/**
	 * Check if a movement from a node to another is valid or not.
	 * Note that this method is only used for the nodes' "neighbors".
	 * @param curNode
	 * @param nextNode
	 * @param curMap
	 * @return Whether this is a valid move.
	 */
	private boolean isValidMovement(Node curNode, Node nextNode, Map curMap) {
		int y2 = nextNode.getCell().getY();
		int x2 = nextNode.getCell().getX();

		int y = curNode.getCell().getY();
		int x = curNode.getCell().getX();

		// Is there anyway to improve this??

		if (y2 - y == 2 && x2 - x == 2) {
			switch(curNode.getDir()) {
				case Node.NORTH:	//nextNode is EAST
					return isValidPlacement(new Coordinate(y2, x2 + 1), curMap) && 
						isValidPlacement(new Coordinate(y2 + 1, x), curMap) &&
						isValidPlacement(new Coordinate(y + 1, x + 1), curMap);
				case Node.EAST:		//nextNode is NORTH
					return isValidPlacement(new Coordinate(y2 + 1, x2), curMap) && 
						isValidPlacement(new Coordinate(y, x2 + 1), curMap) &&
						isValidPlacement(new Coordinate(y + 1, x + 1), curMap);
			}
		}

		if (y2 - y == 2 && x2 - x == -2) {
			switch(curNode.getDir()) {
				case Node.NORTH:
					return isValidPlacement(new Coordinate(y2, x2 - 1), curMap) && 
						isValidPlacement(new Coordinate(y2 + 1, x), curMap) &&
						isValidPlacement(new Coordinate(y + 1, x - 1), curMap);
				case Node.WEST:
					return isValidPlacement(new Coordinate(y2 + 1, x2), curMap) && 
						isValidPlacement(new Coordinate(y, x2 - 1), curMap) &&
						isValidPlacement(new Coordinate(y + 1, x - 1), curMap);
			}
		}

		if (y2 - y == -2 && x2 - x == 2) {
			switch(curNode.getDir()) {
				case Node.SOUTH:
					return isValidPlacement(new Coordinate(y2, x2 + 1), curMap) && 
						isValidPlacement(new Coordinate(y2 - 1, x), curMap) &&
						isValidPlacement(new Coordinate(y - 1, x + 1), curMap);
				case Node.EAST:
					return isValidPlacement(new Coordinate(y2 - 1, x2), curMap) && 
						isValidPlacement(new Coordinate(y, x2 + 1), curMap) &&
						isValidPlacement(new Coordinate(y - 1, x + 1), curMap);
			}
		}

		if (y2 - y == -2 && x2 - x == -2) {
			switch(curNode.getDir()) {
				case Node.SOUTH:
					return isValidPlacement(new Coordinate(y2, x2 - 1), curMap) && 
						isValidPlacement(new Coordinate(y2 - 1, x), curMap) &&
						isValidPlacement(new Coordinate(y - 1, x - 1), curMap);
				case Node.WEST:
					return isValidPlacement(new Coordinate(y2 - 1, x2), curMap) && 
						isValidPlacement(new Coordinate(y, x2 - 1), curMap) &&
						isValidPlacement(new Coordinate(y - 1, x - 1), curMap);
			}
		}

		// No turning required, only moving forward or backward - in which case this is always valid.
		return true;
	}

	/**
	 * Check if the robot can be placed on this spot or not.
	 * Note that since out-of-bound moves are acceptable, this now only checks for collision.
	 * @param curPos
	 * @param curMap
	 * @return Whether this is a valid placement w/ no collision.
	 */
	private boolean isValidPlacement(Coordinate curPos, Map curMap) {
		ArrayList<Coordinate> surroundingCoor = new ArrayList<>();
		for (int dy = -1; dy <= 1; dy++) {
			for (int dx = -1; dx <= 1; dx++) {
				if (curPos.getY() + dy >= 0 && curPos.getY() + dy <= Map.maxY - 1 + 2 * padding
					&& curPos.getX() + dx >= 0 && curPos.getX() + dx <= Map.maxX - 1 + 2 * padding)
					surroundingCoor.add(new Coordinate(curPos.getY() + dy, curPos.getX() + dx));
			}
		}
		
		for (int i = 0; i < surroundingCoor.size(); i++) {
			Cell c = curMap.getCell(surroundingCoor.get(i));
			if (c.getCellType() == Cell.WALL || c.getCellType() == Cell.NORTHWALL ||
			c.getCellType() == Cell.EASTWALL || c.getCellType() == Cell.SOUTHWALL ||
			c.getCellType() == Cell.WESTWALL) {
				return false;
			}
		}

		return true;
	}
}
