package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntExample {


    private final static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(AtomicIntExample::incrementMany);
        Thread t2 = new Thread(AtomicIntExample::incrementMany);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Counter = " + counter);
    }

    private static void incrementMany() {
        for (int i = 0; i < 100_000; i++) {
            counter.incrementAndGet();
        }
    }
}
