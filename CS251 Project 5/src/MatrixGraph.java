/*	MatrixGraph.java
*
*	Implements an adjacency matrix version of a graph
*	Implements the abstract class Graph
*	
*/

import java.util.Scanner;

public class MatrixGraph extends Graph {
	private int[][] adjMatrix;

	public MatrixGraph(Scanner input) {
		super(input);
		adjMatrix = new int[numVertices][numVertices];

		for(int i = 0; i < numVertices; i++) {
			String line = input.nextLine();
			if(line.equals("")) {
				i--;
				continue;
			}
			String[] edges = line.split(" ");
			for(int j = 1; j < edges.length; j++) {
				this.addEdge(i,Integer.parseInt(edges[j]));
			}
		}
	}

	public void addEdge(int u, int v)
	{
		adjMatrix[u][v] = 1;
	}

	public boolean hasEdge(int u, int v)
	{
		return (adjMatrix[u][v] > 0);
	}

	public void removeEdge(int u, int v)
	{
		adjMatrix[u][v] = 0;
	}

	LinkedList<Integer> getAdjacentVertices(int v) {
		LinkedList<Integer> adjEdges = new LinkedList<Integer>();
		for(int i = 0; i < numVertices; i++)
			if(adjMatrix[v][i] > 0)
				adjEdges.add(i);
		return adjEdges;
	}

}
