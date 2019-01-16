package com.springboot.demo.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *    第一题：统计每一个用户（手机号）所耗费的总上行流量、总下行流量，总流量
 */

public class FlowSumMR {

    public static void main(String[] args) throws Exception {


        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "FlowSumMR");
        job.setJarByClass(FlowSumMR.class);

        job.setMapperClass(FlowSumMRMapper.class);
        job.setReducerClass(FlowSumMRReducer.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.setInputPaths(job, new Path("F:\\360YunFile\\codeManager\\demo\\file\\hadoop\\input"));
//        FileOutputFormat.setOutputPath(job, new Path("F:\\360YunFile\\codeManager\\demo\\file\\hadoop\\output_sum"));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/a/hadoop/"));


        boolean isDone = job.waitForCompletion(true);
        System.exit(isDone ? 0 : 1);
    }

    public static class FlowSumMRMapper extends Mapper<LongWritable, Text, Text, Text>{

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {


            String[] split = value.toString().split(" ");

            String outkey = split[0];

            String outValue = split[1] + "\t" + split[2];

            context.write(new Text(outkey), new Text(outValue));

        }
    }

    public static class FlowSumMRReducer extends Reducer<Text, Text, Text, Text>{

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            int upFlow = 0;
            int downFlow = 0;
            int sumFlow = 0;

            for(Text t : values){
                String[] split = t.toString().split("\t");

                int upTempFlow = Integer.parseInt(split[0]);
                int downTempFlow = Integer.parseInt(split[1]);

                upFlow+=upTempFlow;
                downFlow +=  downTempFlow;
            }

            sumFlow = upFlow + downFlow;

            context.write(key, new Text(upFlow + "\t" + downFlow + "\t" + sumFlow));
        }
    }
}