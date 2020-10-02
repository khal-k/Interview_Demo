package com.kjq.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 3:43 下午
 * @package com.kjq.cas
 */
public class ABADemo {  //ABA问题解决  AtomicStampedReference
    //原子引用
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //原子时间戳引用
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("==========ABA问题的产生=============");
        new Thread(()->{
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        },"T1").start();

        new Thread(()->{
            //暂停1秒,保证t1的线程完成ABA操作
            try { TimeUnit.MILLISECONDS.sleep(1000*1); } catch (InterruptedException e) {e.printStackTrace();}
            System.out.println(atomicReference.compareAndSet(100, 2019)+"\t"+atomicReference.get());
        },"T2").start();

        System.out.println("******* 等待2秒....");
        try { TimeUnit.MILLISECONDS.sleep(1000*2); } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("==========ABA问题的解决=============");

        new Thread(()->{
            int stamped = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t 第1次版本号="+stamped);
            //暂停1秒 t3
            try { TimeUnit.MILLISECONDS.sleep(1000*1); } catch (InterruptedException e) {e.printStackTrace();}
            atomicStampedReference.compareAndSet(100, 101,
                    stamped, stamped+1);
            System.out.println(Thread.currentThread().getName()+"\t 第2次版本号="+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t 第3次版本号="+atomicStampedReference.getStamp());
        },"T3").start();

        new Thread(()->{
            int stamped = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t 第1次版本号="+stamped);
            //暂停3秒 t4,保证t3线程已完成ABA操作
            try { TimeUnit.MILLISECONDS.sleep(1000*3); } catch (InterruptedException e) {e.printStackTrace();}
            boolean result = atomicStampedReference.compareAndSet(100, 2020,
                    stamped, stamped+1);
            System.out.println(Thread.currentThread().getName()+"\t 是否能够修改成功------>"+result
            +"\t 当前最新版本号:"+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t 当前实际最新值:"+
                    atomicStampedReference.getReference());
        },"T4").start();
    }
}
