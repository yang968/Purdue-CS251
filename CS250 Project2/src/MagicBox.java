/**
 * MagicBox.java
 *		- Do NOT SUBMIT this file.
 *		- Do NOT MODIFY this file. It will be replaced even if you submit it.
 *
 * Class containing "magic" array sorting methods
 * Also contains helper functions
 */

import java.util.*;

public class MagicBox {
	//Counters for the number of times 8-Sort and 8-Min are used
	private long countSort;
	private long countMin;

	/**
	 * MagicBox
	 *
	 * Constructor for the MagicBox class
	 * Sets the initial values of the counters
	 */
	public MagicBox() {
		countSort = 0;
		countMin = 0;
	}

	/**
	 * getCountSort
	 *
	 * returns the number of times that the 8-Sort box has been used
	 *
	 * @return the integer value of countSort 
	 */
	public long getCountSort() {
		return countSort;
	}

	/**
	 * getCountMin
	 *
	 * returns the number of times that the 8-Min box has been used
	 *
	 * @return the integer value of countMin
	 */
	public long getCountMin() {
		return countMin;
	}

	/**
	* eightSort
	* 
	* Sort an array of length 8.
	*
	* @param array The array to be sorted.
	* @return An array that gives the sorted order of each element in the original array
	*/
	public int[] eightSort(int[] array) {
		countSort++;
		if (array.length != 8)
			return null;
		else {
			int[] newArray = array.clone();
			boolean[] flag = new boolean[8];
			Arrays.sort(newArray);
			int[] rank = new int[8];
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (newArray[i] == array[j]) {
						if (!flag[j]) {
							rank[i] = j;
							flag[j] = true;
							break;
						}
					}
				}
			}
			return rank;
		}
	}

	/**
	* eightMin
	*
	* Find the min element in the array with 8 numbers in it.
	*
	* @param array The array to be checked
	* @return A number that indicates the index of the minimum element
	*/
	public int eightMin(int[] array) {
		countMin++;
		if (array.length != 8)
			return -1;
		else {
			int min = array[0];
			int pos = 0;

			for (int i = 1; i < array.length; i++) {
				if (array[i] < min) {
					pos = i;
					min = array[i];
				}
			}
			return pos;
		}
	}
}


 /*		- Do NOT SUBMIT this file.
  *		- Do NOT MODIFY this file. It will be replaced even if you submit it. 
  */
