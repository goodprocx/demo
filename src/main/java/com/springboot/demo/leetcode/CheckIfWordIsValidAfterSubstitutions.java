package com.springboot.demo.leetcode;

/**
 * We are given that the string "abc" is valid.
 *
 * From any valid string V, we may split V into two pieces X and Y such that X + Y (X concatenated with Y) is equal to V.  (X or Y may be empty.)  Then, X + "abc" + Y is also valid.
 *
 * If for example S = "abc", then examples of valid strings are: "abc", "aabcbc", "abcabc", "abcabcababcc".  Examples of invalid strings are: "abccba", "ab", "cababc", "bac".
 *
 * Return true if and only if the given string S is valid.
 */
public class CheckIfWordIsValidAfterSubstitutions {

    //思路：判断字符串有效->找到abc,去掉，判断剩余的是否是有效的，递归
    public boolean isValid(String S) {
        boolean flag = false;
        flag = repalce(S, S.length());
        return flag;
    }

    public boolean repalce(String s,int length){
        s = s.replace("abc", "");
        if(s.length()==0){
            return true;
        }
        if(s.length() ==length){
            return false;
        }
        return repalce(s, s.length());
    }

    public static void main(String[] args) {
        System.out.println(new CheckIfWordIsValidAfterSubstitutions().isValid("abccba"));
    }
}
