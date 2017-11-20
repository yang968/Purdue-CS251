/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/

import java.util.*;

public class TestTree {
	
	public static void main(String[] args){
		int numOfMachine = 0;
		int freeSpace = 0;
		int strategy = 0;
		
		for (int i = 0; i < args.length; i++){
			switch (args[i]){
				case "-h":
				case "--help":
					System.out.println("usage: java Test [options] [< query input]");
					System.out.println("-s [stratygy]\tStragety to use to schedule jobs, minSpace/ms or minJob/mj");
					System.out.println("-m [N] [M]\tAllocate n machines with free space M");
					return;
				case "-s":
					switch(args[++i].toLowerCase()){
						case "ms":
						case "minspace":
							strategy = 0;
							break;
						case "mj":
						case "minjob":
							strategy = 1;
							break;
					}

					break;
				case "-m":
					numOfMachine = Integer.parseInt(args[++i]);
					freeSpace = Integer.parseInt(args[++i]);
					if (numOfMachine <= 0 || freeSpace <= 0){
						System.out.println("You must specify positive number of machines and freeSpace");
						return;
					}	
					break;
			}
		}
		if (numOfMachine <= 0 || freeSpace <= 0 ){
			System.out.println("You must specify positive number of machines and freeSpace");
			return;
		}
		/* Build Initial Tree with m machines*/
		SearchTree stree = new SearchTree(freeSpace,numOfMachine);
		/* Read input Query */
		readInputQuery(stree, strategy);
	}

	public static void readInputQuery(SearchTree st, int strategy){
		//DEBUGGING
		//int[] machines = new int[st.getNumOfMachine()];

		Scanner s = new Scanner(System.in);
		int sch = 0;
		int r = 0;
		int queries = 0;
		while (s.hasNext()){
			String query = s.next();
			switch (query){
				case "S":
					int jobID = s.nextInt();
					int load = s.nextInt();
					if (strategy == 1)
						if (st.scheduleJobMinJob(jobID,load) == -1) {
							r++;
						}else sch++;
					else {
						if (st.scheduleJobMinSpace(jobID, load) == -1) {
							r++;
						} else sch++;
					}
					//sch++;
					break;
				case "R":
					int releasejobID = s.nextInt();
					st.releaseJob(releasejobID);
					//r++;
					break;
				case "C":
					int memory = s.nextInt();
					int ret = st.count(memory);
					System.out.format("Count %d: There are %d machines avaiable\n",memory,ret);
					break;
				case "M":
					st.measureUtility();
					//System.out.println("Scheduled: " + sch + "Released: " + r + "based on text file");
					//System.out.println("Scheduled - Released = " + (sch - r));
					break;
			}
			queries++;
		}
		//System.out.println("Scheduled - Released = " + (sch - r) + "at the end");
		//System.out.println("Total load : " + sch);
		//System.out.println("total jobs that succeeded: " + sch);
		//System.out.println("total jobs that failed: " + r);
	}	
}

/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/
