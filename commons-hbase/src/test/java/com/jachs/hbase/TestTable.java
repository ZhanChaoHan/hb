package com.jachs.hbase;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.navercorp.pinpoint.common.hbase.HbaseOperations2;
import com.navercorp.pinpoint.common.hbase.HbaseTableFactory;

/**
 * @author zhanchaohan
 * 
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:/applicationContext-web-hbase.xml" } )
public class TestTable {
    @Autowired
    private HbaseOperations2 hbaseOperations2;
    @Autowired
    private HbaseTableFactory hbaseTableFactory;

    private static final String TABLENAME="people";
    
    
    //全表读取
    @Test
    public void crTble () throws IOException {
        Table table = hbaseTableFactory.getTable ( TableName.valueOf (TABLENAME) );
        Scan scan = new Scan ();
        ResultScanner rs = table.getScanner ( scan );
        Iterator<Result> ir = rs.iterator ();
        while ( ir.hasNext () ) {
        	Result result=ir.next ();
        	
        	String row=new String(result.getRow());//行key
        	
            System.out.println (row);
        }
    }
    
    //添加单条数据
    @Test
    public void testc() {
    	hbaseOperations2.put( TableName.valueOf (TABLENAME), "jachsRow".getBytes(), "info".getBytes(), "info:name".getBytes(), "handsome".getBytes());
    }
    
    
}
