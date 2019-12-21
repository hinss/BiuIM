package com.hins;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 使用ZK做配置中心监视器的简单示例
 */
public class ZKConfigWatcher implements Watcher {

    /** zk变量 */
    private ZooKeeper zk = null;

    /**
     * 创建ZK连接
     * @param connectAddr ZK服务器地址列表
     * @param sessionTimeout Session超时时间
     */
    public void createConnection(String connectAddr, int sessionTimeout) {
        this.releaseConnection();
        try {
            //this表示把当前对象进行传递到其中去（也就是在主函数里实例化的new ZooKeeperWatcher()实例对象）
            zk = new ZooKeeper(connectAddr, sessionTimeout, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭ZK连接
     */
    public void releaseConnection() {
        if (this.zk != null) {
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 读取指定节点数据内容
     * @param path 节点路径
     * @return
     */
    public String readData(String path, boolean needWatch) {
        try {
            System.out.println("读取数据操作...");
//            System.out.println("新数据为:" + new String(this.zk.getData(path, needWatch, null)));
            return new String(this.zk.getData(path, needWatch, null));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 更新指定节点数据内容
     * @param path 节点路径
     * @param data 数据内容
     * @return
     */
    public boolean writeData(String path, String data) {
        try {
            System.out.println("更新数据成功，path：" + path + ", stat: " +
                    this.zk.setData(path, data.getBytes(), -1));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public void process(WatchedEvent event) {

        System.out.println("进入 proces... event = " + event);

        if(event == null){
            return ;
        }

        // 获得C/S连接状态
        Event.KeeperState keeperState = event.getState();
        // 获得事件类型
        Event.EventType eventType = event.getType();
        // 监听的path
        String path = event.getPath();

        //  连接状态为
        if(Event.KeeperState.SyncConnected == keeperState){

            if(Event.EventType.NodeDataChanged == eventType){
                System.out.println("ZK节点数据更新，配置修改");
                String newData =  readData(path,true);

                System.out.println("更新配置操作，获取的新数据为: " + newData);
            }

        }
    }


    public static void main(String[] args) {

        ZKConfigWatcher zkConfigWatcher = new ZKConfigWatcher();
        zkConfigWatcher.createConnection("localhost:2181",20000);

        //一开始读取配置
        String result = zkConfigWatcher.readData("/config1",true);
        System.out.println(result);

        //模拟有别的用户改了配置
//        zkConfigWatcher.writeData("/config1","port:9090");


        try {
            //阻塞主线程让展示完成
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
