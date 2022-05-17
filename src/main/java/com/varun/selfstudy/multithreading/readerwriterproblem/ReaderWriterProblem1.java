package com.varun.selfstudy.multithreading.readerwriterproblem;

import java.util.concurrent.Semaphore;

public class ReaderWriterProblem1 {

    private Semaphore reader;

    private Semaphore writer;

    private volatile static int readerCount;

    /**
     * Reader writer problem:
     * Multiple readers allowed at one time to read,
     * only one writer allowed at one time.
     * At any given point in time, both reader and writer
     * cannot perform operation at the same time.
     * <p>
     * First solution:
     * Whenever the first reader thread acquires a lock,
     * acquire a write lock as well so that no writes can happen.
     * Other reader threads can read until reader thread count reduces to 0.
     * When it reduces to 0, one writer thread can start writing by acquiring write lock.
     * Reader threads can't read until writer thread is finished.
     * <p>
     * The problem with this solution is that it can lead to writer thread starvation.
     * To simulate this, start threads t4 and t5 after t1-3 thread in below example.
     */

    ReaderWriterProblem1() {
        reader = new Semaphore(1, true);
        writer = new Semaphore(1, true);
        readerCount = 0;
    }

    private void read() throws InterruptedException {
        reader.acquire();
        try {
            readerCount++;
            if (readerCount == 1) {
                writer.acquire();
            }
        } finally {
            reader.release();
        }

        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " - reading...");

        try {
            reader.acquire();
            readerCount--;
            if (readerCount == 0) {
                writer.release();
            }
        } finally {
            reader.release();
        }
    }

    private void write() throws InterruptedException {
        writer.acquire();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " - writing...");
            writer.release();
        } finally {
            writer.release();
        }

    }

    public static void main(String[] args) {
        ReaderWriterProblem1 rw = new ReaderWriterProblem1();
        Runnable readerTask = () -> {
            while (true) {
                try {
                    rw.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writerTask = () -> {
            while (true) {
                try {
                    rw.write();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(readerTask);
        Thread t2 = new Thread(readerTask);
        Thread t3 = new Thread(readerTask);

        Thread t4 = new Thread(writerTask);
        Thread t5 = new Thread(writerTask);

        t4.start();
        t5.start();

        t1.start();
        t2.start();
        t3.start();

        // Leads to writer threads starvation
        /**
         t1.start();
         t2.start();
         t3.start();

         t4.start();
         t5.start();
         */

    }

}
