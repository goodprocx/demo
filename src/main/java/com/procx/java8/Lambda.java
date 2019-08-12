package com.procx.java8;

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
//        ExecutorService e = Executors.newCachedThreadPool();
//        Future<Boolean> f = e.submit(()->true);
//        System.out.println(f.get());
//        e.shutdown();
//        list();
        arr();
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

    public static void arr(){
        List<Integer> list = new ArrayList();
        list.add(111);
        list.add(111);
        list.add(222);
        list.add(444);
        list.add(111);
        Map<String, List<Integer>> collect3 = list.stream()
                .collect(Collectors.groupingBy((x) -> "group"));//将返回值相同的进行分组
        System.out.println(collect3);

        Map<Integer, List<Integer>> collect31 = list.stream()
                .collect(Collectors.groupingBy(x->x));//将返回值相同的进行分组
        System.out.println(collect31);

        //多级分组 {group={777=[777], 666=[666], 555=[555, 555], 444=[444]}}
        Map<String, Map<Integer, List<Integer>>> collect4 = list.stream()
                .collect(Collectors.groupingBy((x) -> "group", Collectors.groupingBy((x) -> x)));
        System.out.println(collect4);

        //分区 {false=[444], true=[555, 666, 777, 555]}
        Map<Boolean, List<Integer>> collect5 = list.stream()
                .collect(Collectors.partitioningBy((x) -> x ==111));
        System.out.println(collect5);

        //汇总
        DoubleSummaryStatistics collect6 = list.stream()
                .collect(Collectors.summarizingDouble((x) -> x));
        System.out.println(collect6.getMax());
        System.out.println(collect6.getCount());
        //拼接 444,555,666,777,555
        String collect7 = list.stream()
                .map(s -> s.toString())
                .collect(Collectors.joining(","));
        System.out.println(collect7);

        //最大值
        Optional<Integer> integer = list.stream()
                .collect(Collectors.maxBy(Integer::compare));
        System.out.println(integer.get());


        Integer reduce = list.stream()
                .map(s -> (s + 1))
                .reduce(0, (x, y) -> x + y);    //归约：0为第一个参数x的默认值，x是计算后的返回值，y为每一项的值。
        System.out.println(reduce);

        Optional<Integer> reduce1 = list.stream()
                .map(s -> (s + 1))
                .reduce((x, y) -> x + y);  // x是计算后的返回值，默认为第一项的值，y为其后每一项的值。
        System.out.println(reduce1);
    }

}
