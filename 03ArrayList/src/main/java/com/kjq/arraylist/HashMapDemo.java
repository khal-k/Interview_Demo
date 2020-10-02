package com.kjq.arraylist;

import javax.swing.*;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyMapDemo{
    private volatile  Map<String,String> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //数据插入方法
    public void put(String key,String value){
        readWriteLock.writeLock().lock();
       try{
           System.out.println(Thread.currentThread().getName()+"\t -----写入数据"+key);
           //暂停线程200毫秒
           try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) {e.printStackTrace();}
           map.put(key,value);
           System.out.println(Thread.currentThread().getName()+"\t -----写入成功");
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           readWriteLock.writeLock().unlock();
       }
    }

    //数据读取方法
    public void get(String key){
       readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 读取数据");
            //暂停线程200毫秒
            try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) {e.printStackTrace();}
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取数据成功,result="+result);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 4:54 下午
 * @package com.kjq.arraylist
 */
public class HashMapDemo {
    public static void main(String[] args) {
        MyMapDemo myMapDemo = new MyMapDemo();

         for (int i = 1; i <= 5; i++) {
             final int tempInt = i;
             new Thread(()->{
                 myMapDemo.put(tempInt+"",tempInt+"");
             },String.valueOf(i)).start();
         }

        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myMapDemo.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}
