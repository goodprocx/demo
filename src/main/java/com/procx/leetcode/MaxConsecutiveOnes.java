package com.procx.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 1004
 * Given an array A of 0s and 1s, we may change up to K values from 0 to 1.
 *
 * Return the length of the longest (contiguous) subarray that contains only 1s.
 *
 *
 * Example 1:
 *
 * Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
 * Output: 6
 * Explanation:
 * [1,1,1,0,0,1,1,1,1,1,1]
 * Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
 *
 */
public class MaxConsecutiveOnes {

    //遍历统计出１和０的个数为ｌｉｓｔ,遍历1，直到大于等于Ｋ,得到最大连续１的数
    public int longestOnes(int[] A, int K) {

        if(A == null ||A.length == 0){
            return 0;
        }
        int start=A[0];
        int count=0;
        int temp = start;
        List<Integer> tj =  new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            if(A[i]==temp){
                count++;
            }else{
                tj.add(count);
                temp = A[i];
                count=1;
            }
            if(i == A.length-1) tj.add(count);
        }

        //遍历1
        int maxCount = 0;
        for (int i = start==1?0:1; i <tj.size()  ; i+=2) {
            int total = 0;
            int count0 = 0;
            boolean flag = false;
            for (int j = i; j < tj.size() ; j++) {
                if(flag){
                    if(count0+tj.get(j)>K){
                        total = total+Math.min(K-count0,(tj.get(j)+(i>0?tj.get(i-1):0)));
                        break;
                    }else{
                        count0+=tj.get(j);
                    }
                }
                total = tj.get(j)+total;
                if(j == tj.size()-1 && i>0){
                    total= total+Math.min(K-count0,tj.get(i-1));
                }
                flag = !flag;
            }
            if(total>maxCount){
                maxCount = total;
            }
        }
        return maxCount;
    }

    public static void main(String[] args) {
        System.out.println(new MaxConsecutiveOnes().longestOnes(new int[] {0,0,0,1},5));
    }
}
