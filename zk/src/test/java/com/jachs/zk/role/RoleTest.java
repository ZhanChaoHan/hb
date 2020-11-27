package com.jachs.zk.role;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.admin.ZooKeeperAdmin;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.DumbWatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * ACL 权限控制，使用：schema,id,permission 来标识，主要涵盖 3 个方面：<br>
 * 权限模式（Schema）：鉴权的策略<br>
 * 授权对象（ID）<br>
 * 权限（Permission）<br>
 * 
 * 
 * ------------------------------------ Id:<br>
 * IP:通常是一个IP或一个IP段如:192.168.1.145或192.168.0.1/25<br>
 * Digest:自定义,通常是BASE64码或SHA<br>
 * World:只有一个代表全部'anyone'<br>
 * Super:代表超级管理员，拥有所有的权限<br>
 * ------------------------------------ 权限permission:<br>
 * CREATE: 能创建子节点<br>
 * READ：能获取节点数据和列出其子节点<br>
 * WRITE: 能设置节点数据<br>
 * DELETE: 能删除子节点<br>
 * ADMIN: 能设置权限<br>
 * ---------------------------------- schema权限包括：<br>
 * world：默认方式，相当于全世界都能访问<br>
 * auth：代表已经认证通过的用户(cli中可以通过addauth digest user:pwd 来添加当前上下文中的授权用户)<br>
 * digest：即用户名:密码这种方式认证，这也是业务系统中最常用的<br>
 * ip：使用Ip地址认证<br>
 * 
 * @author zhanchaohan
 * 
 */
public class RoleTest {
    private static ZooKeeperAdmin zka;
    private static final String connectString = "127.0.0.1:2181";
    private static final int sessionTimeout = 5000;
    //已知全部watcher 实现类:DumbWatcher, NettyServerCnxn, NIOServerCnxn, ServerCnxn
    private final static Watcher watcher = new DumbWatcher ();

    private final static String NODEPATH = "/role";//测试用根节点路径
    //    private final static ArrayList<ACL>ACLLIST=Ids.OPEN_ACL_UNSAFE;
    private final static CreateMode CM = CreateMode.PERSISTENT;

    @BeforeAll
    public static void init () throws IOException {
        zka = new ZooKeeperAdmin ( connectString, sessionTimeout, watcher );
    }

    @AfterAll
    public static void destroy () throws InterruptedException {
//        zka.close ();
    }

    @Test
    public void testsId () throws KeeperException, InterruptedException {
        ArrayList<ACL> ACLLIST = new ArrayList<> ();

        ACL acl = new ACL ();
//        acl.setId ( new Id ( "ip", "127.0.0.1" ) );//本机可以
        acl.setId ( new Id ( "ip", "192.168.2.158" ) );//非本机不行
        acl.setPerms ( Perms.ALL );//permissions

        ACLLIST.add ( acl );

        if ( zka.exists ( NODEPATH, false ) != null ) {//如果节点存在干掉重新创建用给定的权限集合
            zka.delete ( NODEPATH, -1 );
        }
        zka.create ( NODEPATH, "一个测试".getBytes (), ACLLIST, CM );
        byte[] reData = zka.getData ( NODEPATH, watcher, null );
        System.out.println ( "查询的结果:" + new String ( reData ) );
        
        Stat stat = zka.setData ( NODEPATH, "闪电五连鞭".getBytes (), -1 );
        byte[] reSetData = zka.getData ( NODEPATH, watcher, stat );
        System.out.println ( "重新设置的结果:" + new String ( reSetData ) );
    }
    @Test
    public void testPermission() throws KeeperException, InterruptedException {
        ArrayList<ACL> ACLLIST = new ArrayList<> ();

        ACL acl = new ACL ();
        acl.setId ( new Id ( "world", "anyone" ) );//Id放开全部都可以操作
//        acl.setPerms ( Perms.ADMIN );//能设置权限但本身没有权限
//        acl.setPerms ( Perms.ALL );//全部权限
//        acl.setPerms ( Perms.CREATE );//可以删除，可以创建
        acl.setPerms ( Perms.READ );//不能写入

        ACLLIST.add ( acl );

        if ( zka.exists ( NODEPATH, false ) != null ) {//如果节点存在干掉重新创建用给定的权限集合
            zka.delete ( NODEPATH, -1 );
            System.out.println ( "节点存在已删除" );
        }
        String createInfo=zka.create ( NODEPATH, "一个测试".getBytes (), ACLLIST, CM );
        System.out.println ( "创建了节点:" +createInfo);
        byte[] reData = zka.getData ( NODEPATH, watcher, null );
        System.out.println ( "查询的结果:" + new String ( reData ) );
        
        Stat stat = zka.setData ( NODEPATH, "闪电五连鞭".getBytes (), -1 );
        byte[] reSetData = zka.getData ( NODEPATH, watcher, stat );
        System.out.println ( "重新设置的结果:" + new String ( reSetData ) );
    }
}
