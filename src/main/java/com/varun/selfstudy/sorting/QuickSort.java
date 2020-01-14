package com.varun.selfstudy.sorting;

public class QuickSort implements Sort {

	/*
	* Best case : O(n log n)
	* Worst case: n^2 when all elements in the array are already sorted
	* */
	@Override
	public int[] sort(int[] inputArray) {
		
		quickSort(inputArray, 0, inputArray.length - 1);
		return inputArray;
	}

	private void quickSort(int[] inputArray, int start, int end) {
		if (start < end) {
			int partition = partition(inputArray, start, end);
			quickSort(inputArray, start, partition - 1);
			quickSort(inputArray, partition, end);
		}
	}

	private int partition(int[] inputArray, int start, int end) {
		int i = start - 1;
		int key = inputArray[end];
		for (int j = start; j <= end - 1; j++) {
			if (inputArray[j] <= key) {
				i++;
				int temp = inputArray[i];
				inputArray[i] = inputArray[j];
				inputArray[j] = temp;
			}
		}
		int temp = inputArray[i + 1];
		inputArray[i + 1] = inputArray[end];
		inputArray[end] = temp;

		return i + 1;
	}

}
