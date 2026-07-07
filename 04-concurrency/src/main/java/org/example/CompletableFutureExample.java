package org.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureExample {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<String> userFuture =
                CompletableFuture.supplyAsync(() -> loadUser(), executor);

        CompletableFuture<String> accountsFuture =
                CompletableFuture.supplyAsync(() -> loadAccounts(), executor);

        CompletableFuture<String> documentsFuture =
                CompletableFuture.supplyAsync(() -> loadDocuments(), executor);

        CompletableFuture.allOf(userFuture, accountsFuture, documentsFuture).join();

        String user = userFuture.join();
        String accounts = accountsFuture.join();
        String documents = documentsFuture.join();

        System.out.println(user + " " + accounts + " " + documents);

        System.out.println("Time = " + (System.currentTimeMillis() - start));

        executor.shutdown();
    }

    private static void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static String loadUser() {
        safeSleep(1000);
        return "user";
    }

    private static String loadAccounts() {
        safeSleep(1000);
        return "accounts";
    }

    private static String loadDocuments() {
        safeSleep(1000);
        return "documents";
    }

}
