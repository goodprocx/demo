package com.procx.summarize;

public class IntegerDemo {

    /**
     * int会自动动装箱,而valueOf方法会先从缓冲中取值，缓存数据为-128到127，超过部分范围就会new一个对象出来，Long也一样
     * 所以建议Integer比较大小的时候用equal方法
     */

    public static void main(String[] args) {
        Integer a = 1; //自动装箱，编译之后会等价于 Integer.valueOf(1);
        Integer b = 1;
        Long a1 = 128l;
        Long b1 = 128l;
        int a2 =111111;
        int b2 =111111;
        System.out.println(a==b);
        System.out.println(a1==b1);
        System.out.println(a2==b2);
    }
}
