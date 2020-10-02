package com.kjq.arraylist;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 4:18 下午
 * @package com.kjq.arraylist
 *
 * 集合不安全问题
 *  ArrayList集合
 *
 *  java.util.ConcurrentModificationException  读写并发异常
 *  1.故障现象
 *
 *  2.导致原因
 *      线程不安全
 *  3.解决方案
 *      1.vector
 *      2.Collections.synchronizedList(new ArrayList<>())
 *      3.CopyOnWrite容器   写时复制 CopyOnWriteArrayList<>()
 *  4.优化建议
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        /*listNoteSafe();*/
        Map<String ,String> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,6));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }

    private static void listNoteSafe() {
        //List<String> list = new ArrayList<>();
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
