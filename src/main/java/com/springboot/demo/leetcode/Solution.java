package com.springboot.demo.leetcode;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    public int[][] kClosest(int[][] points, int K) {
        int[][] rs = new int[K][2];
        List<Temp> list = Arrays.asList(points).stream().map(x->{
            int sum = x[0]*x[0]+x[1]*x[1];
            return new Temp(sum, x);
        }).sorted(Comparator.comparingInt(x -> x.sum)).limit(K).collect(Collectors.toList());
        for (int i = 0; i < list.size() ; i++) {
            rs[i] = list.get(i).value;
        }
        return rs;
    }

    public class Temp implements Comparable<Temp>{
        int sum;
        int[] value;

        public Temp(int sum, int[] value) {
            this.sum = sum;
            this.value = value;
        }

        @Override
        public int compareTo(Temp o) {
            return this.sum-o.sum;
        }
    }

    public static void main(String[] args) {
      new Solution().kClosest(new int[][]{{1,3},{-2,2}},1);
    }
}
