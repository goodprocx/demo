package com.procx.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class sparkTest1 {

    static JavaSparkContext sc = new JavaSparkContext(new SparkConf().set("spark.testing.memory", "471859200").setMaster("local").setAppName("test2"));
    public static void join(){
        final List<Tuple2<Integer, String>> names = Arrays.asList(
                new Tuple2<Integer, String>(1, "东方不败"),
                new Tuple2<Integer, String>(2, "令狐冲"),
                new Tuple2<Integer, String>(3, "林平之")
        );
        final List<Tuple2<Integer, Integer>> scores = Arrays.asList(
                new Tuple2<Integer, Integer>(1, 99),
                new Tuple2<Integer, Integer>(2, 98),
                new Tuple2<Integer, Integer>(3, 97)
        );

        final JavaPairRDD<Integer, String> nemesrdd = sc.parallelizePairs(names);
        final JavaPairRDD<Integer, Integer> scoresrdd = sc.parallelizePairs(scores);

        final JavaPairRDD<Integer, Tuple2<String, Integer>> joinRDD = nemesrdd.join(scoresrdd);
        joinRDD.foreach(tuple -> System.out.println("学号:"+tuple._1+" 姓名:"+tuple._2._1+" 成绩:"+tuple._2._2));
    }

    public static void main(String[] args) {
        ArrayList<Long> list = new ArrayList();
        for (long i = 1; i <=10000 ; i++) {
            list.add(i);
        }
        Date st = new Date();
        long count = list.stream().reduce((x,y)->x+y).get();
        System.out.println(count+"================================="+(new Date().getTime()-st.getTime()));
        Date st1 = new Date();
        long count1 = sc.parallelize(list).reduce((x,y)->x+y).longValue();
        System.out.println(count1+"================================="+(new Date().getTime()-st1.getTime()));
    }
}
