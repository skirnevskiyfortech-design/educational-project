package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

public class SynchronizersExample {

        /*
    самые частоиспользуемые семафоры это
    Semaphore - он ограничивает количесво потоков в определенном участке кода
    CountDownLatch - Один поток ждёт, пока другие выполнят нужное количество действий
    CyclicBarrier - используется, когда несколько потоков должны встретиться в одной точке и продолжить выполнение вместе.
    Exchanger - позволяет двум потокам обменяться данными
     */


    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== SEMAPHORE DEMO ===");
        semaphoreDemo();

        Thread.sleep(3000);

        System.out.println("\n=== COUNTDOWNLATCH DEMO ===");
        countDownLatchDemo();

        Thread.sleep(3000);

        System.out.println("\n=== CYCLICBARRIER DEMO ===");
        cyclicBarrierDemo();

        Thread.sleep(3000);

        System.out.println("\n=== EXCHANGER DEMO ===");
        exchangerDemo();

        Thread.sleep(3000);

        System.out.println("\n=== PHASER DEMO ===");
        phaserDemo();
    }

    /**
     * Semaphore ограничивает количество потоков,
     * которые одновременно могут выполнять участок кода.
     */
    private static void semaphoreDemo() {
        Semaphore semaphore = new Semaphore(2);

        for (int i = 1; i <= 5; i++) {
            int taskId = i;

            new Thread(() -> {
                try {
                    System.out.println("Task " + taskId + " waiting for permit");

                    semaphore.acquire();

                    System.out.println("Task " + taskId + " started by "
                            + Thread.currentThread().getName());

                    sleep(1000);

                    System.out.println("Task " + taskId + " finished");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release();
                }
            }, "semaphore-thread-" + i).start();
        }
    }

    /**
     * CountDownLatch позволяет одному потоку ждать,
     * пока несколько других потоков завершат работу.
     */
    private static void countDownLatchDemo() throws InterruptedException {
        int workersCount = 3;
        CountDownLatch latch = new CountDownLatch(workersCount);

        for (int i = 1; i <= workersCount; i++) {
            int workerId = i;

            new Thread(() -> {
                try {
                    System.out.println("Worker " + workerId + " started initialization");

                    sleep(1000 + workerId * 500L);

                    System.out.println("Worker " + workerId + " finished initialization");
                } finally {
                    latch.countDown();
                    System.out.println("Worker " + workerId + " called countDown()");
                }
            }, "latch-worker-" + i).start();
        }

        System.out.println("Main thread is waiting for all workers...");
        latch.await();

        System.out.println("All workers finished. Main thread continues.");
    }

    /**
     * CyclicBarrier заставляет группу потоков встретиться в одной точке.
     * Пока все потоки не вызовут await(), никто дальше не пойдёт.
     */
    private static void cyclicBarrierDemo() {
        int parties = 3;

        CyclicBarrier barrier = new CyclicBarrier(parties, () ->
                System.out.println("All workers reached barrier. Barrier action executed.")
        );

        for (int i = 1; i <= parties; i++) {
            int workerId = i;

            new Thread(() -> {
                try {
                    System.out.println("Worker " + workerId + " doing phase 1");

                    sleep(1000 + workerId * 500L);

                    System.out.println("Worker " + workerId + " waiting on barrier");

                    barrier.await();

                    System.out.println("Worker " + workerId + " doing phase 2");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }, "barrier-worker-" + i).start();
        }
    }

    /**
     * Exchanger позволяет двум потокам обменяться объектами.
     */
    private static void exchangerDemo() {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread first = new Thread(() -> {
            try {
                String myData = "data from FIRST thread";

                System.out.println("First thread sends: " + myData);

                String received = exchanger.exchange(myData);

                System.out.println("First thread received: " + received);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "exchanger-first");

        Thread second = new Thread(() -> {
            try {
                String myData = "data from SECOND thread";

                System.out.println("Second thread sends: " + myData);

                String received = exchanger.exchange(myData);

                System.out.println("Second thread received: " + received);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "exchanger-second");

        first.start();
        second.start();
    }

    /**
     * Phaser похож на CyclicBarrier, но более гибкий.
     * Он позволяет синхронизировать потоки по фазам.
     */
    private static void phaserDemo() {
        Phaser phaser = new Phaser(1); // регистрируем main thread

        for (int i = 1; i <= 3; i++) {
            phaser.register();

            int workerId = i;

            new Thread(() -> {
                System.out.println("Worker " + workerId + " registered");

                System.out.println("Worker " + workerId + " phase 1 started");
                sleep(1000 + workerId * 300L);
                System.out.println("Worker " + workerId + " phase 1 finished");

                phaser.arriveAndAwaitAdvance();

                System.out.println("Worker " + workerId + " phase 2 started");
                sleep(1000 + workerId * 300L);
                System.out.println("Worker " + workerId + " phase 2 finished");

                phaser.arriveAndDeregister();

                System.out.println("Worker " + workerId + " deregistered");

            }, "phaser-worker-" + i).start();
        }

        System.out.println("Main waits for phase 1");
        phaser.arriveAndAwaitAdvance();

        System.out.println("Main detected: phase 1 completed by all workers");

        System.out.println("Main waits for phase 2");
        phaser.arriveAndAwaitAdvance();

        System.out.println("Main detected: phase 2 completed by all workers");

        phaser.arriveAndDeregister();

        System.out.println("Phaser demo finished");
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}