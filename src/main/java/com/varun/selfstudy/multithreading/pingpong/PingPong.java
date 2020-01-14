package com.varun.selfstudy.multithreading.pingpong;

public class PingPong implements Runnable {

    private static final Object lock = new Object();

    String name;

    PingPong(String name) {
        this.name = name;
    }

    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    Thread.sleep(1000);
                    System.out.println(name);
                    lock.notify();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new PingPong("ping"));
        Thread t2 = new Thread(new PingPong("pong"));
        t1.start();
        t2.start();
    }
}
