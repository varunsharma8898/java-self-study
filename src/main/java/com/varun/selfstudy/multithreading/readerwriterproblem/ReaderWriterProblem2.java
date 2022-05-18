package com.varun.selfstudy.multithreading.readerwriterproblem;

import java.util.concurrent.Semaphore;

public class ReaderWriterProblem2 {

    private Semaphore reader;
    private Semaphore writer;
    private volatile int readerCount;

    ReaderWriterProblem2() {
        reader = new Semaphore(1, true);
        writer = new Semaphore(1, true);
        readerCount = 0;
    }

    // incomplete

    private void read() throws InterruptedException {
        reader.acquire();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " - reading...");
        } finally {
            reader.release();
        }
    }

    private void write() throws InterruptedException {
//        writer.acquire();
//        try {
//            Thread.sleep(1000);
//            reader.acquire();
//            readerCount++;
//            if (readerCount == 1)
//        } finally {
//            writer.release();
//        }
    }

    public static void main(String[] args) {
        ReaderWriterProblem2 obj = new ReaderWriterProblem2();
        Runnable readerTask = () -> {
            while (true) {
                try {
                    obj.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable writerTask = () -> {
            while (true) {
                try {
                    obj.write();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
