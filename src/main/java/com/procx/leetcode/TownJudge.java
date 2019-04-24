package com.procx.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * In a town, there are N people labelled from 1 to N.  There is a rumor that one of these people is secretly the town judge.
 *
 * If the town judge exists, then:
 *
 * The town judge trusts nobody.
 * Everybody (except for the town judge) trusts the town judge.
 * There is exactly one person that satisfies properties 1 and 2.
 * You are given trust, an array of pairs trust[i] = [a, b] representing that the person labelled a trusts the person labelled b.
 *
 * If the town judge exists and can be identified, return the label of the town judge.  Otherwise, return -1.
 */
public class TownJudge {

    public int findJudge(int N, int[][] trust) {
        if(N==1){
            return 1;
        }
        int result = -1;
        Map<Integer,List<Integer>> map = Arrays.stream(trust).map(x->x[1]).collect(Collectors.groupingBy(x->x));
        List<Integer> judgeList = map.entrySet().stream().filter(x->x.getValue().size()>=N-1).map(x->x.getKey()).collect(Collectors.toList());
        List peopleList = Arrays.stream(trust).map(x->x[0]).collect(Collectors.toList());
        List<Integer> newJudgeList = judgeList.stream().filter(x->!peopleList.contains(x)).collect(Collectors.toList());
        if(newJudgeList !=null && newJudgeList.size()>0){
            result = newJudgeList.get(0);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new TownJudge().findJudge(1,new int[][]{}));
    }
}
