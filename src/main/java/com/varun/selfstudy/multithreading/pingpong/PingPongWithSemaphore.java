package com.varun.selfstudy.multithreading.pingpong;

import java.util.concurrent.Semaphore;

public class PingPongWithSemaphore {

    private Semaphore pingSemaphore;
    private Semaphore pongSemaphore;

    PingPongWithSemaphore() {
        this.pingSemaphore = new Semaphore(1);
        this.pongSemaphore = new Semaphore(0);
    }

    public void ping() {
        while (true) {
            try {
                pingSemaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " - PING");
                pongSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pong() {
        while (true) {
            try {
                pongSemaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " - PONG");
                pingSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PingPongWithSemaphore game = new PingPongWithSemaphore();
        Thread t1 = new Thread(() -> game.ping());
        Thread t2 = new Thread(() -> game.pong());
        t1.start();
        t2.start();
    }

}
