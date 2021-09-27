package algorithms;

import java.util.ArrayList;

import entities.Cell;
import entities.DirectedCoordinate;
import entities.Coordinate;

public class PathFinder {
    public PathFinder() {}


    public ArrayList<DirectedCoordinate> findShortestPath(ArrayList<Cell> pictureCellList, Coordinate curPos, int curDir){
        //The main method.

        //initializing
        ArrayList<DirectedCoordinate> fullCoorList = processCellList(pictureCellList, curPos, curDir);
        ArrayList<DirectedCoordinate> finalCoorList = new ArrayList<DirectedCoordinate>(fullCoorList.size());

        //calling other functions to calculate
        double[][] adjacencyMatrix = getAdjacencyMatrix(fullCoorList);
        ArrayList<Integer> nodeList = getShortestPathGreedy(adjacencyMatrix);

        for (int i = 0; i < nodeList.size(); i++) {
            finalCoorList.add(fullCoorList.get(nodeList.get(i)));
        }
        return finalCoorList;
    }

    public ArrayList<DirectedCoordinate> processCellList(ArrayList<Cell> pictureCellList, Coordinate curPos, int curDir){
        //This method turns the list of picture cells into a list of coordinates w/ directions used for calculation

        //initialization
        int numNodes = pictureCellList.size();
        ArrayList<DirectedCoordinate> coorList = new ArrayList<DirectedCoordinate>(1 + numNodes);
        coorList.add(new DirectedCoordinate(curPos.getY(), curPos.getX(), curDir));

        //adding new coordinates from cell list. Note that this depicts the position and rotation of the robot
        for (int i = 0; i < pictureCellList.size(); i++) {
            Cell curCell = pictureCellList.get(i);
            switch(curCell.getCellType()) {
                case 'A':
                    coorList.add(new DirectedCoordinate(curCell.getY() + 3, curCell.getX(), DirectedCoordinate.SOUTH));
                    break;
                case 'B':
                    coorList.add(new DirectedCoordinate(curCell.getY(), curCell.getX() + 3, DirectedCoordinate.WEST));
                    break;
                case 'C':
                    coorList.add(new DirectedCoordinate(curCell.getY() - 3, curCell.getX(), DirectedCoordinate.NORTH));
                    break;
                case 'D':
                    coorList.add(new DirectedCoordinate(curCell.getY(), curCell.getX() - 3, DirectedCoordinate.EAST));
                    break;
            }
        }
        return coorList;
    }

    private ArrayList<Integer> getShortestPathGreedy(double[][] adjacencyMatrix) {
        // This method will give the order of travelling given the adjacency matrix based on a greedy algorithm

        //initialization
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

        // traverse all nodes
        while (!nodeList.isEmpty()) {
            minDist = 99999;
            minIdx = 0;
            // for current node, find the closest node
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

    private double[][] getAdjacencyMatrix(ArrayList<DirectedCoordinate> coorList) {
        //This method calculates the adjacency matrix for a list of vertices

        //initialization
        int numOfVertices = coorList.size();
        double[][] adjacencyMatrix = new double[numOfVertices][numOfVertices];

        //calculation
        for (int i = 0; i < numOfVertices; i++) {
            for (int j = 0; j < numOfVertices; j++) {
                adjacencyMatrix[i][j] = getEuclideanDistance(coorList.get(i), coorList.get(j));
            }
        }
        return adjacencyMatrix;
    }

    private double getEuclideanDistance(DirectedCoordinate a, DirectedCoordinate b) {
        //This method calculates the distance between two coordinates
        return Math.sqrt(Math.pow(Math.abs(a.getX() - b.getX()), 2) + Math.pow(Math.abs(a.getY() - b.getY()), 2));
    }
}
