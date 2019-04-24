package com.procx.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * https://www.cnblogs.com/qingyunzong/p/8584933.html
 */
public class CourseScoreMR1 {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Job job = Job.getInstance(conf);


        job.setJarByClass(CourseScoreMR1.class);
        job.setMapperClass(CourseScoreMR1Mapper.class);
        job.setReducerClass(CourseScoreMR1Reducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        Path inputPath = new Path("E:\\bigdata\\cs\\input");
        Path outputPath = new Path("E:\\bigdata\\cs\\output_1");
        FileInputFormat.setInputPaths(job, inputPath);
        if(fs.exists(outputPath)){
            fs.delete(outputPath, true);
        }
        FileOutputFormat.setOutputPath(job, outputPath);


        boolean isDone = job.waitForCompletion(true);
        System.exit(isDone ? 0 : 1);
    }

    public static class CourseScoreMR1Mapper extends Mapper<LongWritable, Text, Text, DoubleWritable>{

        /**
         *  数据的三个字段： course , name, score
         *
         *  value == algorithm,huangzitao,85,86,41,75,93,42,85,75
         *
         *  输出的key和value：
         *
         *  key ： course
         *
         *  value : avgScore
         *
         *  格式化数值相关的操作的API ： NumberFormat
         *                      SimpleDateFormat
         */

        Text outKey = new Text();
        DoubleWritable outValue = new DoubleWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] split = value.toString().split(",");

            String course = split[0];

            int sum = 0;
            int count = 0;

            for(int i = 2; i<split.length; i++){
                int tempScore = Integer.parseInt(split[i]);
                sum += tempScore;

                count++;
            }

            double avgScore = 1D * sum / count;


            outKey.set(course);
            outValue.set(avgScore);

            context.write(outKey, outValue);
        }

    }

    public static class CourseScoreMR1Reducer extends Reducer<Text, DoubleWritable, Text, Text>{


        Text outValue = new Text();
        /**
         * key :  course
         *
         * values : 98.7   87.6
         */
        @Override
        protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {

            double sum = 0;
            int count = 0;

            for(DoubleWritable dw : values){
                sum += dw.get();
                count ++;
            }

            double lastAvgScore = sum / count;

            outValue.set(count+"\t" + lastAvgScore);

            context.write(key, outValue);
        }
    }
}
