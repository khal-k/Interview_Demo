package com.kjq.lock;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 7:10 下午
 * @package com.kjq.lock
 */
public class SpinLockDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in ");
        while (!atomicReference.compareAndSet(null, thread)){

        }
    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnLock()");
    }
    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            //暂停一会线程
            try { TimeUnit.MILLISECONDS.sleep(1000*5); } catch (InterruptedException e) {e.printStackTrace();}
            spinLockDemo.myUnlock();
        },"AA").start();
        //保证AA线程必须被启动
        try { TimeUnit.MILLISECONDS.sleep(1000*1); } catch (InterruptedException e) {e.printStackTrace();}

        new Thread(()->{
            spinLockDemo.myLock();
            try { TimeUnit.MILLISECONDS.sleep(1000*1); } catch (InterruptedException e) {e.printStackTrace();}
            spinLockDemo.myUnlock();
        },"BB").start();
    }
}
