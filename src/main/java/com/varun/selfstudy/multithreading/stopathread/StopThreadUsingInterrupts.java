package com.varun.selfstudy.multithreading.stopathread;

public class StopThreadUsingInterrupts implements Runnable {

    private volatile boolean isTerminated;

    StopThreadUsingInterrupts() {
        this.isTerminated = false;
    }

    public void terminateThread() {
        System.out.println(Thread.currentThread().getName() + " - terminating the thread...");
        isTerminated = true;
    }

    @Override
    public void run() {
        while (!isTerminated) {
            try {
                System.out.println(Thread.currentThread().getName() + " - sleep for 1 sec");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " - Interrupted!");
                isTerminated = true;
            }
        }
        System.out.println(Thread.currentThread().getName() + " - Terminated!");
    }

    public static void main(String[] args) {
        StopThreadUsingInterrupts r1 = new StopThreadUsingInterrupts();
        Thread t1 = new Thread(r1);
        t1.start();
        try {
            System.out.println(Thread.currentThread().getName() + " - sleep for 5 sec");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " - Interrupted!");
        }

        t1.interrupt();
    }
}
