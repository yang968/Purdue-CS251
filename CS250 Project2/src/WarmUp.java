/**
 * WarmUp
 *
 * The class where you will implement all of 
 * the warm up methods for project 2. 
 *
 * You MUST SUBMIT this file.
 * Do NOT modify variable & method defintion.
 * TODO: yang968
 * TODO: Seungho Yang
 * TODO: PSO 10
 * TODO: 10/15/2016
 */

import java.util.Arrays;

public class WarmUp {
	private MagicBox magicBox = new MagicBox();
	/**
	 * TODO: min8Min
	 *
	 * returns the minimum element of the given array as determined using 8Min
	 *
	 * @param array - the array of integers being checked
	 * @return min: the value of the least element in array
	 */
	public int min8Min(int[] array){
		/* If array length is less than 8, copy the elements to an array of size 8 and pad remaining
		 * elements to Integer.MAX_VALUE before finding the minimum element */
		if (array.length < 8) {
			int[] current = new int[8];
			for (int i = 0; i < current.length; i++) {
				if (i < array.length) {
					current[i] = array[i];
				}
				else {
					current[i] = Integer.MAX_VALUE;
				}
			}
			return array[magicBox.eightMin(current)];
		}

		/* If array length is 8, find minimum element from array and return right away */
		if (array.length == 8) {
			return array[magicBox.eightMin(array)];
		}

		/* If array length is greater than 8, find minimum element from first 8 elements and keep track of it in currentMin
		 * Add the next 7 elements in an array, add the value of the currentMin to the last spot of the 'current' array.
		 * If the returning value from eightMin() is not 7, update the currentMin value.
		 * If the remaining elements is less than 7, pad the remaining spots with Integer.MAX_VALUE and add the value of
		 * currentMin before finding the minimum value index.
		 * */
		int min = -1;
		int currentMin = magicBox.eightMin(Arrays.copyOfRange(array, 0, 8));
		int i = 8;
		while (i < array.length) {
			int[] current = new int[8];
			if (i + 7 > array.length) {
				current = Arrays.copyOfRange(array, i, i + 8);
				for (int j = array.length - i; j < current.length; j++) {
					current[j] = Integer.MAX_VALUE;
				}
				current[7] = array[currentMin];
				min = magicBox.eightMin(current);
			}
			else {
				current = Arrays.copyOfRange(array, i, i + 8);
				current[7] = array[currentMin];
				min = magicBox.eightMin(current);
			}
			if (min != 7) {
				currentMin = min + i;
			}
			i += 7;
		}
		/* return minimum value of the array */
		return array[currentMin];
	}

	/**
	 * TODO: isSorted8Sort
	 *
	 * checks if the given array is sorted in increasing order using the 8-Sort Magic Box
	 *
	 * @param array - the array of integers being checked
	 * @return: true if sorted, false otherwise
	 */
	public boolean isSorted8Sort(int[] array){
		/*
		It first sends in first 8 elements to eightSort() and checks if the returning array
		returns indices from 0-7 order. If indices do not return 0-7 in numerical order, method returns false.
		If it’s in order, method points to the 8th element (index 7) and sends it to eightSort()
		with 7 next elements.
		 */
		int i;
		int quo = array.length / 7;
		for (i = 0; i < (quo * 7); i+=7) {
			int index = 0;
			int[] currentArray = Arrays.copyOfRange(array, i, i + 8);
			int[] indices = magicBox.eightSort(currentArray);
			for (int j = 0; j < currentArray.length; j++) {
				if (index != indices[j]) {
					return false;
				}
				index++;
			}
		}
		/*
		If there are remaining elements, put them in the remains array and pad the remaining spots
		 with Integer.MAX_VALUE before checking if the remaining elements are sorted.
		 */
		if (array.length % 7 > 1) {
			int[] remains = Arrays.copyOfRange(array, i, i + 8);
			for (int j = 0; j < 8; j++) {
				if (array.length - i <= 0) remains[j] = Integer.MAX_VALUE;
				else {
					remains[j] = array[i];
				}
				i++;
			}
			int[] currentArray = magicBox.eightSort(remains);
			int index = 0;
			for (int k = 0; k < currentArray.length; k++) {
				if (index != currentArray[k]) {
					return false;
				}
				index++;
			}
		}
		//System.out.println("num 8sort: " + magicBox.getCountSort());
		return true;
	}

	/**
	 * TODO: isSorted8Min
	 *
	 * checks if the given array is sorted in increasing order using the 8-Min Magic Box
	 *
	 * @param array - the array of integers being checked
	 * @return: true if sorted, false otherwise
	 */
	public boolean isSorted8Min(int[] array){
		/*
		Go through the array elements one by one until the second last element and run eightMin() of the eight elements.
		If the returning value of the eightMin() equals 0, move on to the next element.
		If eightMin() returns value other than 0, return false as the array is not sorted.
		 */
		for (int i = 0; i < array.length - 1; i++) {
			if (i > array.length-8) {
				int[] copy = Arrays.copyOfRange(array, i, i + 8);
				for (int j = array.length - i; j < copy.length; j++) {
					copy[j] = Integer.MAX_VALUE;
				}
				if (magicBox.eightMin(copy) != 0) {
					return false;
				}
			}
			else if (magicBox.eightMin(Arrays.copyOfRange(array, i, i + 8)) != 0) {
				return false;
			}
		}
		//System.out.println("num 8min: " + magicBox.getCountMin());
		return true;
	}
}
