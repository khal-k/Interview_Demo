package com.kjq.cas;

import java.util.concurrent.CountDownLatch;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 8:59 下午
 * @package com.kjq.cas
 *
 * 模拟一个CountDownLatch的案例
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            final int tempInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 国被灭....");
                countDownLatch.countDown();
            },CountEnum.forEach_CountEmu(i).getReMessage()).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ******秦,统一");

        System.out.println(Thread.currentThread().getName());
        System.out.println();
        System.out.println(CountEnum.ONE);
        System.out.println(CountEnum.ONE.getRetCode());
        System.out.println(CountEnum.ONE.getReMessage());

    }

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(7);
        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 第"+tempInt+"个同学离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ******班长离开班级,关门..");
    }

}
