package com.springboot.demo.hadoop;

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


public class CourseScoreMR3{

    private static final int TOPN = 3;

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Job job = Job.getInstance(conf);


        job.setJarByClass(CourseScoreMR3.class);
        job.setMapperClass(CourseScoreMR2Mapper.class);
        job.setReducerClass(CourseScoreMR2Reducer.class);

        job.setMapOutputKeyClass(CourseScore.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(CourseScore.class);
        job.setOutputValueClass(NullWritable.class);


//        job.setPartitionerClass(CSPartitioner.class);
//        job.setNumReduceTasks(4);


        // 指定分组规则
        job.setGroupingComparatorClass(CourseScoreGC.class);


        Path inputPath = new Path("E:\\bigdata\\cs\\input");
        Path outputPath = new Path("E:\\bigdata\\cs\\output_3_last");
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

        int count = 0;

        /**
         * reducer阶段的reduce方法的调用参数：key相同的额一组key-vlaue
         *
         * redcuer阶段，每次遇到一个不同的key的key_value组， 那么reduce方法就会被调用一次。
         *
         *
         * values这个迭代器只能迭代一次。
         * values迭代器在迭代的过程中迭代出来的value会变，同时，这个value所对应的key也会跟着变         合理
         *
         */
        @Override
        protected void reduce(CourseScore key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {


            int count = 0;

            for(NullWritable nvl : values){
                System.out.println("*********************************** " + (++count) + "      " + key.toString());

                if(count == 3){
                    return;
                }
            }


            // 原样输出
            /*for(NullWritable nvl :  values){
                context.write(key, nvl);
            }*/


            // 输出每门课程的最高分数   ,  预期结果中，key的显示都是一样的
//            for(NullWritable nvl : values){
//                System.out.println(key + " - " nvl);
//
//                valueList.add(nvl);
//            }

//            List<Value> valueList = null;
            // 预期结果中，key的显示都是一样的
            /*int count = 0;
            for(NullWritable nvl : values){
                count++;
            }
            for(int i = 0; i<count; i++){
            valueList.get(i) = value
                System.out.println(key + " - "+ value);
            }*/


//            math hello 1
//            math hi  2
        }
    }
}