package algorithms;
import java.util.ArrayList;
import java.util.Collections;
import entities.Cell;

public class PathFinder {
    public PathFinder() {}

    public ArrayList<Integer> findShortestPath(ArrayList<Cell> pictureCellList){
        //intializing set ups
        Cell origin = new Cell(1, 1);
        int numNodes = pictureCellList.size();
        ArrayList<Cell> totalCellList = new ArrayList<Cell>(1 + numNodes);
        Cell[] pictureCellArray = new Cell[pictureCellList.size()];
        pictureCellArray = pictureCellList.toArray(pictureCellArray);
        totalCellList.add(origin);
        Collections.addAll(totalCellList, pictureCellArray);

        double[][] adjacencyMatrix = getAdjacencyMatrix(totalCellList);
        ArrayList<Integer> nodeList = getShortestPathGreedy(adjacencyMatrix);
        System.out.println(nodeList);
        return nodeList;
    }

    private ArrayList<Integer> getShortestPathGreedy(double[][] adjacencyMatrix) {
        // final path to take
        ArrayList<Integer> path = new ArrayList<>(adjacencyMatrix.length);
        // list of nodes to traverse
        ArrayList<Integer> nodes = new ArrayList<>(adjacencyMatrix.length);
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            nodes.add(i);
        }
        path.add(0); // add origin
        nodes.remove(0); // remove origin
        int currentNode = 0; // to track current processing node
        double min = 99999; // to find min distance from current node
        int minIdx = 0; // to track index of node with min distance from current node
        // traverse all nodes
        while (!nodes.isEmpty()) {
            min = 99999;
            minIdx = 0;
            // for current node, find the closest node
            for (int ii = 0; ii < adjacencyMatrix.length; ii++) {
                if (currentNode != ii && !path.contains(ii)) {
                    System.out.println("Calculating for " + currentNode + " " + ii);
                    if (adjacencyMatrix[currentNode][ii] < min) {
                        System.out.println(currentNode + " " + ii);
                        minIdx = ii;
                        min = adjacencyMatrix[currentNode][ii];
                    }
                }
            }
            // add index of closest node to final path
            path.add(minIdx);
            // prepare to traverse the next node in path
            currentNode = minIdx;
            // remove next node from list of nodes to traverse
            nodes.remove((Integer) minIdx);
        }
        return path;
    }

    private double[][] getAdjacencyMatrix(ArrayList<Cell> cellList) {
        int numOfVertices = cellList.size();
        double[][] adjacencyMatrix = new double[numOfVertices][numOfVertices];
        for (int i = 0; i < numOfVertices; i++) {
            for (int j = 0; j < numOfVertices; j++) {
                adjacencyMatrix[i][j] = getEuclideanDistance(cellList.get(i), cellList.get(j));
            }
        }
        return adjacencyMatrix;
    }

    private double getEuclideanDistance(Cell a, Cell b) {
        return Math.sqrt(Math.pow(Math.abs(a.getX() - b.getX()), 2) + Math.pow(Math.abs(a.getY() - b.getY()), 2));
    }
}
