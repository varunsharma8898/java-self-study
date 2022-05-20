package com.varun.selfstudy.multithreading.scattergather;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ScatterGatherUsingCountdownLatch {
    /**
     * Q: Fetch prices from 3 different websites, timeout 3 seconds
     *
     * */

    public static void main(String[] args) {

        ScatterGatherUsingCountdownLatch obj = new ScatterGatherUsingCountdownLatch();
        try {
            Set<Integer> prices = obj.getPrices(1);
            System.out.println(prices);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Set<Integer> getPrices(int productId) throws InterruptedException {
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>()); // v-imp to use syncSet
        CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread(new Task("url-1", productId, prices, latch));
        Thread t2 = new Thread(new Task("url-2", productId, prices, latch));
        Thread t3 = new Thread(new Task("url-3", productId, prices, latch));

        t1.start(); t2.start(); t3.start();

        latch.await(3, TimeUnit.SECONDS);
        return prices;
    }


    private class Task implements Runnable {

        private Set<Integer> prices;
        private CountDownLatch latch;

        String url;
        int productId;

        Task(String url, int productId, Set<Integer> prices, CountDownLatch latch) {
            this.url = url;
            this.productId = productId;
            this.prices = prices;
            this.latch = latch;
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
            latch.countDown();
        }
    }

}
