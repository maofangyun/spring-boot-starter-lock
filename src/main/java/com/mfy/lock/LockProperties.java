package com.mfy.lock;


/**
 * @author maofangyun
 * @date 2021/9/6 14:45
 */
public class LockProperties {

    private String name;

    private int expire;

    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
