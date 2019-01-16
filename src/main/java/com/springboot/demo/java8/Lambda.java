package com.springboot.demo.java8;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lambda {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService e = Executors.newCachedThreadPool();
        Future<Boolean> f = e.submit(()->true);
        System.out.println(f.get());
        e.shutdown();
        list();
    }

    public static void list(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        languages = languages.stream().map(String::toLowerCase).filter(x->x.length()>4).collect(Collectors.toList());
        System.out.println(languages);

        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);
    }

}
