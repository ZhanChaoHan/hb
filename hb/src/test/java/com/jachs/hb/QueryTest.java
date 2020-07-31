/*
 * 创建人: zhanchaohan
 */
package com.jachs.hb;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QueryTest {
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
    //全表扫描
    @Test
    public void scanTable () throws IOException {
        HTable table = (HTable) connection.getTable ( TableName.valueOf ( "jachsTestTR" ) );
        Scan scan = new Scan (); //  创建扫描对象
        ResultScanner results = table.getScanner ( scan ); //  全表的输出结果
        for ( Result result : results ) {
            for ( Cell cell : result.rawCells () ) {
                System.out.println ( "行键:" + 
                        new String ( CellUtil.cloneRow ( cell ) ) + "\t"
                        + "列族:" + 
                        new String ( CellUtil.cloneFamily ( cell ) ) + "\t" + "列名:"
                        + new String ( CellUtil.cloneQualifier ( cell ) ) + "\t" + "值:"
                        + new String ( CellUtil.cloneValue ( cell ) ) + "\t" + "时间戳:"
                        + cell.getTimestamp () );
            }
        }
        results.close ();
        table.close ();
    }
}
