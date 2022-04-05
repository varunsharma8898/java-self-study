package com.varun.selfstudy.algorithm;

public class MaxSumSubarray {

    /**
     * Kadane's Algorithm: to find a contiguous subarray with the largest sum
     */

    public int findLargestSumInSubarray(int[] arr) {

        int maxSoFar = 0, maxEndingHere = 0, minSoFar = 0;

        for (int i : arr) {
            maxEndingHere = maxEndingHere + i;
            if (maxEndingHere < 0) {
                maxEndingHere = 0;
            }
            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
            }
            minSoFar = Math.min(i, minSoFar);
        }

        if (maxSoFar == 0) {
            return minSoFar;
        }
        return maxSoFar;
    }

    public static void main(String[] args) {
        MaxSumSubarray msb = new MaxSumSubarray();
        System.out.println(msb.findLargestSumInSubarray(new int[] { 1, 2, 3, 0, -6, 5, 4, 1 }));
        System.out.println(msb.findLargestSumInSubarray(new int[] { -1, -1, -1, -1 }));
        System.out.println(msb.findLargestSumInSubarray(new int[] { 1, 2, 3, 0, -3, 4, 5, -10 }));
    }

}
