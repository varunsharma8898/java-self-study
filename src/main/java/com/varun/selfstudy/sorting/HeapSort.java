package com.varun.selfstudy.sorting;

public class HeapSort implements Sort {

	@Override
	public int[] sort(int[] inputArray) {
		heapSort(inputArray);                                // Total = O(n log n).
		return inputArray;
	}

	private void heapSort(int[] inputArray) {
		int maxIndex = inputArray.length - 1;
		buildMaxHeap(inputArray, maxIndex);                  // O(n)
		for (int i = maxIndex; i > 0; i--) {
			int temp = inputArray[0];
			inputArray[0] = inputArray[i];
			inputArray[i] = temp;
			maxHeapify(inputArray, i - 1, 0);   // O(log n)
		}
	}

	private void buildMaxHeap(int[] inputArray, int maxIndex) {
		for (int i = maxIndex / 2; i >= 0; i--) {
			maxHeapify(inputArray, maxIndex, i);
		}
	}

	private void maxHeapify(int[] inputArray, int maxIndex, int i) {
		int left = 2 * i + 1;
		int right = left + 1;
		int largest;

		if (left <= maxIndex && inputArray[left] > inputArray[i]) {
			largest = left;
		} else {
			largest = i;
		}

		if (right <= maxIndex && inputArray[right] > inputArray[largest]) {
			largest = right;
		}

		if (largest != i) {
			int temp = inputArray[i];
			inputArray[i] = inputArray[largest];
			inputArray[largest] = temp;

			maxHeapify(inputArray, maxIndex, largest);
		}
	}

}
