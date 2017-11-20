/*		Project 5 driver
 * 
 * 		Contains IO handling code
 * 		You should NOT edit this file. It will be replaced during grading.
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class Project5 {

	
	public static void main(String[] args) {
		
		// Declarations
		Scanner input = new Scanner(System.in);
		Graph G;	
		String graphType = input.nextLine();
		
		// Create appropriate graph type
		if(graphType.equals("list"))
			G = new ListGraph(input);
		else if(graphType.equals("matrix"))
			G = new MatrixGraph(input);
		else
		{
			input.close();
			throw new IllegalArgumentException("ERROR: Invalid graph implementation type \"" + graphType + "\"");
		}
		
		// Read commands and execute them
		String command;
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		Part1 driver1 = new Part1();
		Part2 driver2 = new Part2();
		int u,v;
		
		// Loop until EOF
		while(input.hasNext())
		{
			command = input.next(p);
			switch(command)
			{
				case "medianDegree":
					System.out.println(driver1.medianDegree(G));
					break;
					
				case "giantComponent":
					System.out.println(driver1.hasGiantConnectedComponent(G));
					break;
					
				case "is5Clique":
					LinkedList<Integer> vertices = new LinkedList<Integer>();
					for(int i = 0; i < 5; i++)
						vertices.add(input.nextInt());
					System.out.println(driver1.is5Clique(G,vertices));
					break;
					
				case "has6DegreeRootN":
					System.out.println(driver1.has6DegreeRootN(G));
					break;	
					
				case "has6Degree6":
					System.out.println(driver1.has6Degree6(G));
					break;
					
				case "maxInDegree":
					System.out.println(driver2.maxInDegree(G));
					break;
					
				case "maxOutDegree":
					System.out.println(driver2.maxOutDegree(G));
					break;
					
				case "hasOneCycle":
					System.out.println(driver2.hasOneCycle(G));
					break;
					
				case "numEdgeTriangles":
					u = input.nextInt();
					v = input.nextInt();
					System.out.println(driver2.numEdgeTriangles(G, u, v));
					break;
					
				case "numTriangles":
					System.out.println(driver2.numTriangles(G));
					break;
					
				case "vertexClusterCoeff":
					v = input.nextInt();
					System.out.println(driver2.vertexClusterCoeff(G, v));
					break;
					
				case "globalClusterCoeff":
					System.out.println(driver2.globalClusterCoeff(G));
					break;
					
				default:
					System.out.println("Invalid command.");
					break;
			}
		}
	}
}
