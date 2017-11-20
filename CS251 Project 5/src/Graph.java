/*		Graph.java
*		This abstract class represents the basic functions required to work with a graph
*		Specific implementations of this abstract class are ListGraph and MatrixGraph
*
*		You may NOT edit this file - it will be replaced during grading!
*/

import java.util.Scanner;


public abstract class Graph {
	/* Constructor
	*
	*	Constructs a graph by reading in edges from standard input
	*/

	protected int numVertices;

	public Graph(Scanner input)
	{
		numVertices = input.nextInt();
	}


	abstract boolean hasEdge(int u, int v);

	abstract void addEdge(int u, int v);

	abstract void removeEdge(int u, int v);
	
	abstract LinkedList<Integer> getAdjacentVertices(int v);

	public int getNumVertices()
	{
		return numVertices;
	}
}
