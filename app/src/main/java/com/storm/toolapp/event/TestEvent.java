package com.storm.toolapp.event;

import java.io.Serializable;

/**
 * @创建作者 Storm
 * @创建时间 2017-12-17 10:19
 * @创建描述 ${测试bean}
 */

public class TestEvent implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
