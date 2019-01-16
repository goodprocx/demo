package com.springboot.demo.hadoop;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class CSPartitioner extends Partitioner<CourseScore,NullWritable>{

    /**
     *
     */
    @Override
    public int getPartition(CourseScore key, NullWritable value, int numPartitions) {

        String course = key.getCourse();
        if(course.equals("math")){
            return 0;
        }else if(course.equals("english")){
            return 1;
        }else if(course.equals("computer")){
            return 2;
        }else{
            return 3;
        }


    }

}