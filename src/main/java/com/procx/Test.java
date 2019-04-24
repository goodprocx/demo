package com.procx;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;


public class Test {
    static class inner{
        static int one = 1;
        static final  int two = 1;
        static final  int three = new Integer(1);
    }
    public static void main(String[] args) {

        ArrayList list = new ArrayList();
        list.add("hello world");
        Vector v = new Vector();
        v.add("hello world");
        System.out.println(list.equals(v));
    }
}
