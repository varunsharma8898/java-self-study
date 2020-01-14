package com.varun.selfstudy.multithreading.printinorder;

public class PrintInOrder {

    private volatile int i;

    private static final Object lock = new Object();

    public PrintInOrder() {
        i = 1;
    }

    public void first(Runnable printFirst) throws InterruptedException {

        synchronized (lock) {
            while (i != 1) {
                lock.wait();
            }
            i = 2;
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            lock.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
            while (i != 2) {
                lock.wait();
            }
            i = 3;
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            lock.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock) {
            while (i != 3) {
                lock.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    public static void main(String[] args) {
        final PrintInOrder foo = new PrintInOrder();

        // Keeping the original as well as lambda expressions just to memorize how to do it

        Thread t1 = new Thread() {
            public void run() {
                try {
                    foo.first(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("first");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread(() -> {
            try {
                foo.second(() -> System.out.println("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                foo.third(() -> System.out.println("third"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        // start the thread in any way you want, second should always be printed after first.
        t1.start();
        t3.start();
        t2.start();
    }

//    Suppose we have a class:
//
//    public class Foo {
//        public void first() { print("first"); }
//        public void second() { print("second"); }
//        public void third() { print("third"); }
//    }
//
//    The same instance of Foo will be passed to three different threads.
//    Thread A will call first(), thread B will call second(), and thread C will call third().
//    Design a mechanism and modify the program to ensure that second() is executed after first(), and third() is executed after second().
//
//
//
//    Example 1:
//
//    Input: [1,2,3]
//    Output: "firstsecondthird"
//    Explanation: There are three threads being fired asynchronously.
//    The input [1,2,3] means thread A calls first(), thread B calls second(), and thread C calls third().
//    "firstsecondthird" is the correct output.
//
//    Example 2:
//
//    Input: [1,3,2]
//    Output: "firstsecondthird"
//    Explanation: The input [1,3,2] means thread A calls first(), thread B calls third(), and thread C calls second().
//    "firstsecondthird" is the correct output.
//
//
//    Note:
//
//    We do not know how the threads will be scheduled in the operating system,
//    even though the numbers in the input seems to imply the ordering.
//    The input format you see is mainly to ensure our tests' comprehensiveness.

}
