package com.will.conc;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(()->{
            System.out.println("result:1");
            return 1;
        });
        Thread.sleep(1000);
    }
}
