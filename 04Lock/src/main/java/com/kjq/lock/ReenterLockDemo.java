package com.kjq.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t invoked sendSMS()");
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getId()+"\t #####invoked sendEmail()");
    }

    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t *****invoked set()");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 6:25 下午
 * @package com.kjq.lock
 *
 * 可重入锁(也叫递归锁)
 *
 * 指的是同一线程外层函数获得锁之后,内层递归函数仍然能获取该锁的diamante,
 * 在同一线程在外层获取锁的时候,在进入内层方法会自动获取锁
 *
 * 也即是说,线程可以进入任何一个它已经拥有的锁所同步着的代码块
 *
 * 10	 invoked sendSMS()          10线程在外层方法获取锁的时候
 * 10	 #####invoked sendEmail()   10在进入内层方法会自动获取锁
 * 11	 invoked sendSMS()
 * 11	 #####invoked sendEmail()
 */
public class ReenterLockDemo {
    public static void main(String[] args){
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T2").start();
        try { TimeUnit.MILLISECONDS.sleep(1000*3); } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("==========");
        System.out.println("==========");
        System.out.println("==========");


        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();
    }
}
