package com.jachs.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.admin.ZooKeeperAdmin;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.DumbWatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author zhanchaohan
 * 
 */
public class TestZkCreateAndDelete {
    private static ZooKeeperAdmin zka;
    private static final String connectString = "127.0.0.1:2181";
    private static final int sessionTimeout = 5000;
    //已知全部watcher 实现类:DumbWatcher, NettyServerCnxn, NIOServerCnxn, ServerCnxn
    private final static Watcher watcher = new DumbWatcher ();
    private final static String NODEPATH="/jachs";//测试用根节点路径
    private final static ArrayList<ACL>ACLLIST=Ids.OPEN_ACL_UNSAFE;
    private final static CreateMode CM=CreateMode.PERSISTENT;
    
    @BeforeAll
    public static void init () throws IOException {
        zka = new ZooKeeperAdmin ( connectString, sessionTimeout, watcher );
    }

    @AfterAll
    public static void destroy () throws InterruptedException {
        zka.close ();
    }
    //创建一个节点，存入一条数据并读取
    @Test
    public void createNode () throws KeeperException, InterruptedException {
        if ( zka.exists ( NODEPATH, true ) == null ) {
            zka.create ( NODEPATH, "一个测试".getBytes (), ACLLIST, CM );
        }
        System.out.println ( "查看/jachs节点数据:get /node => " + new String ( zka.getData ( NODEPATH, false, null ) ) );
    }
    //给定节点下添加多个子节点
    @Test
    public void AddDataToNode() throws KeeperException, InterruptedException {
        if ( zka.exists (NODEPATH, true ) == null ) {//节点不存在创建节点
            zka.create ( NODEPATH, "一个测试".getBytes (), ACLLIST, CM );
        }
        for ( int kk = 0 ; kk < 20 ; kk++ ) {
            zka.create ( "/jachs/childNode"+kk, (kk+"一个测试").getBytes (), ACLLIST, CM );
        }
        System.out.println ( "查看/jachs节点数据:get /node => " + new String ( zka.getData ( "/jachs", false, null ) ) );
    }
    //获取给定节点全部子节点
    @Test
    public void GetNodeChild() throws KeeperException, InterruptedException {
        List<String> childList= zka.getChildren ( NODEPATH, true );
        for ( String string : childList ) {
            System.out.println ( string );
        }
    }
    //替换节点内容
    @Test
    public void SetData() throws KeeperException, InterruptedException {
        byte [] data=zka.getData ( NODEPATH, watcher, null);//查询旧的内容
        System.out.println ( "原内容:"+new String ( data ) );
        
        Stat stat=zka.setData ( NODEPATH, "新的内容".getBytes (), -1 );//添加新的内容
        
        byte [] newData=zka.getData ( NODEPATH, watcher, stat);//查询覆盖后的内容
        System.out.println ( "新内容："+new String ( newData ) );
    }
}
