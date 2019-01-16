package com.springboot.demo.spark;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class SparkTest {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("WordCount").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        jsc.textFile("F:\\360YunFile\\codeManager\\demo\\file\\spark.txt")
               .flatMap(line->Arrays.asList(line.split(" ")).iterator())
               .mapToPair(x->new Tuple2<>(x, 1))
               .reduceByKey((a,b)->a+b)
               .mapToPair(x->new Tuple2<>(x._2, x._1))
               .sortByKey()
                .mapToPair(x->new Tuple2<>(x._2, x._1))
               .foreach(x-> System.out.println(x));
    }

}
