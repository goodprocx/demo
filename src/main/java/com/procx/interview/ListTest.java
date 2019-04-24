package com.procx.interview;

import java.util.Arrays;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {
        int[] test = new int[] {1,2,3};
//        Integer[] test1 = new Integer[] {1,2,3};
        List list = Arrays.asList(test); //泛型可变长度参数，认为传递的只有一个int[]参数。所以传包装类integer
        System.out.println(list.size()); //1
    }
}
