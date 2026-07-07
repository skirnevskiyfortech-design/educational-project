package org.example;

public class TreadIncrementCounter {

    static int counter = 0;

    static int anotherCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread tr1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter();
            }
        });

        Thread tr2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter();
            }
        });

        tr1.start();
        tr2.start();

        tr1.join();
        tr2.join();

        System.out.println("Counter: " + counter);
        System.out.println("Counter: " + anotherCounter);
    }

    /*
    Если static то при использовании synchronized будет захватываться монитор самого класса
    Если без static то монитор объекта через (this), т е конструктор
     */
    private static synchronized void incrementCounter() {
        counter++;
    }
}
