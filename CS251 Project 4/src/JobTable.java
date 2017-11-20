//Job Table


/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/

import java.util.HashMap;

public class JobTable {
	
	//Definition of an individual Job
	private class Job{
		int size;	//the free space being taken up by the job
		int machine;	//the machine ID corresponding to the machine which has been assigned this job
		
		//Constructor for the job class
		private Job(int size, int machine) {
			this.size = size;
			this.machine = machine;
		}
	}
	//Hash maps mapping machineID's to the corresponding Node and jobIDs to the corresponding Job
	private HashMap<Integer, Node> machineMap;
	private HashMap<Integer, Job> map;
	
	//JobTable Constructor
	public JobTable() {
		machineMap = new HashMap<Integer, Node>();
		map = new HashMap<Integer, Job>();
	}
	
	//add a new job to the job table
	public void addJob(int jobid, int size, Node machine) {
		//Map the new job and (re)map the corresponding machine node
		map.put(jobid, new Job(size, machine.id));
		machineMap.put(machine.id, machine);
	}
	
	//delete a job from the job table
	public void deleteJob(int jobid, Node machine) {
		//remove the job from the job hashmap
		map.remove(jobid);
		//remap the machine node
		machineMap.put(machine.id,machine);
	}
	
	//return the memory occupied by a job
	public int jobSize(int jobid) {
		Job j = map.get(jobid);
		if (j == null) {
			throw new IllegalArgumentException("Job not found");
		}
		return j.size;
	}
	
	//return the Node of machine running a job
	public Node jobMachine(int jobid) {
		Job j = map.get(jobid);
		if (j == null) return null;
		int machineID = j.machine;
		Node machineNode = machineMap.get(machineID);
		if (j == null) {
			throw new IllegalArgumentException("Job not found");
		}
		return machineNode;
	}

}

/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/
