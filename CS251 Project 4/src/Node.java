
/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/
public class Node {
	
	public int free;	// the free memory remaining in the machine
	public int id; //the machine id of the node
	public int numjobs;	//the number of jobs assigned to this machine
	public Node left, right;	//references to children when in the RBTree
	public Node minJobsNode;	//references of node with the minimum number of jobs in the subtree
	public boolean color;	//red or black; for maintaining balance
	public int size;	//number of elements in the subtree
	
	/*
	 * Constructor for a Node object to be used in a Red-Black tree
	 */
	public Node(int machineid, int freespace, int numjobs){
		this.free = freespace;
		this.id = machineid;
		this.numjobs = numjobs;
		minJobsNode = this;
		left = right = null;
		color = true;
		size = 1;
	}
	
	/*
	 * Add a job to the Node (remove the size)
	 */
	public void addJob(int jobSize) {
		if (jobSize > free)
			return;
		this.free = this.free - jobSize;
		this.numjobs++;
	}
	
	/*
	 * Remove a job from the node (add back the size)
	 */
	public void removeJob(int jobSize) {
		this.free = this.free + jobSize;
		this.numjobs--;
	}	
}

/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/
