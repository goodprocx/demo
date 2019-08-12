package com.procx.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class StreamDemo {
    private  static List<String> words =Arrays.asList("wmyskxz", "say", "wow", "to", "everybody");

    public static void main(String[] args) {
        test();
    }
    public void filter(){
        words.stream()
                .filter(word -> word.startsWith("w"))
                .distinct()
                .skip(1)
                .limit(1)
                .forEach(System.out::println);
    }

    public static void map(){
        words.stream()
                .map(String::length).forEach(System.out::println);
        words.stream()
                .map(s->s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * allMatch、anyMatch、noneMatch、findFirst 和 findAny
     */
    public static void  match(){
        System.out.println(words.stream().anyMatch(x->x.length()>=9));
    }

    public static void test(){
        IntStream.range(1, 10)
                .peek(x -> System.out.print("\nA" + x))
                .limit(3)
                .peek(x -> System.out.print("B" + x))
                .forEach(x -> System.out.print("C" + x));
// ==============输出：===============
// A1B1C1
// A2B2C2
// A3B3C3

        IntStream.range(1, 10)
                .peek(x -> System.out.print("\nA" + x))
                .skip(6)
                .peek(x -> System.out.print("B" + x))
                .forEach(x -> System.out.print("C" + x));
// ==============输出：===============
// A1
// A2
// A3
// A4
// A5
// A6
// A7B7C7
// A8B8C8
// A9B9C9
    }
}
