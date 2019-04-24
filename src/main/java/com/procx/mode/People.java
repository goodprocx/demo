package com.procx.mode;

import lombok.Data;

import java.io.Serializable;

@Data
public class People implements Serializable {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
