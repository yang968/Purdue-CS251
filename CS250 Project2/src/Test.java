/**
 * Test
 *
 * @author
 * @pso
 * @date 
 */
import java.util.*;
public class Test{
	public static enum Algorithm{Selection, MagicMinSelection, MagicSortSelection, MagicMerge, Merge};
	//Do not include any of your implementation in this file
	public static void main(String[] args) {
		//The following line  extracts the string input into an integer array for you
		Algorithm alg = Algorithm.Selection;
		for (int i = 0; i < args.length; i++){
			switch(args[i]){
				case "-h" :
				case "--help" :
					System.out.println("usage: Sort [options] [< data]");
					System.out.println("-a <algorithm>\tuse specified algorithm (default = selection)");
					System.out.println("\t selection or s for standard selection sort");
					System.out.println("\t merge or m for standard merge sort");
					System.out.println("\t magicminselection or mms for Selection Sort use Min-Box");
					System.out.println("\t magicsortselection, mss, magiceightselection, mes, m8s for Selection Sort use Magic-Box");
					System.out.println("\t magicmerge or mn for merge sort use magic box");
					return;
				case "-a":
					switch(args[++i].toLowerCase()){
						case "selection" :
						case "s" :
							alg = Algorithm.Selection;
							break;
						case "merge" :
						case "m" :
							alg = Algorithm.Merge;	
							break;
						case "magicminselection" :
						case "mms" :
							alg = Algorithm.MagicMinSelection;
							break;
						case "magiceightselection" :
						case "magicsortselection" :
						case "mss" :
						case "m8s" :
						case "mes" :
							alg = Algorithm.MagicSortSelection;
							break;
						case "magicmerge" :
						case "mm" :
							alg = Algorithm.MagicMerge;	
							break;												
					}
			}
		}

		int[] data= readInput(); 
		Merge merge = new Merge();
		Selection select = new Selection();
		switch(alg){
			case Selection:
				select.sortSelection(data);
				System.out.println("Standard Selection Sort");
				System.out.println("Comparisons: " + select.count);
				break;
			case MagicMinSelection:
				select.sortSelection8Min(data);
				System.out.println("8-Min Selection Sort");
				System.out.println("8-Sort Uses: " + select.magicBox.getCountMin());
				break;
			case MagicSortSelection:
				select.sortSelection8Sort(data);
				System.out.println("8-Sort Selection Sort");
				System.out.println("8-Sort Uses: " + select.magicBox.getCountSort());
				break;
			case Merge:
				merge.sortMerge(data);
				System.out.println("Standard Merge Sort");
				System.out.println("Comparisons: " + merge.count);
				break;
			case MagicMerge:
				merge.sortMerge8Sort(data);
				System.out.println("8-Sort Merge Sort");
				System.out.println("8-Sort Uses: " + merge.magicBox.getCountSort());
				break;
		}
		if(isSorted(data)) System.out.printf("\033[92mSort Passed\033[0m\n");
		else System.out.printf("\033[91mSort Failed\033[0m\n");
		

	}
	/* Read input from file, you can assume this input reader will never crash */
	public static int[] readInput(){
		Scanner s = new Scanner(System.in);

		int n = s.nextInt();
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
		    array[i] = s.nextInt();
		}

		return array;
	}
	/* Check if an array is sorted */
	public static boolean isSorted(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
		    if (array[i] > array[i + 1]) {
				System.out.println(i + " " + array[i] + " EEEEEEEEEEEEEEEEEEEEEEEEEEEE");
				return false;
			}
		}
		return true;
	}

}
