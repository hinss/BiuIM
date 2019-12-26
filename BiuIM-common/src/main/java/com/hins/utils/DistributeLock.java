package com.hins.utils;

/**
 * 分布式锁
 */
public interface DistributeLock {


    void lock();

    void unlock();

    boolean tryLock();
}
