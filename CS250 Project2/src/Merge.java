import java.util.Arrays;

/**
 * Merge.java, you MUST SUBMIT this file.
 *
 * Multiple Implementations of merge sort involving Magic Boxes
 *
 * TODO: yang968
 * TODO: Seungho Yang
 * TODO: PSO 10
 * TODO: 10/15/2016
 */

public class Merge{
	public MagicBox magicBox = new MagicBox();
	public int count; //Counter for the number of comparisons in the standard algorithm

	/**
	 * sortMerge
	 * A standard merge sort
	 *
	 * @param array: The array to be sorted.
	 */
	public void sortMerge(int[] array) {
		//Implemented merge sort and increment "count" 
		//to keep track of the number of pairwise comparisons
		if (array.length <= 1) return;

		// Split the array in half
		int[] first = new int[array.length / 2];
		int[] second = new int[array.length - first.length];
		System.arraycopy(array, 0, first, 0, first.length);
		System.arraycopy(array, first.length, second, 0, second.length);

		// Sort each half
		sortMerge(first);
		sortMerge(second);

		// Merge the halves together, overwriting the original array
		standardMerge(first, second, array);
	}

	/**
	 * standardMerge
	 *
	 * merges two arrays back together for merge sort
	 *
	 * @param a the first array being merged
	 * @param b the second array being merged
	 * @param result the target array where the other two arrays are being merged
	 */
	private void standardMerge(int[] a, int[] b, int[] result) {
		// Merge both halves into the result array
		// Next element to consider in the first array
		int aIndex = 0;
		// Next element to consider in the second array
		int bIndex = 0;

		// Next open position in the result
		int j = 0;
		// As long as neither iFirst nor iSecond is past the end, move the
		// smaller element into the result.

		while (aIndex < a.length && bIndex < b.length) {
			count++;
			if (a[aIndex] < b[bIndex]) {
				result[j] = a[aIndex];
				aIndex++;
			} else {
				result[j] = b[bIndex];
				bIndex++;
			}
			j++;
		}
		// copy what's left
		System.arraycopy(a, aIndex, result, j, a.length - aIndex);
		System.arraycopy(b, bIndex, result, j, b.length - bIndex);
	}


	/**
	 * TODO: sortMerge8Sort
	 * Merge Sort the array using the 8-Sort Box
	 *
	 * @param array: The array to be sorted.
	 */
	public void sortMerge8Sort(int[] array) {
		/*
		Base case is when array length <= 8. copy array makes a copy of the array.
		When it's less than 8, pad remaining elements in copy with Integer.MAX_VALUE before sending to eightSort().
		Rearrange of the array based on the returned indices in indices array.
		 */
		if (array.length <= 8) {
			int[] copy = Arrays.copyOfRange(array, 0, 8);
			for (int i = array.length; i < copy.length; i++) {
				copy[i] = Integer.MAX_VALUE;
			}
			int[] indices = magicBox.eightSort(copy);
			for (int j = 0; j < array.length; j++) {
				array[j] = copy[indices[j]];
			}
			return;
		}

		/* Divide the array until the array length is less than or equal to 8 (base case) */
		int[] first = new int[array.length / 2];
		int[] second = new int[array.length - first.length];
		System.arraycopy(array, 0, first, 0, first.length);
		System.arraycopy(array, first.length, second, 0, second.length);

		sortMerge8Sort(first);
		sortMerge8Sort(second);

		/* merge the two arrays */
		merge8(first, second, array);
	}

	/**
	 * merge the arrays a and b after sorting them to result array
	 * @param a first group of elements that is sorted
	 * @param b second group of elements that is sorted
	 * @param result resulting array where elements from a and b are merged into
     */
	public void merge8(int[] a, int[] b, int[] result) {
		/* aIndex and bIndex keeps track of how which elements still need to be sorted
		* If an element from aIndex or bIndex is sent to results, respective index will be incremented
		* comp array holds the elements that need to be sorted.
		* */
		int aIndex = 0;
		int bIndex = 0;
		int index = 0;
		int[] comp = new int[8];

		while (aIndex < a.length && bIndex < b.length) {
			Arrays.fill(comp, Integer.MAX_VALUE);
			int numCopyA = Integer.min(4, a.length - aIndex);
			int numCopyB = Integer.min(4, b.length - bIndex);
			System.arraycopy(a, aIndex, comp, 0, numCopyA);
			System.arraycopy(b, bIndex, comp, numCopyA, numCopyB);
			int[] copy = Arrays.copyOf(comp, comp.length);
			int[] indices = magicBox.eightSort(copy);
			int aCount = 0;
			int bCount = 0;
			for (int i = 0; aCount < numCopyA && bCount < numCopyB; i++) {
				comp[i] = copy[indices[i]];
				if (indices[i] < numCopyA) {
					aIndex++;
					aCount++;
				} else if (indices[i] < numCopyA + numCopyB) {
					bIndex++;
					bCount++;
				}
				result[index++] = comp[i];
			}
		}
		System.arraycopy(a, aIndex, result, index, a.length - aIndex);
		System.arraycopy(b, bIndex, result, index, b.length - bIndex);
	}
}
 /* Merge.java, you MUST SUBMIT this file. 
  * Do not modify any variable definition
  */
