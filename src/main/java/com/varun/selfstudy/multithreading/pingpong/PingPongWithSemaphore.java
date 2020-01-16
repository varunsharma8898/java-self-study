package com.varun.selfstudy.multithreading.pingpong;

import java.util.concurrent.Semaphore;

public class PingPongWithSemaphore {

    private Semaphore semaphore;

    PingPongWithSemaphore(int count) {
        this.semaphore = new Semaphore(count);
    }

    public void hit(String action) {
        while (true) {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " - " + action);
                semaphore.release();
                Thread.sleep(100);         // necessary here, otherwise the current thread doesnt give up access in time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PingPongWithSemaphore game = new PingPongWithSemaphore(1);
        Thread t1 = new Thread(() -> game.hit("PING"));
        Thread t2 = new Thread(() -> game.hit("PONG"));
        t1.start();
        t2.start();
    }

}
