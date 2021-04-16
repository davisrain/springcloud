package com.dzy;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Zookeeper {

    private ZooKeeper zk;
    private static final int SESSION_TIMEOUT=60000 * 30;

    @Before
    public void before() throws IOException {
        zk = new ZooKeeper("47.105.108.96:2181", SESSION_TIMEOUT, e -> {
            System.out.println("默认回调函数");
        });
    }
    @Test
    public void ls() throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren("/",e -> {
            System.out.println("自定义回调函数");
        });
        System.out.println("===================================");
        for (String child : children) {
            System.out.println(child);
        }
        System.out.println("===================================");
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    @Test
    public void create() throws KeeperException, InterruptedException {
        String s = zk.create("/idea2", "idea2020".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
    }

    @Test
    public void get() throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        byte[] data = zk.getData("/idea2", e -> {
            System.out.println("/idea2的值改变了");
        }, stat);
        System.out.println(new String(data));
        System.out.println(stat);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void set() throws KeeperException, InterruptedException {
        Stat stat = zk.setData("/idea2", "abc".getBytes(), 2);
        System.out.println(stat);
    }

    @Test
    public void stat() throws KeeperException, InterruptedException {
        Stat stat = zk.exists("/idea", false);
        if(stat == null)
            System.out.println("节点不存在");
        else
            System.out.println(stat);
    }

    @Test
    public void delete() throws KeeperException, InterruptedException {
        Stat stat = zk.exists("/idea2", false);
        int version = stat.getVersion();
        zk.delete("/idea2", version);
    }


    public void register() {
        try {
            byte[] data = zk.getData("/idea1", new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("节点数据发生改变");
                    register();
                }
            }, null);
            System.out.println(new String(data));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testRegister() throws InterruptedException {
        register();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @After
    public void after() throws InterruptedException {
        zk.close();
    }

}
