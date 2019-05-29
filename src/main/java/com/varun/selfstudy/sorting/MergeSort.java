package com.varun.selfstudy.sorting;

import java.util.Arrays;

public class MergeSort implements Sort {

	@Override
	public int[] sort(int[] inputArray) {
		mergeSort(inputArray);
		return inputArray;
	}

	private void mergeSort(int[] inputArray) {
		if (inputArray.length < 2)
			return;

		int mid = inputArray.length / 2;
		
		int[] leftArray = Arrays.copyOfRange(inputArray, 0, mid);   // endIndex is not inclusive in Arrays.copyOfRange
		int[] rightArray = Arrays.copyOfRange(inputArray, mid, inputArray.length);

		mergeSort(leftArray);
		mergeSort(rightArray);
		merge(inputArray, leftArray, rightArray);
	}

	private void merge(int[] inputArray, int[] leftArray, int[] rightArray) {
		int i = 0, j = 0, k = 0;
		while (i < leftArray.length && j < rightArray.length) {
			if (leftArray[i] < rightArray[j]) {
				inputArray[k++] = leftArray[i++];
			}
			else {
				inputArray[k++] = rightArray[j++];
			}
		}
		while (i < leftArray.length) {
			inputArray[k++] = leftArray[i++];
		}
		while (j < rightArray.length) {
			inputArray[k++] = rightArray[j++];
		}
	}

}
