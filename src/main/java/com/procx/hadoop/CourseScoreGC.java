package com.procx.hadoop;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 *  分组规则的指定
 */
public class CourseScoreGC extends WritableComparator{

    public CourseScoreGC(){
        super(CourseScore.class, true);
    }

    /**
     *
     *   方法的定义解释：
     *
     *   方法的意义：一般来说，都可以从方法名找到一些提示
     *   方法的参数：将来你的MR程序中，要作为key的两个对象，是否是相同的对象
     *   方法的返回值： 返回值类型为int  当返回值为0的时候。证明， 两个参数对象，经过比较之后，是同一个对象
     *
     *   在我们的需求中： 分组规则是  Course
     *
     */
    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        CourseScore cs1 = (CourseScore)a;
        CourseScore cs2 = (CourseScore)b;

        int compareTo = cs1.getCourse().compareTo(cs2.getCourse());

        return compareTo;
    }
}
