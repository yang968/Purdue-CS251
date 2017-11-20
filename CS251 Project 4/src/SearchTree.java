/*Search Tree class
 * Your implementation goes in this file
 */
import java.util.*;

/**
 * TODO Name: Seungho Yang
 * TODO userid: yang968
 * TODO completion date: 11/18/2016
 * TODO PSO: 10
 */
public class SearchTree {
	
	private Node root; //The root of the RB Tree
	private JobTable jobs; //The jobTable
	//The following variables are used for measuring utilities, do not change them!
	private int[] machineLoads;
	private int numOfMachine;
	private int scheduled;
	private int requests;
	//You can add any other variables here if needed

	//Create a balanced search tree consisting of all the machines initially empty
	public SearchTree(int space, int numOfMachine) {
		jobs = new JobTable();
		scheduled = requests = 0;
		this.numOfMachine = numOfMachine;
		machineLoads = new int[numOfMachine];
		machineLoads[0] = 0;
		root = new Node(0, space, 0);
		for (int i = 1; i < numOfMachine; i++){
			insertNewNode(i, space, 0);
			machineLoads[i] = 0;
		}
	}

	public SearchTree(JobTable jt) {
		jobs = jt;
	}
	
	public void insertNewNode(int machine, int free, int numjobs){
		root = RedBlackBST.insert(root, new Node(machine,free, numjobs));
	}

	//TODO: All parts needed for you implementation are in functions listed below:
	
	//Find the machine with just enough free space to schedule a job
	//Update the free size and number of jobs on the machine
	//Return machine id... -1 if no such machine exists
	public int scheduleJobMinSpace(int jobid, int size) {
		Node m; 
		requests++;
		/* Do not modify the code above */
		/* TODO: Start your implementation here, find m to schedule */
		// Find node to allocate the job
		m = findNodeMinSpace(root, size);
		// If there's no node for the job, discard it and return -1
		if (m == null) {
			//System.out.println("Failed to schedule " + jobid);
			return -1;
		}
		// Delete Node after creating a deep copy
		Node n = new Node(m.id, m.free, m.numjobs);
		root = RedBlackBST.delete(root, m);
		// Add the job to the node
		n.addJob(size);
		// Add the job to the jobs hashmap
		jobs.addJob(jobid, size, n);
		// Insert the node back to the Tree
		root = RedBlackBST.insert(root, n);
		//System.out.println("Success! scheduled " + jobid + " to " + n.id);
		// original node refers to the deep copy
		m = n;
		/* Do not modify the following part */
		scheduled++;
		machineLoads[m.id]++;
		return m.id;
	}

	/**
	 * Helper method that returns the best node for the job
	 * @param x root node
	 * @param size size of the job
     * @return best node
     */
	private Node findNodeMinSpace(Node x, int size) {
		Node result = null;
		while (x != null) {
			if (x.free == size) {
				return x;
			}
			else if (size < x.free){
				if (result == null || x.free < result.free) {
					result = x;
				}
				x = x.left;
			}
			else {
				x = x.right;
			}
		}
		return result;
	}
	

	//Find the machine with enough free space and minimum number of jobs to schedule a job
	//Update the free size and number of jobs on the machine
	//Return machine id... -1 if no such machine exists
	public int scheduleJobMinJob(int jobid, int size) {
		Node m;
		requests++;
		/* TODO: Start your implementation here: Find node m to schedule the job  */
		// Check if there's a node for the job
		m = findNodeMinJob(root, size);
		// return -1 if there's no node for it
		if (m == null) {
			return -1;
		}
		//System.out.println("job: " + jobid + " is going to node " + m.id);
		// Delete Node after creating a deep copy
		Node n = new Node(m.id, m.free, m.numjobs);
		root = RedBlackBST.delete(root, m);
		// Add the job to the node
		n.addJob(size);
		// Add the job to the jobs hashmap
		jobs.addJob(jobid, size, n);
		// Insert the job to the tree
		root = RedBlackBST.insert(root, n);
		// original node refers to the deep copy
		m = n;
		/* Do not modify the following part */
		machineLoads[m.id]++;
		scheduled++;
		return m.id;
	}

	/**
	 * Helper method that returns the best node after using min job strategy
	 * @param u initially root node (will change thru recursion)
	 * @param size size of the job
     * @return best node
     */
	private Node findNodeMinJob(Node u, int size) {
		Node result = null;
		while (u != null) {
			if (u.free >= size) {
				Node w;
				if (u.right != null) {
					w = solveTies(u, u.right.minJobsNode);
				}
				else w = u;
				if (result == null) result = w;
				else result = solveTies(result, w);
				Node bestFromLeftOfu = findNodeMinJob(u.left, size);
				result = solveTies(result, bestFromLeftOfu);
				break;
			}
			else {
				u = u.right;
			}
		}
		return result;
	}

	/**
	 * Compares the two nodes and returns the best one
	 * If both have same numJobs and free space, return one with smaller id
	 * If both have same numjobs, return one with smaller free space
	 * Or return one with smaller numjobs
     * @return better node for the job
     */
	private Node solveTies(Node a, Node b) {
		Node result;
		if (a != null && b == null) return a;
		if (a == null && b != null) return b;
		if (a.numjobs == b.numjobs && a.free == b.free) {
			result = (a.id < b.id) ? a : b;
		}
		else if (a.numjobs == b.numjobs) {
			result = (a.free < b.free)? a : b;
		}
		else result = (a.numjobs < b.numjobs) ? a : b;
		return result;
	}

	//Update the free space and number of jobs on machine releasing a job
	public void releaseJob(int jobid) {
		Node m = jobs.jobMachine(jobid);
		/* TODO: Release m */

		if (m != null) {
			// Make deep copy of the node
			Node n = new Node(m.id, m.free, m.numjobs);
			n.size = m.size;
			n.minJobsNode = m.minJobsNode;
			// Delete node from the tree
			root = RedBlackBST.delete(root, m);
			// Remove the job from the node
			n.removeJob(jobs.jobSize(jobid));
			machineLoads[n.id]--;
			// insert node back to the tree
			root = RedBlackBST.insert(root, n);
			// TODO: Does the job need to be deleted from JobTable as well?
			jobs.deleteJob(jobid, n);
		}
	}
	
	//Return the number of machines that have atleast given free space
	public int count(int free){
		/* TODO: start your implementation here */
		if (free <= 0) return 0;
		return count(free, root);
	}

	/**
	 * helper method that uses recursion and finds number of nodes that have certain free space
	 * @param free free size
	 * @param x root
     * @return number of nodes in the tree
     */
	public int count(int free, Node x) {
		if (x == null) return 0;
		// If at leaf node
		if (x.left == null && x.right == null) {
			if (x.free >= free) return x.size;
			else return 0;
		}
		// If node satisfies free size
		if (x.free >= free) {
			if (x.right != null) return 1 + x.right.size + count(free, x.left);
			else return 1 + count(free, x.left);
		}
		// If node does not satisfy
		else{
			int i = 0;
			try{
				i = count(free, x.right);
			} catch (Exception e) {

			}
			return i;
		}
	}
	
	/*
	* DO NOT EDIT THE FOLLOWING FUNCTION
	* IT IS INVOLVED IN MEASURING THE UTILITIES FOR EXPERIMENTAL SECTION
	*/
	public void measureUtility(){
		double ideal = 0.0;
		double medianload = 0.0;
		ArrayList<Integer> loads = new ArrayList<Integer>();
		int size = 0;
		for (int i = 0; i < numOfMachine; i ++){
			int load = machineLoads[i];
			loads.add(load);
			size+=load;
		}

		int len = loads.size();

		Collections.sort(loads);
		if (len%2 != 0) {
			medianload = loads.get(len/2);
		}
		else {
			medianload = (loads.get(len/2) + loads.get(len/2-1))/2.0;
		}
		System.out.println(size);
		ideal = size/(double)numOfMachine;
		double fairness = medianload/ideal;
		double thoroughput = scheduled/(double)requests;
		System.out.format("Fairness: %f, Thoroughput: %f\n",fairness,thoroughput);
	}
	/*
	* DO NOT EDIT THE FUNCTION ABOVE
	*/

	/* My Debug function. Please Ignore
	public void inOrder(Node x, int jobSize) {
		if (x != null) {
			inOrder(x.left, jobSize);
			//System.out.println("id: "+ x.id + " and free: " + x.free);
			if (x.free >= jobSize) {
				System.out.println("Machine : " + x.id + " has free: " + x.free + " and can take jobSize: " + jobSize);
			}
			inOrder(x.right, jobSize);
		}
	} */
}
