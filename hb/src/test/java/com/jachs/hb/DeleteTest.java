/*
 * 创建人: zhanchaohan
 */
package com.jachs.hb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeleteTest {
    private Connection connection;

    @Before
    public void initHbase () throws IOException {
        System.setProperty ( "hadoop.home.dir", "F:\\hDriver" );
        Configuration configuration = HBaseConfiguration.create ();
        //        configuration.set ( "hbase.zookeeper.property.clientPort", "2181" );
        //        configuration.set ( "hbase.zookeeper.quorum", "http://101.200.126.12" );
        //        configuration.set ( "hbase.master", "http://101.200.126.12:16010" );
        connection = ConnectionFactory.createConnection ( configuration );
    }

    @After
    public void destroy () {
        try {
            connection.close ();
        }
        catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
    //删除数据表
    @Test
    public void droptable () throws IOException {
        TableName tn=TableName.valueOf ( "jachsTest" );
        Admin admin = connection.getAdmin ();
        
        if (admin.tableExists(tn)) {  
            admin.disableTable(tn);  
            admin.deleteTable(tn);  
        }
        admin.close();
    }
    @Test
    public  void deletedata() throws IOException{
        List list = new ArrayList();
        Configuration conf = HBaseConfiguration.create();
        HTable table = new HTable(conf, "jachsTest");
        Delete d1 = new Delete("".getBytes());
        list.add(d1);
          
        table.delete(list);  
        System.out.println("data deleted!");
    }

}
