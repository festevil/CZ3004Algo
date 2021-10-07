package algorithms;

import java.util.ArrayList;

import entities.Cell;
import entities.CellCoorPair;
import entities.Map;
import entities.Coordinate;
import entities.DirectedCoor;

public class HamiltonianPathFinder {
    public HamiltonianPathFinder() {}

    /**
     * Return the order of coordinates to travel.
     * Note that this calls other methods to do this (not by itself)
     * @param pictureCellList
     * @param curPos
     * @param curDir
     * @return Order of coordinates to travel.
     */
    public ArrayList<CellCoorPair> findShortestPath(ArrayList<Cell> pictureCellList, Coordinate curPos, int curDir) {

        // Call processCellList to get the coordinates from pictureCellList
        ArrayList<DirectedCoor> fullCoorList = processCellList(pictureCellList, curPos, curDir);

        ArrayList<CellCoorPair> finalMap = new ArrayList<CellCoorPair>(fullCoorList.size());

        // Calculation phase
        double[][] adjacencyMatrix = getAdjacencyMatrix(fullCoorList);
        ArrayList<Integer> indexList = getShortestPathNearestNeighbor(adjacencyMatrix);

        // Recombine the result
        for (int i = 0; i < indexList.size(); i++) {
            if (i == 0)
                finalMap.add(new CellCoorPair(new Cell(1, 1), fullCoorList.get(indexList.get(i))));
            else
                finalMap.add(new CellCoorPair(pictureCellList.get(indexList.get(i) - 1), fullCoorList.get(indexList.get(i))));
        }
        return finalMap;
    }

    /**
     * This method turns the list of picture cells into a list of coordinates w/ directions used for calculation
     * @param pictureCellList
     * @param curPos
     * @param curDir
     * @return List of DirectedCoor
     */
    private ArrayList<DirectedCoor> processCellList(ArrayList<Cell> pictureCellList, Coordinate curPos, int curDir) {
        int numNodes = pictureCellList.size();
        ArrayList<DirectedCoor> coorList = new ArrayList<DirectedCoor>(1 + numNodes);

        // Adding the starting position
        coorList.add(new DirectedCoor(curPos, curDir));

        // Adding new coordinates from cell list. Note that this depicts the position and rotation of the robot.
        for (int i = 0; i < pictureCellList.size(); i++) {
            Cell curCell = pictureCellList.get(i);
            switch(curCell.getCellType()) {
                case 'A':
                    int plusY = 3;
                    while (curCell.getY() + plusY > Map.maxY - 2 && plusY >= 3)
                        plusY--;
                    coorList.add(new DirectedCoor(curCell.getY() + plusY, curCell.getX(), DirectedCoor.SOUTH));
                    break;
                case 'B':
                    int plusX = 3;
                    while (curCell.getX() + plusX > Map.maxX - 2 && plusX >= 3)
                        plusX--;
                    coorList.add(new DirectedCoor(curCell.getY(), curCell.getX() + plusX, DirectedCoor.WEST));
                    break;
                case 'C':
                    int minusY = 3;
                    while (curCell.getY() - minusY < 1 && minusY >= 3)
                        minusY++;
                        coorList.add(new DirectedCoor(curCell.getY() - minusY, curCell.getX(), DirectedCoor.NORTH));
                    break;
                case 'D':
                    int minusX = 3;
                    while (curCell.getX() - minusX < 1 && minusX >= 3)
                        minusX++;
                    coorList.add(new DirectedCoor(curCell.getY(), curCell.getX() - minusX, DirectedCoor.EAST));
                    break;
            }
        }

        return coorList;
    }

    /**
     * This method will give the order of travelling given the adjacency matrix based on nearest neighbor algorithm.
     * @param adjacencyMatrix
     * @return Index list - order of coordinates to travel
     */
    private ArrayList<Integer> getShortestPathNearestNeighbor(double[][] adjacencyMatrix) {
        // Initialization
        ArrayList<Integer> finalPath = new ArrayList<>(adjacencyMatrix.length);
        ArrayList<Integer> nodeList = new ArrayList<>(adjacencyMatrix.length);
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodeList.add(i);
        }
        finalPath.add(0);       // add origin to final path
        nodeList.remove(0);     // remove origin from list of nodes
        int curNode = 0;        // to track current processing node
        double minDist;         // to find min distance from current node
        int minIdx;             // to track index of node with min distance from current node

        // Traverse all nodes
        while (!nodeList.isEmpty()) {
            minDist = 99999;
            minIdx = 0;
            // For current node, find the closest node
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (curNode != j && !finalPath.contains(j) && adjacencyMatrix[curNode][j] < minDist) {
                    minIdx = j;
                    minDist = adjacencyMatrix[curNode][j];
                }
            }
            
            finalPath.add(minIdx);                  // add index of closest node to final path
            curNode = minIdx;                       // prepare to traverse the next node in path
            nodeList.remove((Integer) minIdx);      // remove next node from list of nodes to traverse
        }

        return finalPath;
    }

    /**
     * This method calculates the adjacency matrix for a list of coordinates
     * @param coorList
     * @return The adjacency matrix
     */
    private double[][] getAdjacencyMatrix(ArrayList<DirectedCoor> coorList) {

        // Initialization
        int numOfVertices = coorList.size();
        double[][] adjacencyMatrix = new double[numOfVertices][numOfVertices];

        // Calculation
        for (int i = 0; i < numOfVertices; i++) {
            for (int j = 0; j < numOfVertices; j++) {
                // Needs to calculate this more precisely with direction!
                adjacencyMatrix[i][j] = getManhattanDistance(coorList.get(i), coorList.get(j));
            }
        }
        return adjacencyMatrix;
    }

    // private double getEuclideanDistance(Coordinate a, Coordinate b) {
    //     //This method calculates the distance between two coordinates
    //     return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    // }
    
    /**
     * Return the Manhattan Distance from a to b.
     * @param a
     * @param b
     * @return Manhattan Distance
     */
    private double getManhattanDistance(DirectedCoor a, DirectedCoor b) {
        //This method calculates the distance between two coordinates
        return (double) Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
