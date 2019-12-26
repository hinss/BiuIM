package com.hins.utils;

import com.hins.serialize.impl.MySerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * @Description: zk实现的分布式锁(使用临时顺序节点的方式)
 * @Version: 1.0.0
 * @Author:Wyman
 * @Date:2019/12/25
 */
public class ZkDistributedLock implements DistributeLock {


    private String lockPath;

    private ZkClient zkClient;

    private ThreadLocal<String> currentPath = new ThreadLocal<>();

    private ThreadLocal<String> beforePath = new ThreadLocal<>();


    public ZkDistributedLock(String lockPath){
        super();
        this.lockPath = lockPath;

        zkClient = new ZkClient("localhost:2181");
        zkClient.setZkSerializer(new MySerializer());
        //创建目录节点为永久节点，所有的顺序子节点才是临时节点
        if(!this.zkClient.exists(lockPath)){

            this.zkClient.createPersistent(lockPath);
        }

    }


    @Override
    public boolean tryLock() {

        //暂时使用不用不可重入的方式实现

        if(currentPath.get() == null){
            //创建一个临时顺序节点
            String aaa = this.zkClient.createEphemeralSequential(lockPath, "aaa");

            currentPath.set(aaa);
        }

        //获得所有的节点
        List<String> children = this.zkClient.getChildren(lockPath);

        //排序
        Collections.sort(children);

        //判断当前节点是否最小的
        if(currentPath.get().equals(lockPath + "/" + children.get(0))){

            return true;
        }else{
            //设置前一个元素的节点名称
            int curIndex = children.indexOf(currentPath.get().substring(lockPath.length() + 1));
            beforePath.set(lockPath + "/" + children.get(curIndex - 1));
        }

        return false;
    }

    @Override
    public void lock() {

        if(!tryLock()){

            waitForLock();

            lock();
        }

    }

    @Override
    public void unlock() {

        zkClient.delete(lockPath + "/" + currentPath.get());


    }

    private void waitForLock(){

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

                System.out.println("====监听到节点被删除====");
                countDownLatch.countDown();
            }
        };

        //监听前一个节点的变化
        zkClient.subscribeDataChanges(this.beforePath.get(), listener);

        //让自己阻塞
        if(this.zkClient.exists(beforePath.get())){

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //醒来后，取消订阅
        zkClient.unsubscribeDataChanges(this.beforePath.get(),listener);

    }


    public static void main(String[] args) {

        //使用CyclicBarrier 模拟并发测试

        //并发数
        int currency = 1;

        //循环屏障
        CyclicBarrier cb = new CyclicBarrier(currency);

        //多线程模拟高并发
        for(int i = 0; i < currency; i++){

            new Thread(() -> {

                System.out.println("=====我准备好啦=====");

                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                ZkDistributedLock zkDistributedLock = new ZkDistributedLock("/config2");


                try {
                    //并发抢锁
                    zkDistributedLock.lock();
                    System.out.println(Thread.currentThread().getName() + "获得锁! ");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    zkDistributedLock.unlock();
                }

            }).start();

        }



    }



}
