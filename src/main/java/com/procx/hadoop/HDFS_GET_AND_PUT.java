package com.procx.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFS_GET_AND_PUT {

    public static void main(String[] args) throws Exception {


        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        conf.set("dfs.replication", "2");
        FileSystem fs = FileSystem.get(conf);


        /**
         * 更改操作用户有两种方式:
         *
         * 1、直接设置运行换种的用户名为hadoop
         *
         *     VM arguments ;   -DHADOOP_USER_NAME=hadoop
         *
         * 2、在代码中进行声明
         *
         *  System.setProperty("HADOOP_USER_NAME", "hadoop");
         */
        System.setProperty("HADOOP_USER_NAME", "hadoop");

        // 上传
        fs.copyFromLocalFile(new Path("F:\\360YunFile\\codeManager\\demo\\file\\test.txt"), new Path("/a/test.txt"));



        /**
         * .crc  ： 校验文件
         *
         * 每个块的元数据信息都只会记录合法数据的起始偏移量：  qqq.txt  blk_41838 :  0 - 1100byte
         *
         * 如果进行非法的数据追加。最终是能够下载合法数据。
         * 由于你在数据的中间， 也就是说在 0 -1100 之间的范围进行了数据信息的更改。 造成了采用CRC算法计算出来校验值，和最初存入进HDFS的校验值
         * 不一致。HDFS就认为当前这个文件被损坏了。
         */


        // 下载
        fs.copyToLocalFile(new Path("/a/ggg.txt"), new Path("F:\\360YunFile\\codeManager\\demo\\file\\hadoop.txt"));


        /**
         * 上传和下载的API的底层封装其实就是 ： FileUtil.copy(....)
         */

        fs.close();
    }
}