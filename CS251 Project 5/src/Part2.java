import java.util.Arrays;

/*		Part 2
 * 
 * 		TODO: Implement the following methods
 *              
 *      maxInDegree
 *      maxOutDegree
 *      hasOneCycle 
 * 		numEdgeTriangles
 * 		numTriangles
 * 		vertexClusterCoeff
 * 		globalClusterCoeff
 *
 * 		TODO: NAME: Seungho Yang
 *      TODO: login id: yang968
 *      TODO: project completion date: 12/2/2016
 *      TODO: PSO: 10
 */
public class Part2 {

	// TODO:
	// Returns the maximum in-degree in the graph G
	/**
	 * Created an int array that is the size of number of vertices
	 * From Adjacent vertices list of all the vertices, increment array index of where
	 * current out degree is pointing to. Update variable max simultaneously
	 * if current in degrees is bigger than max.
     */
	public int maxInDegree(Graph G) {
		int[] inDegrees = new int[G.getNumVertices()];
		int max = 0;
		for (int i = 0; i < G.getNumVertices(); i++) {
			LinkedList.Node current = G.getAdjacentVertices(i).first;
			while (current != null) {
				inDegrees[(int) current.item]++;
				if (max < inDegrees[(int) current.item]) {
					max = inDegrees[(int) current.item];
				}
				current = current.next;
			}
		}
		return max;
	}

	// TODO:
	// Returns the maximum out degree in the graph G
	/**
	 * finds maximum out degree by traversing the vertices and comparing current size of
	 * Adjacent Vertices list with variable max
     */
	public int maxOutDegree(Graph G) {
		int max = 0;
		for (int i = 0; i < G.getNumVertices(); i++) {
			int outDegrees = G.getAdjacentVertices(i).size();
			if (outDegrees == G.getNumVertices() - 1) {
				return outDegrees;
			}
			if (outDegrees > max) {
				max = outDegrees;
			}
		}
		return max;
	}
	
	// TODO:
	// Determines if a graph has only one cycle
	// Returns a linked list containing the vertices in the cycle if there was only one cycle
	// Returns an empty linked list if there are none or more than one cycle
	/**
	 * Simple DFS algorithm may cause some issues while finding cycles in a direct graph
	 * Here's an example case
	 * 	0<---5<---4  DFS algorithm marks the vertices that it traverses so the algorithm doesn't need to
	 *	|         ^  revisit them but this can block the next DFS search from reaching the starting vertex.
	 *	V		  |  In this example, DFS algorithm will first identify cycle [0, 1, 2, 3, 4, 5] and those
	 *	1--->2--->3  vertices will be marked. The algorithm will back track to vertex 1 and now go to vertex 6.
	 *	|    ^	  ^  Next vertex is 7 and it will revisit vertex 2. Since it was visited earlier, the algorithm will
	 *	V    |	  |  not revisit this vertex and cycle [0, 1, 6, 7, 2, 3, 4, 5] will not be recognized.
	 *	6--->7	  |  DFS will back track to vertex 6 and now visit vertex 8. Vertex 9 will lead DFS to vertex 3
	 *	|		  |  which has been visited earlier again. DFS stops and tracks back when there are no more
	 *	V		  |  vertices to visit. Even though there are 3 cycles in this graph, simple DFS algorithm will not
	 *	8-------->9  recognize the other two. To fix this, there could be a list of original first cycle. When DFS
	 * visits a marked vector, check if its in the list. If the marked vertex is in the list, then there is another
	 * cycle that overlaps with first identified cycle. If the marked vertex is not in the list, then there's no cycle
	 * at that point.
     */
	public LinkedList<Integer> hasOneCycle(Graph G) {
		return new LinkedList<Integer>();
	}

	// TODO:
	// Returns the total number of triangles in the entire graph G that contain the edge (u,v)
	public int numEdgeTriangles(Graph G, int u, int v) {
		if (!G.hasEdge(u, v)) return 0;
		int counter = 0;
		// List of adjacent vertices from both vertices
		LinkedList<Integer> uAdjVertices = G.getAdjacentVertices(u);
		LinkedList<Integer> vAdjVertices = G.getAdjacentVertices(v);
		// Pointers to both list of adjacent vertices
		LinkedList.Node uNode = uAdjVertices.first;
		LinkedList.Node vNode = vAdjVertices.first;
		// O(nlogn) for Adjacency List implementation
		if (G instanceof ListGraph) {
			// Integer arrays for both adjacent vertices
			int[] uList = new int[uAdjVertices.size()];
			int[] vList = new int[vAdjVertices.size()];
			int a = 0;
			// insert adjacent vertices to the arrays
			while (uNode != null) {
				uList[a] = (int) uNode.item;
				a++;
				uNode = uNode.next;
			}
			a = 0;
			while (vNode != null) {
				vList[a] = (int) vNode.item;
				a++;
				vNode = vNode.next;
			}
			// Sort the arrays
			Arrays.sort(uList);
			Arrays.sort(vList);
			// integer variable pointers that point to index of both arrays
			int i = 0;
			int j = 0;
			while (i < uList.length && j < vList.length) {
				// If both pointers point to same value, increment counter and move pointers to next node
				if (uList[i] == vList[j]) {
					counter++;
					i++;
					j++;
				}
				// If value of first pointer > value of second pointer, move second pointer to next node
				else if (uList[i] > vList[j]){
					j++;
				}
				// If value of first pointer < value of second pointer, move first pointer to next node
				else {
					i++;
				}
			}
			return counter;
		}
		// O(n) for Adjacency Matrix implementation
		while (uNode != null && vNode != null) {
			// If both pointers point to same value, increment counter and move pointers to next node
			if ((int) uNode.item == (int) vNode.item) {
				counter++;
				uNode = uNode.next;
				vNode = vNode.next;
			}
			// If value of first pointer > value of second pointer, move second pointer to next node
			else if ((int) uNode.item > (int) vNode.item){
				vNode = vNode.next;
			}
			// If value of first pointer < value of second pointer, move first pointer to next node
			else {
				uNode = uNode.next;
			}
		}
		return counter;
	}

	// TODO:
	// Returns the total number of triangles in the graph
	public int numTriangles(Graph G) {
		int triangles = 0;
		int totalVertices = G.getNumVertices();
		// O(maxDegree^2 * n)
		for (int i = 0; i < totalVertices; i++) {
			// List of adjacent vertices of a vertex
			LinkedList<Integer> iAdjVertices = G.getAdjacentVertices(i);
			// First adjacent vertex
			LinkedList.Node iNode = iAdjVertices.first;
			while (iNode != null) {
				// List of adjacent vertices from adjacent vertex of original vertex
				LinkedList<Integer> jAdjVertices = G.getAdjacentVertices((int) iNode.item);
				LinkedList.Node jNode = jAdjVertices.first;
				while (jNode != null) {
					// if list of List of adjacent vertices of original vertex contains same adjacent vertex,
					// increment triangle counter
					if (iAdjVertices.contains((int) jNode.item)){
						triangles++;
					}
					jNode = jNode.next;
				}
				iNode = iNode.next;
			}
		}
		return triangles / 6;
	}

	// TODO:
	// Returns the vertex clustering coefficient of v
	// The vertex clustering coefficient is the fraction of pairs of vertices
	// that are adjacent to v that are also adjacent to each other.
	/**
	 * Find clustering coefficient of a vertex using numEdgeTriangles method.
	 * Formula to find clustering coefficient is (2 * Triangles) / (degrees * (degrees - 1))
	 * If two adjacent vertices are connected, then they form a triangle with original vertex.
     */
	public double vertexClusterCoeff(Graph G, int v) {
		LinkedList<Integer> adjVertices = G.getAdjacentVertices(v);
		int vertices = adjVertices.size();
		// If number of adjacent vertices is less than 2, clustering coefficient is 0
		if (vertices < 2) {
			return 0.0;
		}
		// O(n^2) or O(n^2logn) if ListGraph
		double triangles = 0.0;
		// first adjacent vertex
		LinkedList.Node current = adjVertices.first;
		while (current != null) {
			triangles += numEdgeTriangles(G, (int) current.item, v);
			current = current.next;
		}
		// Divide by two to get original number of triangles
		triangles /= 2;
		return (2 * triangles) / (vertices * (vertices - 1));
	}

	/**
	 * Sums up clustering coefficients of all vertices and returns the average
     */
	public double globalClusterCoeff(Graph G) {
		double sum = 0.0;
		for (int i = 0; i < G.getNumVertices(); i++) {
			sum += vertexClusterCoeff(G, i);
		}
		return sum / G.getNumVertices();
	}

}
