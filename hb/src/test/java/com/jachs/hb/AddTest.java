package com.jachs.hb;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jachs.hb.entity.User;

/****
 * 添加数据测试
 * 
 * @author zhanchaohan
 *
 */
public class AddTest {
    private Connection connection;

    @Before
    public void initHbase () throws IOException {
//        System.setProperty ( "hadoop.home.dir", "F:\\hDriver" );
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

    //插入数据
    @Test
    public void insertData () throws IOException {
        TableName tablename = TableName.valueOf ( "jachsTestTR" );
        User user = new User ( "1", "un", "pw", "gender", "age", "phone", "email" );
        Put put = new Put ( ( "id" + user.getId () ).getBytes () );
        put.addColumn ( "username".getBytes (), "username".getBytes (), user.getUsername ().getBytes () );
        put.addColumn ( "password".getBytes (), "password".getBytes (), user.getPassword ().getBytes () );
        put.addColumn ( "gender".getBytes (), "gender".getBytes (), user.getGender ().getBytes () );
        put.addColumn ( "age".getBytes (), "age".getBytes (), user.getAge ().getBytes () );
        put.addColumn ( "phone".getBytes (), "phone".getBytes (), user.getPhone ().getBytes () );
        put.addColumn ( "email".getBytes (), "email".getBytes (), user.getEmail ().getBytes () );
        Table table = connection.getTable ( tablename );
        table.put ( put );
    }

    //循环插入
    @Test
    public void LoopInser () throws IOException{
        TableName tablename = TableName.valueOf ( "jachsTestTR" );
        for ( int k = 0 ; k < 10 ; k++ ) {
            Put put = new Put ( ( "id" + k+"我的ID" ).getBytes () );
            put.addColumn ( "我的名字".getBytes (), "UserName".getBytes (), (k+"username").getBytes () );
            put.addColumn ( "密码".getBytes (), "PassWord".getBytes (), (k+"password").getBytes () );
            put.addColumn ( "性别".getBytes (), "Gender".getBytes (), (k+"gender").getBytes () );
            put.addColumn ( "年龄".getBytes (), "Age".getBytes (), (k+"age").getBytes () );
            put.addColumn ( "电话".getBytes (), "Phone".getBytes (), (k+"phone").getBytes () );
            put.addColumn ( "邮箱".getBytes (), "Email".getBytes (), (k+"email").getBytes () );
            Table table = connection.getTable ( tablename );
            table.put ( put );
        }
    }
}
