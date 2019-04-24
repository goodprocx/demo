package com.procx.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CourseScoreMR2{

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);
        Job job = Job.getInstance(conf);


        job.setJarByClass(CourseScoreMR2.class);
        job.setMapperClass(CourseScoreMR2Mapper.class);
//        job.setReducerClass(CourseScoreMR2Reducer.class);

        job.setMapOutputKeyClass(CourseScore.class);
        job.setMapOutputValueClass(NullWritable.class);
//        job.setOutputKeyClass(CourseScore.class);
//        job.setOutputValueClass(NullWritable.class);


        job.setPartitionerClass(CSPartitioner.class);
        job.setNumReduceTasks(4);


        Path inputPath = new Path("E:\\bigdata\\cs\\input");
        Path outputPath = new Path("E:\\bigdata\\cs\\output_2");
        FileInputFormat.setInputPaths(job, inputPath);
        if(fs.exists(outputPath)){
            fs.delete(outputPath, true);
        }
        FileOutputFormat.setOutputPath(job, outputPath);


        boolean isDone = job.waitForCompletion(true);
        System.exit(isDone ? 0 : 1);
    }

    public static class CourseScoreMR2Mapper extends Mapper<LongWritable, Text, CourseScore, NullWritable>{

        CourseScore cs = new CourseScore();

        /**
         * value =  math,huangxiaoming,85,75,85,99,66,88,75,91
         */
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] split = value.toString().split(",");

            String course = split[0];
            String name = split[1];

            int sum = 0;
            int count = 0;

            for(int i = 2; i<split.length; i++){
                int tempScore = Integer.parseInt(split[i]);
                sum += tempScore;

                count++;
            }

            double avgScore = 1D * sum / count;

            cs.setCourse(course);
            cs.setName(name);
            cs.setScore(avgScore);

            context.write(cs, NullWritable.get());
        }

    }

    public static class CourseScoreMR2Reducer extends Reducer<CourseScore, NullWritable, CourseScore, NullWritable>{

        @Override
        protected void reduce(CourseScore key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {


        }
    }
}
