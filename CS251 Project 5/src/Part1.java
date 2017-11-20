import java.util.*;
import java.lang.*;

/*		Part 1
 * 
 * 		TODO: Implement the following method stubs
 * 		
 * 		medianDegree
 * 		hasGiantConnectedComponent
 * 		is5Clique
 * 		has6DegreeRootN
 * 		has6Degree6
 *
 *		TODO: NAME: Seungho Yang
 *      TODO: login id: yang968
 *      TODO: project completion date: 12/2/2016
 *      TODO: PSO: 10
 */
public class Part1 {

	// Created a boolean array for DFS search for hasGiantConnectedComponent()
	private boolean[] marked;

	// TODO:
	// Find the median degree of all vertices in G
	public double medianDegree(Graph G) {
		// Using counting sort. edgeSizes array is size of maxEdge + 1
		/*
		int[] edgeSizes = new int[G.getMaxEdge() + 1];
		for (int j = 0; j < G.getNumVertices(); j++) {
			edgeSizes[G.getAdjacentVertices(j).size()]++;
		}

		for (int r = 0; r < G.getMaxEdge(); r++) {
			edgeSizes[r + 1] += edgeSizes[r];
		}

		int[] aux = new int[G.getNumVertices()];
		for (int k = 0; k < G.getNumVertices(); k++) {
			aux[edgeSizes[G.getAdjacentVertices(k).size()]-1] = G.getAdjacentVertices(k).size();
			edgeSizes[G.getAdjacentVertices(k).size()]--;
		}

		// if there are even number of vertices
		if (G.getNumVertices() % 2 == 0) {
			return (aux[G.getNumVertices() / 2] + aux[G.getNumVertices() / 2 - 1]) / 2.0;
		}
		// if there are odd number of vertices
		return aux[G.getNumVertices() / 2];*/
		return 0;
	}

	// TODO:
	// Determines if G has a connected component of size greater than or equal to ceil(n/2)
	// If it does, return a linked list containing the vertices in the giant connected component
	// Returns an empty list if there is no giant connected component
	public LinkedList<Integer> hasGiantConnectedComponent(Graph G) {
		// boolean array that checks if a vertex was visited
		marked = new boolean[G.getNumVertices()];
		int ceiling = (int) Math.ceil(G.getNumVertices() / 2.0);
		LinkedList<Integer> vertices = null;
		for (int v = 0; v < G.getNumVertices(); v++) {
			// run Helper method if vertex has not been marked
			if (!marked[v]) {
				vertices = dfsForCC(G, v);
			}
			// If group of vertices match the criteria return the list
			if (vertices.size() >= ceiling) {
				return vertices;
			}
		}
		return new LinkedList<Integer>();
	}

	/**
	 * Helper method for hasGiantConnectedComponent()
	 * @param G - Original Graph
	 * @param vertex - vertex that is checked
     * @return List of vertices
     */
	private LinkedList<Integer> dfsForCC(Graph G, int vertex) {
		LinkedList<Integer> vertices = new LinkedList<Integer>();
		vertices.add(vertex);
		//LinkedList<Integer> results = new LinkedList<Integer>();
		LinkedList<Integer> results;
		marked[vertex] = true;
		for (int n: G.getAdjacentVertices(vertex)) {
			if (!marked[n]) {
				results = dfsForCC(G, n);
				vertices.addAll(results);
			}
		}
		return vertices;
	}

	// TODO:
	// Returns true if the given 5 vertices in the list form a clique
	// Returns false otherwise
	public boolean is5Clique(Graph G, LinkedList<Integer> vertices) {
		if (vertices.size() != 5) return false;
		// current node is the first vertex from the vertices list
		LinkedList.Node current = vertices.first;
		while (current != vertices.last) {
			// next node is the second vertex from vertices
			LinkedList.Node nextItem = current.next;
			// If current node has less than 4 edges there's no point of checking and just need to return false
			if (G.getAdjacentVertices((int) current.item).size() < 4) {
				return false;
			}
			while (nextItem != null) {
				// If the vertices aren't connected return false
				if (!G.hasEdge((int) current.item, (int) nextItem.item)) {
					return false;
				}
				nextItem = nextItem.next;
			}
			current = current.next;
		}
		return true;
	}

	// TODO:
	// Determines if the given graph has at least 6 vertices of degree 
	// greater than or equal to floor(sqrt(n))
	// If so, return a linked list containing 6 vertices of degree greater than or equal to
	// floor(sqrt(n))
	// Return an empty list if there are not at least 6 vertices with the required degree
	public LinkedList<Integer> has6DegreeRootN(Graph G) {
		// If there are less than 6 vertices in the Graph just return an empty list
		if (G.getNumVertices() < 6) return new LinkedList<Integer>();
		int rootN = (int) Math.floor(Math.sqrt(G.getNumVertices()));
		LinkedList<Integer> results = find6FromList(G, rootN, true);
		return results;
	}

	// TODO:
	// Determines if the graph has at least 6 vertices of degree less than 6
	// If so, return a linked list containing 6 vertices that satisfy the condition
	// If not, return an empty linked list.
	public LinkedList<Integer> has6Degree6(Graph G) {
		// If there are less than 6 vertices in the Graph just return an empty list
		if (G.getNumVertices() < 6) return new LinkedList<Integer>();
		LinkedList<Integer> results = find6FromList(G, 6, false);
		return results;
	}

	/**
	 * Helper method for has6DegreeRootN and has6Degree6
	 * @param G - original graph
	 * @param input - either floor(sqrt(n)) or 6
	 * @param sqrtOR6 - true if RootN, false if 6
     * @return A list of 6 vertices if there are 6 or more, else an empty list
     */
	private LinkedList<Integer> find6FromList(Graph G, int input, boolean sqrtOR6) {
		LinkedList<Integer> results = new LinkedList<Integer>();
		int counter = 0;
		for (int i = 0; i < G.getNumVertices(); i++) {
			// if has6DegreeRootN called the method
			if (sqrtOR6) {
				if (counter < 6 && G.getAdjacentVertices(i).size() >= input) {
					results.add(i);
					counter++;
				}
			}
			// if has6Degree6 called the method
			else {
				if (counter < 6 && G.getAdjacentVertices(i).size() < input) {
					results.add(i);
					counter++;
				}
			}
			// return 6 vertices
			if (counter == 6) return results;
		}
		return new LinkedList<Integer>();
	}
}
