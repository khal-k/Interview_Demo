package com.kjq.cas;

import com.kjq.mytest.MySpinLockDemo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyMap{
    //读写分离就是读写在不同的线程,一定要保证可见性--->volatile
    //凡缓存的东西一定要用volatile修饰...mybatis
    private volatile Map<String,String> map =new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key,String value){
       readWriteLock.writeLock().lock();
       try{
           System.out.println(Thread.currentThread().getName()+"\t 正在写入...key="+key);
           try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) {e.printStackTrace();}
           map.put(key,value);
           System.out.println(Thread.currentThread().getName()+"\t 写入完成");
       }catch(Exception e){
           e.printStackTrace();
       }finally{
           readWriteLock.writeLock().unlock();
       }
    }

    public void get(String key){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 读取数据...");
            try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) {e.printStackTrace();}
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成,,,result="+result);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 8:35 下午
 * @package com.kjq.cas
 *
 * 读是共享
 * 写是唯一
 *
 * 写操作:原子+独占,整个过程必须是一个完整的统一体,中间不许被分割
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyMap myMap = new MyMap();

         for (int i = 1; i <= 5; i++) {
             final int tempInt = i;
             new Thread(()->{
                 myMap.put(tempInt+"",tempInt+"");
             },String.valueOf(i)).start();
         }

         //try { TimeUnit.MILLISECONDS.sleep(1000*3); } catch (InterruptedException e) {e.printStackTrace();}

          for (int i = 1; i <= 5; i++) {
              final int tempInt = i;
              new Thread(()->{
                  myMap.get(tempInt+"");
              },String.valueOf(i)).start();
          }
    }
}
