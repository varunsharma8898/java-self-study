package com.varun.selfstudy.multithreading.pingpong;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PingPongWithLock {

    String name;

    Lock lock = new ReentrantLock();

    Condition pinged;

    Condition ponged;

    public static volatile boolean isPinged;

    PingPongWithLock(String name) {
        this.name = name;
        this.pinged = lock.newCondition();
        this.ponged = lock.newCondition();
    }

    public void ping() {
        try {
            Thread.sleep(1000);
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " PING");
                pinged.signalAll();
                ponged.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void pong() {
        try {
            Thread.sleep(1000);
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " PONG");
                ponged.signalAll();
                pinged.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final PingPongWithLock p1 = new PingPongWithLock("pingpong");
        Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    p1.ping();
                }
            }
        };
        t1.start();
        Thread t2 = new Thread() {
            public void run() {
                while (true) {
                    p1.pong();
                }
            }
        };
        t2.start();
    }
}
