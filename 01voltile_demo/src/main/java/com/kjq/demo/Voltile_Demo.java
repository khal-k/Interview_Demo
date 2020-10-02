package com.kjq.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{
    volatile int number = 0;
    //int number = 0;
    public void changeNumber(){ this.number = 3; }
    //public synchronized void addPlusPlus(){ number++; }
    public  void addPlusPlus(){ number++; }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 9:45 上午
 * @package com.kjq.demo
 *
 * 1.验证了volatile的可见性
 * 2.验证原子性指的是什么?
 *      1.不可分割,完整性,也即某个线程正在做某个具体业务,中间不可被加塞或者分割
 *      要么同时成功,要么同时失败.
 *
 *      2.volatile不保证原子性的案例演示
 *          为了解决一个number++,最好别使用synchronized
 *      3.如何解决原子性
 *          加sync
 *          直接使用juc的AtomicInteger
 *
 */
public class Voltile_Demo {
    public static void main(String[] args) {
        //seeOkVolatile();
        MyData myData = new MyData();
         for (int i = 1; i <= 20; i++) {
             new Thread(()->{
                 for (int j = 0; j < 1000; j++) {
                     myData.addPlusPlus();
                     myData.addMyAtomic();
                 }
             },String.valueOf(i)).start();
         }
         //原子性就是看最终的一致性是否得到保障
        //为什么大于2,main和gc连个线程,>2代表上面的循环开启
        while (Thread.activeCount()>2){
            //只要大于2,为了让上面的线程更好的计算
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t finally number = "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t atomicInteger number = "+myData.atomicInteger);
    }

    //volatile可以保证可见性,即使通知其他线程,主物理内存可见数据被修改
    private static void seeOkVolatile() {
        final MyData myData = new MyData();

        new Thread(()->{
            System.out.println("****"+Thread.currentThread().getName()+"\t come in");
            try { TimeUnit.MILLISECONDS.sleep(1000*3); } catch (InterruptedException e) {e.printStackTrace();}
            myData.changeNumber();
            System.out.println("A线程**********change Number to"+myData.number);
        },"A").start();

        while(myData.number==0){
            //主线程判断数据是否改变,否则一直循环
            //主线程可见该资源数据被其他线程修改...volatile
        }
        System.out.println("main线程 *********** number has changed");
    }
}
