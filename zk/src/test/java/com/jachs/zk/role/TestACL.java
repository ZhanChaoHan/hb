package com.jachs.zk.role;

import java.util.ArrayList;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.ACL;
import org.junit.jupiter.api.Test;

/**
 * @author zhanchaohan
 * @see https://zookeeper.apache.org/doc/r3.6.2/apidocs/zookeeper-server/index.html
 * 权限控制
 * @see org.apache.zookeeper.ZooDefs
 * 生成策略
 * @see org.apache.zookeeper.CreateMode
 * 权限自定义测试类
 * @see RoleTest
 */
public class TestACL {

    /***
     * Node节点的权限控制，官方给出默认的三个
     * @author zhanchaohan
     */
    @Test
    public void testACL() {
        //所有人都可以访问的权限
        ArrayList<ACL> aclL= Ids.OPEN_ACL_UNSAFE;
        //这个ACL给创建者身份验证id的所有权限。
        ArrayList<ACL> aclL1= Ids.CREATOR_ALL_ACL;
        //只读
        ArrayList<ACL> aclL2= Ids.READ_ACL_UNSAFE;
        
    }
    /***
     * Node节点的生成策略<br>
     * 源码路径:org.apache.zookeeper.CreateMode
     * @author zhanchaohan
     * 默认给出的7种策略如下，想自定义策略创建重写源码CreateMode（int,booble,booble,booble,booble）即可
     */
    @Test
    public void testCreateMode() {
        //当客户端断开连接时，znode不会自动删除。
        CreateMode cm = CreateMode.PERSISTENT;
        //当客户端断开连接时，znode不会自动删除，它的名字后面会加上一个单调递增的数字。
        CreateMode cm1 = CreateMode.PERSISTENT_SEQUENTIAL;
        //当客户端断开连接时，znode将被删除。
        CreateMode cm2 = CreateMode.EPHEMERAL;
        //断开连接后，znode将被删除，并显示其名称将附加一个单调递增的数字。
        CreateMode cm3 = CreateMode.EPHEMERAL_SEQUENTIAL;
        //znode将是一个容器节点。集装箱节点是一种特殊用途的节点，对诸如leader、lock和，当容器的最后一个子容器被删除时，等在将来某个时间被服务器删除的候选对象。考虑到这个属性，你应该准备好{@链接org.apache.zookeeper.KeeperException.NoNodeException}在此容器节点内创建子级时。
        CreateMode cm4 = CreateMode.CONTAINER;
        //当客户端断开连接时，znode不会自动删除。但是，如果znode没有在给定的TTL中修改，则一旦没有子项，将被删除。
        CreateMode cm5 = CreateMode.PERSISTENT_WITH_TTL;
        //当客户端断开连接时，znode不会自动删除，它的名字后面会加上一个单调递增的数字。但是，如果znode没有在给定的TTL中修改，则一旦没有子项，将被删除。
        CreateMode cm6 = CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL;
    }
    
}
