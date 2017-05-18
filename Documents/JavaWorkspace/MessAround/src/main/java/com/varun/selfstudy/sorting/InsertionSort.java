package com.varun.selfstudy.sorting;

/*
Insertion Sort

Input: only a single line containing 20 space separated integers to sort:

91 81 71 61 51 45 52 62 82 92 8 4 6 5 3 1 2 7 21 32 

Algorithm time complexity: O(n^2)
	O(n * (n-1)) =~ O(n^2)

*/

public class InsertionSort implements Sort {

//	public static int[] insertionSort(int[] inputArray) {
//
//		for (int i = 0; i < inputArray.length; i++) {
//			for (int j = i + 1; j > 0 && j < inputArray.length; j++) {
//				if (inputArray[i] > inputArray[j]) {
//					int temp = inputArray[i];
//					inputArray[i] = inputArray[j];
//					inputArray[j] = temp;
//				}
//			}
//		}
//
//		return inputArray;
//	}

	public int[] sort(int[] inputArray) {

		for (int j = 1; j < inputArray.length; j++) {
			int key = inputArray[j];
			int i = j - 1;
			while (i >= 0 && inputArray[i] > key) {
				inputArray[i + 1] = inputArray[i];
				i--;
			}
			inputArray[i + 1] = key;
		}

		return inputArray;
	}

	/**
	@SuppressWarnings("null")
	public static void main(String[] args) {

		Scanner userInput = new Scanner(System.in);
		int[] inputArray = new int[20];
		int[] sortedArray;

		int i = 0;
		for (String s : userInput.nextLine().split(" ")) {
			inputArray[i] = Integer.parseInt(s);
			i++;
		}

		sortedArray = insertionSort(inputArray);
		for (int ia : sortedArray) {
			System.out.print(ia + " ");
		}
	}
	**/
}
