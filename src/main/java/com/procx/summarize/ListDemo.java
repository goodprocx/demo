package com.procx.summarize;

import java.util.Arrays;
import java.util.List;

/**
 * Arrays.asList方法定义是泛型可变长度参数，当传入的是基本类型的时候认为传递的只有一个数组参数。所以传包装类
 */
public class ListDemo {
    public static void main(String[] args) {
        int[] test = new int[] {1,2,3};
//        Integer[] test1 = new Integer[] {1,2,3};
        List list = Arrays.asList(test); //泛型可变长度参数，认为传递的只有一个int[]参数。所以传包装类integer
        System.out.println(list.size()); //1
    }

}
