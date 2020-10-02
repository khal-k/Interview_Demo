package com.kjq.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 6:04 下午
 * @package com.kjq.lock
 */
public class T1 {
    volatile int n = 0;

    public void add(){
        n++;
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);
    }
}
