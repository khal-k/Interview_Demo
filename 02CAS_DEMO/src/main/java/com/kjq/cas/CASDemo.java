package com.kjq.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 12:57 下午
 * @package com.kjq.cas
 *
 * 1    CAS是什么? ==>compareAndSet
 *      比较并交换
 *
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2020)
            +"\t current data :"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024)
                +"\t current data :"+atomicInteger.get());
    }
}
