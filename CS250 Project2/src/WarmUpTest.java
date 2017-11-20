/* 
 * An sample main for Warm Up *
 * Do NOT SUBMIT IT, this will be replaced for testing.
*/
import java.util.*;
public class WarmUpTest{
	public static void main(String[] args){
		//The following line  extracts the string input into an integer array for you
		int[] array = readInput();
		for (int i = 0; i < array.length; i ++) System.out.println(array[i]);
		WarmUp warmup = new WarmUp();

		/*
		//test min8Min
		int min = warmup.min8Min(array);
		System.out.println("Min: " + min);
		*/
		//test isSorted8Min
		boolean minSort = warmup.isSorted8Min(array);
		if(minSort) System.out.println("isSorted8Min returns true!");
		else System.out.println("isSorted8Min returns false!");

		//test isSorted8Sort
		boolean sortSort = warmup.isSorted8Sort(array);
		if(sortSort) System.out.println("isSorted8Sort returns true!");
		else System.out.println("isSorted8Sort returns false!");
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
		    if (array[i] > array[i + 1]) 
				return false;
		}
		return true;
	}
}