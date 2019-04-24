package com.procx.spark;

import com.procx.mode.People;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SparkSqlTest {
    static SparkConf conf = new SparkConf().set("spark.testing.memory", "471859200").setAppName("HelloWorld").setMaster("local[1]").set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
    static SparkSession session = SparkSession.builder().config(conf).getOrCreate();
    public static void main(String[] args) {
        save();
    }


    public static void jdbc(){
        Properties properties = new Properties();
        properties.setProperty("user", "bigdat");
        properties.setProperty("password", "bigdat");
        session.read().jdbc("jdbc:oracle:thin:@172.30.35.100:1521:orcl", "public_area", properties)
                .createOrReplaceTempView("temp");
        session.sql("select * from temp where temp.code = 500103000000").show();

//        session.read().json("F:\\360YunFile\\codeManager\\demo\\file\\json.json").as(Encoders.bean(People.class)).write()
//                .format("jdbc").option("url", "jdbc:oracle:thin:@172.30.35.100:1521:orcl")
//                .option("dbtable", "cx_test")
//                .option("user", "bigdat")
//                .option("password", "bigdat").mode(SaveMode.Append).save() ;


    }


    public static void json(){
        session.read().json("F:\\360YunFile\\codeManager\\demo\\file\\json.json").show();
    }

    public static void save(){
        session.read().json("F:\\360YunFile\\codeManager\\demo\\file\\json.json").as(Encoders.bean(People.class))
                .write().mode(SaveMode.Overwrite).json("F:\\360YunFile\\codeManager\\demo\\file\\test.json");
    }

    public static Dataset structType(){
        JavaRDD<Row> rdd = session.read().textFile("F:\\360YunFile\\codeManager\\demo\\file\\spark.txt").javaRDD()
                .map(line->{
                    String[] arr = line.split(",");
                    Row row = RowFactory.create(arr[0],Integer.valueOf(arr[1]));
                    return row;
                });
        List<StructField> fieldList = new ArrayList<>();
        fieldList.add(DataTypes.createStructField("name", DataTypes.StringType, false));
        fieldList.add(DataTypes.createStructField("age", DataTypes.IntegerType, false));

        StructType structType = DataTypes.createStructType(fieldList);
        session.createDataFrame(rdd,structType ).show();
        return session.createDataFrame(rdd,structType );
    }

    public static void reflection(){
        JavaRDD<People> rdd =
                session.read().textFile("F:\\360YunFile\\codeManager\\demo\\file\\spark.txt").javaRDD()
                        .map(line->{
                            String[] arr = line.split(",");
                            People people = new People(arr[0],Integer.valueOf(arr[1]));
                            return people;
                        });
        rdd.foreach(x-> System.out.println(x.toString()));
        session.createDataFrame(rdd, People.class).createOrReplaceTempView("people");
        session.sql("select * from people").show();
    }
}
