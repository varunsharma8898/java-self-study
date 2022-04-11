package com.varun.selfstudy.datastructures;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {

    private int capacity;

    private List<T> queue;

    BlockingQueue(int capacity) {
        queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void enqueue(T item) throws InterruptedException {

        while (queue.size() == capacity) {
            wait();
        }
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " - Enqueuing item = " + item);
        this.queue.add(item);
    }

    public synchronized T dequeue() throws InterruptedException {

        /**
         * // if (queue.size == 0)
         * if a condition is satisfied, multiple thread will be notified and only one will be able to complete
         * the others might get null value and so unexpected things will happen.
         * so its best to check this condition in a while loop instead of a simple if condition.
         * */
        while (queue.size() == 0) {                               // v-imp to use while instead of simple if condition, see notes above
            wait();
        }
        notifyAll();
        T item = this.queue.remove(0);
        System.out.println(Thread.currentThread().getName() + " - Dequeuing item = " + item);
        return item;
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> q = new BlockingQueue<>(10);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    q.enqueue(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 100; i < 110; i++) {
                try {
                    Thread.sleep(100);
                    q.enqueue(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100; i ++) {
                try {
                    Thread.sleep(100);
                    System.out.println(q.dequeue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
