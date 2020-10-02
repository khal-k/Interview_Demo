package com.kjq.cas;

import lombok.Getter;

/**
 * @author 孔佳齐丶
 * @create 2020-10-02 9:09 下午
 * @package com.kjq.cas
 *
 * 枚举不是kv键值对,是一个数据版的mysql数据库
 *
 *  mysql dbName = countEnum
 *  table
 *  one
 *  ID  userName  age  brith userEmail
 *  1     齐       222
 *
 *       one(1,"齐",v2,v3,v4)
 *
 * 枚举不可能终端,敏感数据,可以放入枚举,
 *
 * 1,对应的值 一次修改此次生效,,,代码耦合度低
 */
public enum CountEnum {
    ONE(1,"齐"), TWO(2,"楚"), THREE(3,"燕"),
    FOUR(4,"魏"), FIVE(5,"韩"), SIX(6,"赵");

    @Getter private Integer retCode;
    @Getter private String reMessage;
    CountEnum(Integer retCode, String reMessage) {
        this.retCode = retCode;
        this.reMessage = reMessage;
    }
    public static CountEnum forEach_CountEmu(int index){
        CountEnum[] myArray = CountEnum.values();
        for (CountEnum element : myArray) {
            if(index==element.getRetCode()){
                return element;
            }
        }
        return null;
    }
}


