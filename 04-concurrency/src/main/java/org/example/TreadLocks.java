package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TreadLocks {

    private static final Object lockSy = new Object();
    private static final Lock lockRe = new ReentrantLock();

    static int counter1 = 0;
    static int counter2 = 0;

    public static void main(String[] args) throws InterruptedException {

        /*
        Synchronized Lock
         */
        Thread tr1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter1();
            }
        });

        Thread tr2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter1();
            }
        });

        tr1.start();
        tr2.start();

        tr1.join();
        tr2.join();

        System.out.println("Counter (Synchronized Lock): " + counter1);


        /*
        ReentrantLock
         */

        Thread tr3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter2();
            }
        });

        Thread tr4 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter2();
            }
        });

        tr3.start();
        tr4.start();

        tr3.join();
        tr4.join();

        System.out.println("Counter (ReentrantLock): " + counter2);
    }

    private static void incrementCounter1() {
        synchronized (lockSy) {
            counter1++;
        }
    }

    private static void incrementCounter2() {
        lockRe.lock();
        try {
            counter2++;
        } finally {
            lockRe.unlock();
        }
    }
}
