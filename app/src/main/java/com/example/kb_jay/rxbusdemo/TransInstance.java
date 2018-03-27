package com.example.kb_jay.rxbusdemo;


 /**
  * *
  * 🚗上运送的实体类
  * @author kb_jay
  * created at 2018/3/27 下午3:27
  */
public class TransInstance {
    public String name;
    public int age;

    public TransInstance(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        return "TransInstance{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
