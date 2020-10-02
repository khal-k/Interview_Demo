package com.kjq.demo;

import javax.swing.*;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 12:05 下午
 * @package com.kjq.demo
 *
 * 多线程会有很多不确定性,要把这些不确定的变为确定的
 */
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;
    private SingletonDemo(){
        //构造方法只执行一次,保证提供的对象是同一个
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletonDemo()");
    }

    //DCL (Double Check Lock 双端检索机制)
    public static SingletonDemo getInstance(){
        if(instance==null){
            synchronized (SingletonDemo.class){
                if(instance==null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) {
        //单线程操作
        //System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
        //System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
        //System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());

        //并发多线程后,情况发生了很大的变化
         for (int i = 1; i <= 50; i++) {
             new Thread(()->{
                 SingletonDemo.getInstance();
             },String.valueOf(i)).start();
         }
    }
}
