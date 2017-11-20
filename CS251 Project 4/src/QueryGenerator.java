/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/

import java.util.*;
import java.lang.*;

public class QueryGenerator {
	
	public static void main(String[] args){
		int jobs = 0;
		int maxload = 0;
		boolean counting = false;
		boolean release = false;
		boolean utility = false;
		for (int i = 0; i < args.length; i++){
			switch (args[i]){
				case "-h":
				case "--help":
					System.out.println("usage: java QueryGenerator [options] [>output file name]");
					System.out.println("-s [n] [maxload]\tSchedule n jobs, each job takes up to maxload space");
					System.out.println("-c\tEnable counting query");
					System.out.println("-r\tEnable random release query");
					System.out.println("-m\tEnable measurement query");
					return;
				case "-c":
					counting = true;
					break;
				case "-r":
					release = true;
					break;
				case "-m":
					utility = true;
					break;
				case "-s":
					jobs = Integer.parseInt(args[++i]);
					maxload = Integer.parseInt(args[++i]);
					if (jobs  <= 0 || maxload <= 0){
						System.out.println("You must specify positive number of queries and maxload");
						return;
					}	
					break;
			}
		}
		if (jobs  <= 0 || maxload <= 0){
			System.out.println("You must specify positive number of queries and maxload");
			return;
		}
		int trivialCount = jobs/10;
		ArrayList<Integer> livingJobs = new ArrayList<Integer>();
		for (int i = 0; i < jobs; i ++){
			int randomLoad;
			int coin = (int)(Math.random()*10);
			/* 80% light job, 20% heavy jobs */
			if (coin > 7){
				int minLoad = (int)(0.8*maxload);
				randomLoad = minLoad + (int)(Math.random() * (maxload-minLoad));
			}
			else{
				int max = (int)(0.2*maxload);
				randomLoad= 1 + (int)(Math.random() * (max-1));
			}
			livingJobs.add(i);
			System.out.format("S\t%d\t%d\n",i,randomLoad);
			if (counting && i%trivialCount == 1){
				int randomMemory = (int)(Math.random() * 2*maxload);
				System.out.format("C\t%d\n",randomMemory);
			}
			if (utility && i%trivialCount == 1){
				System.out.format("M\n");
			}
			if (release){
				double prRelease = Math.random();
				if (prRelease < 0.15){
					
          int randomJob = (int)(Math.random()* (livingJobs.size() - 1));
          int job = livingJobs.get(randomJob);
					livingJobs.remove(randomJob);
					System.out.format("R\t%d\n",job);					
				}
			}

		}


	}

	public static ArrayList<Machine> readInputQuery(){
		Scanner s = new Scanner(System.in);

		int n = s.nextInt(); // # of machines
		ArrayList<Machine> ret = new ArrayList<Machine>();
			for (int i = 0; i < n; i++) {
			    int freeSpace = s.nextInt(); 
			    ret.add(new Machine(freeSpace,i,0));
			}
		return ret;
	}	
}

/*
* DO NOT MODIFY THIS FILE FOR YOUR IMPLEMENTATION
* THIS FILE WILL BE REPLACED DURING TESTING
*/
