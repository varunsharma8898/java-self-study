package com.varun.selfstudy.sorting;

import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SortTest {

	private static final int LONGNUMBER = 100000;

	private int[] inputArray = new int[LONGNUMBER];
	private int[] expectedArray = new int[LONGNUMBER];
	private Sort sortObj;
	private long start;

	private Random rand = new Random();

	@Before
	public void setUp() {
		for (int i = 0; i < LONGNUMBER; i++) {
			inputArray[i] = rand.nextInt();
			expectedArray[i] = inputArray[i];
		}
		Arrays.sort(expectedArray);
		start = System.currentTimeMillis();
	}

	@After
	public void tearDown() {
		double timeTaken = System.currentTimeMillis() - start;
		timeTaken = timeTaken / 1000;
		System.out.println("Time: " + timeTaken + " seconds");
	}

	@Test
	public void testInsertionSort() {
		sortObj = new InsertionSort();

		int[] sortedArray = sortObj.sort(inputArray);

		System.out.println("Testing Insertion Sort with [" + LONGNUMBER + "] elements.");

		Assert.assertArrayEquals(expectedArray, sortedArray);
	}

	@Test
	public void testBubbleSort() {
		sortObj = new BubbleSort();

		int[] sortedArray = sortObj.sort(inputArray);

		System.out.println("Testing Bubble Sort with [" + LONGNUMBER + "] elements.");

		Assert.assertArrayEquals(expectedArray, sortedArray);
	}

	@Test
	public void testMergeSort() {
		sortObj = new MergeSort();

		int[] sortedArray = sortObj.sort(inputArray);

		System.out.println("Testing Merge Sort with [" + LONGNUMBER + "] elements.");

		Assert.assertArrayEquals(expectedArray, sortedArray);
	}

	@Test
	public void testHeapSort() {
		sortObj = new HeapSort();

		int[] sortedArray = sortObj.sort(inputArray);

		System.out.println("Testing Heap Sort with [" + LONGNUMBER + "] elements.");

		Assert.assertArrayEquals(expectedArray, sortedArray);
	}

	@Test
	public void testQuickSort() {
		sortObj = new QuickSort();

		int[] sortedArray = sortObj.sort(inputArray);

		System.out.println("Testing Quick Sort with [" + LONGNUMBER + "] elements.");

		Assert.assertArrayEquals(expectedArray, sortedArray);
	}
}
