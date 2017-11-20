import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Selection.java, you must SUBMIT this file.
 * Do not modify any variable definition
 *
 * Multiple Implementations of Selection sort involving Magic Boxes
 *
 * TODO: yang968
 * TODO: Seungho Yang
 * TODO: PSO 10
 * TODO: 10/15/2016
 */

public class Selection{
	public MagicBox magicBox = new MagicBox();
	public int count;

	/**
	 * sortSelection
	 * A standard selection sort
	 *
	 * @param array: The array to be sorted.
	 */
	public void sortSelection(int[] array) {
		// a for loop to control the number of elements that finish sorting
		for (int left = 0; left < array.length - 1; left++) {
			int right = array.length - 1;
			int min = right; //index of the minimum element

			// from last to first unsorted element, find the min
			// and place it into the first unsorted position
			while (right >= left) {
				if(array[right] < array[min]){
					min = right;
				}
				count++;
				right--;
			}

			if(min!=left){
				int temp = array[left];
				array[left] = array[min];
				array[min] = temp;
			}
		}
	}

	/**
	 * TODO: sortSelection8Sort
	 * Selection Sort the array using the 8-Sort Box
	 *
	 * @param array: The array to be sorted.
	 */
	public void sortSelection8Sort(int[] array) {
		if (array.length <= 1) return;

		/* array copy : copy of array that will be referenced to update array
		*  array realIndex: array that keeps track of the indices in sorted order
		*  array result: resulting array
		*  */
		int[] copy = Arrays.copyOf(array, array.length);

		/* If array length is less than or equal to 8, sort them by the result of 8-sort and return */
		if (array.length <= 8) {
			int[] comp = new int[8];
			Arrays.fill(comp, Integer.MAX_VALUE);
			System.arraycopy(array, 0, comp, 0, array.length);
			int[] temp = new int[8];
			temp = magicBox.eightSort(comp);
			for (int i = 0; i < array.length; i++) {
				array[i] = comp[temp[i]];
			}
			return;
		}

		/* If array length is greater than 8, sort by groups of 4 until the whole array is sorted */
		int sortIndex, sICount, currentIndex, cICount;
		for (sortIndex = 0; sortIndex < array.length - 4; sortIndex += 4) {
			int[] comp = new int[8];
			int[] temp = new int[8];
			Arrays.fill(comp, Integer.MAX_VALUE);
			for (currentIndex = sortIndex + 4; currentIndex < array.length; currentIndex += 4) {
				Arrays.fill(comp, Integer.MAX_VALUE);
				sICount = Integer.min(4, array.length - sortIndex);
				cICount = Integer.min(4, array.length - currentIndex);
				System.arraycopy(array, sortIndex, comp, 0, sICount);
				System.arraycopy(array, currentIndex, comp, sICount, cICount);
				temp = magicBox.eightSort(comp);
				for (int i = 0; i < sICount + cICount; i++) {
					if (i < 4) {
						array[sortIndex + i] = comp[temp[i]];
					}
					else {
						array[currentIndex + i - 4] = comp[temp[i]];
					}
				}
			}
		}
		return;
	}

	/**
	 * TODO: sortSelection8Min
	 * Selection Sort the array using the 8-Min Box
	 * Calls helper method SS8minHelper that iterates through the array and find the minimum element's index.
	 *
	 * @param array: The array to be sorted.
	 */
	public void sortSelection8Min(int[] array) {
		if (array.length <= 1) return;
		for (int j = 0; j < array.length-1; j++) {
			int minIndex = SS8minHelper(Arrays.copyOfRange(array, j, array.length));
			/* Swap elements from target index with that of the minimum index */
			int temp = array[j];
			array[j] = array[minIndex + j];
			array[minIndex + j] = temp;
		}
	}

	/**
	 * Finds the index of the minimum of the given array
	 * @param array partial array of the original
	 * @return index of minimum
     */
	public int SS8minHelper(int[] array) {
		int min = -1;
		int[] newArray = new int[8];
		/* If array length is less than 8, pad the remaining spots with Integer.MAX_VALUE before finding the minimum
		 * using eightMin() */
		if (array.length < 8) {
			for (int i = 0; i < newArray.length; i++) {
				if (i < array.length) {
					newArray[i] = array[i];
				} else {
					newArray[i] = Integer.MAX_VALUE;
				}
			}
		}
		int currentMin = magicBox.eightMin(Arrays.copyOfRange((array.length < 8)? newArray : array, 0, 8));
		if (array.length < 8) {
			return currentMin;
		}
		/* If array length is greater than 8, find minimum element from first 8 elements and keep track of it in currentMin
		 * Add the next 7 elements in an array, add the value of the currentMin to the last spot of the 'current' array.
		 * If the returning value from eightMin() is not 7, update the currentMin value.
		 * If the remaining elements is less than 7, pad the remaining spots with Integer.MAX_VALUE and add the value of
		 * currentMin before finding the minimum value index.
		* */
		else {
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
				} else {
					current = Arrays.copyOfRange(array, i, i + 8);
					current[7] = array[currentMin];
					min = magicBox.eightMin(current);
				}
				if (min != 7) {
					currentMin = min + i;
				}
				i += 7;
			}
		}
		return currentMin;
	}
}


/**
 * Selection.java, you should SUBMIT this file.
 * Do not modify any variable definition
 */


