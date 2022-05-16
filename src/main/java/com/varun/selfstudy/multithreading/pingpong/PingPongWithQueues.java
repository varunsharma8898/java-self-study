package com.varun.selfstudy.multithreading.pingpong;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PingPongWithQueues {

    BlockingQueue<String> serving;

    BlockingQueue<String> receiving;

    PingPongWithQueues(BlockingQueue<String> q1, BlockingQueue<String> q2) {
        this.serving = q1;
        this.receiving = q2;
    }

    private void ping() {
        while (true) {
            try {
                Thread.sleep(1000);
                String msg = receiving.take();
                System.out.println(Thread.currentThread().getName() + " " + msg);
                String newMsg = msg.equals("PING") ? "PONG" : "PING";
                serving.offer(newMsg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        final BlockingQueue<String> q1 = new ArrayBlockingQueue<String>(1);
        final BlockingQueue<String> q2 = new ArrayBlockingQueue<String>(1);
        q2.offer("PING");
        Thread t1 = new Thread() {
            public void run() {
                PingPongWithQueues p1 = new PingPongWithQueues(q1, q2);
                p1.ping();
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                PingPongWithQueues p2 = new PingPongWithQueues(q2, q1);
                p2.ping();
            }
        };

        t1.start();
        t2.start();
    }

}
