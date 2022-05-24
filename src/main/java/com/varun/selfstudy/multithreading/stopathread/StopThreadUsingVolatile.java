package com.varun.selfstudy.multithreading.stopathread;

public class StopThreadUsingVolatile implements Runnable {

    private volatile boolean isTerminated;

    StopThreadUsingVolatile() {
        this.isTerminated = false;
    }

    public void terminateThread() {
        System.out.println(Thread.currentThread().getName() + " - terminating the thread...");
        this.isTerminated = true;
    }

    @Override
    public void run() {
        while (!isTerminated) {
            try {
                System.out.println(Thread.currentThread().getName() + " - Sleeping for 1 sec...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " - InterruptedException!");
            }
        }

        System.out.println(Thread.currentThread().getName() + " - Terminated...");
    }

    public static void main(String[] args) {
        StopThreadUsingVolatile r1 = new StopThreadUsingVolatile();
        Thread t1 = new Thread(r1);
        t1.start();

        try {
            System.out.println(Thread.currentThread().getName() + " - Sleeping for 5 sec...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " - InterruptedException!");
        }

        r1.terminateThread();

        /**
         *
         * Same as using volatile, just that we're not using runnable terminateThread() here.
         * Instead we're using thread.interrupt() which is a better way to stop a thread.
         *
         * */
    }
}
