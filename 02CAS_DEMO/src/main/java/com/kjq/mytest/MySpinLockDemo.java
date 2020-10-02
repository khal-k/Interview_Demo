package com.kjq.mytest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 8:13 下午
 * @package com.kjq.mytest
 */
public class MySpinLockDemo {
    //第一步,因为自旋锁用到的是cas机制,所以要创建一个泛型是Thread的原子引用
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //第二步,创建一个加锁方法
    public void myLock(){
        //1.获取当前调用方法代码的线程
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t 进来啦.......");
        //2.使用原子引用的compareAndSet方法,将期望值设置为null,因为是第一个线程进来,将要修改的真实值设置为该线程对戏本身
        //且用while循环做判读,如果符合是第一个进来的线程,方法返回值为true取反,跳出循环
        while(!atomicReference.compareAndSet(null, thread)) {

        }
    }
    //第三步,创建一个解锁的方法
    public void myUnLock(){
        Thread thread = Thread.currentThread();
        try { TimeUnit.MILLISECONDS.sleep(1000*1); } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("******"+Thread.currentThread().getName()+"\t 解锁啦");
        atomicReference.compareAndSet(thread,null);
    }

    public static void main(String [] args){
        MySpinLockDemo mySpinLockDemo = new MySpinLockDemo();
        new Thread(()->{
            //AA线程先进来加锁
            mySpinLockDemo.myLock();
            //占用5秒之后放锁
            try { TimeUnit.MILLISECONDS.sleep(1000*5); } catch (InterruptedException e) {e.printStackTrace();}
            mySpinLockDemo.myUnLock();
        },"AA").start();
        //保证AA第一上锁
        try { TimeUnit.MILLISECONDS.sleep(1000*1); } catch (InterruptedException e) {e.printStackTrace();}
        new Thread(()->{
            mySpinLockDemo.myLock();
            //占用1秒就行
            mySpinLockDemo.myUnLock();
        },"BB").start();
    }
}
