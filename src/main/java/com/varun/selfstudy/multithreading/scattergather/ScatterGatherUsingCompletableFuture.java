package com.varun.selfstudy.multithreading.scattergather;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ScatterGatherUsingCompletableFuture {

    /**
     * Q: Fetch prices from 3 different websites, timeout 3 seconds
     *
     * */

    public static void main(String[] args) {

        ScatterGatherUsingCompletableFuture obj = new ScatterGatherUsingCompletableFuture();
        try {
            Set<Integer> prices = obj.getPrices(1);
            System.out.println(prices);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Set<Integer> getPrices(int productId) throws InterruptedException, ExecutionException {
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>()); // v-imp to use syncSet

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(new Task("url-1", productId, prices));
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(new Task("url-2", productId, prices));
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(new Task("url-3", productId, prices));

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(future1, future2, future3);
        try {
            allTasks.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("Thread timeout, consume exception");
        }

        return prices;
    }


    private class Task implements Runnable {

        private Set<Integer> prices;

        String url;
        int productId;

        Task(String url, int productId, Set<Integer> prices) {
            this.url = url;
            this.productId = productId;
            this.prices = prices;
        }

        @Override
        public void run() {
            int price = 0;

            // make http call to url and fetch price
            // for now, just put random val
            price = new Random().nextInt(100);

            // To timeout thread 3
            if (this.url.equals("url-3")) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            prices.add(price);
        }
    }
}
