package com.procx.threads;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {
    private static volatile int count =0 ;
    private static ExecutorService executorService = Executors.newFixedThreadPool(100);
    private static AtomicInteger atoCount = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i =0;i<1000000;i++){
            executorService.execute(()->{
                count++;  //volatile非原子性
                atoCount.incrementAndGet();
            });
        }
        executorService.shutdown();
        while(!executorService.isTerminated()){
            Thread.yield();
        }
        System.out.println(count);
        System.out.println(atoCount.get());
    }
}
