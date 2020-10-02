package com.kjq.cas;

import lombok.*;

import java.util.concurrent.atomic.AtomicReference;
@Getter
@ToString
@AllArgsConstructor
class User{ String uerName;int age;
}
/**
 * @author 孔佳齐丶
 * @create 2020-10-02 3:24 下午
 * @package com.kjq.cas
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("z3",22);
        User li4 = new User("li4",33);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        //主物理内存变量为z3
        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(z3, li4)+"\t"+atomicReference.get().toString());
    }
}
