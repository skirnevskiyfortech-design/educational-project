package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final Semaphore semaphore1 = new Semaphore(0);
    private static final Semaphore semaphore2 = new Semaphore(0);
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        int[] array2 = {2, 3, 1};


        for (int i : array2) {

            executorService.submit(() -> {
                try {
                    switch (i) {
                        case 1:
                            first(() -> System.out.print("first"));
                            break;
                        case 2:
                            second(() -> System.out.print("second"));
                            break;
                        case 3:
                            third(() -> System.out.print("third"));
                            break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        System.out.println("SemaphoreExample");
        executorService.shutdown();
    }


    public static void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        semaphore1.release();
    }

    public static void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        semaphore1.acquire();
        printSecond.run();
        semaphore2.release();
    }

    public static void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        semaphore2.acquire();
        printThird.run();
    }
}
