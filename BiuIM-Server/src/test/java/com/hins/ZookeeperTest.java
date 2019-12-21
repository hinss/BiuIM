package com.hins;

import org.I0Itec.zkclient.ContentWatcher;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ZookeeperTest {

    @Value("${test}")
    private int NUM;


    private String serverIP = "127.0.0.1:2181";

    @Test
    public void Demo1() throws IOException {

        ZkClient zkClient = new ZkClient(serverIP,5000,15000,new SerializableSerializer());
        String path = "/config1";





    }

    @Test
    public void getZookeeperData(){

        String path = "/config1";

        ZkClient zkClient = new ZkClient(serverIP,5000,15000,new SerializableSerializer());

        Object o = zkClient.readData(path);
        System.out.println(o);

    }

    @Test
    public void testBytes() throws Exception {
        ZkClient zkClient = new ZkClient(serverIP, 2000, 30000, new BytesPushThroughSerializer());
        byte[] bytes = new byte[100];
        new Random().nextBytes(bytes);
        zkClient.createPersistent("/a", bytes);
        byte[] readBytes = zkClient.readData("/a");
        assertArrayEquals(bytes, readBytes);
    }

    @Test
    public void testSerializables() throws Exception {
        ZkClient zkClient = new ZkClient(serverIP, 2000, 30000, new SerializableSerializer());
        String data = "hello world";
        zkClient.createPersistent("/a", data);
        String readData = zkClient.readData("/a");
        assertEquals(data, readData);
    }

}

