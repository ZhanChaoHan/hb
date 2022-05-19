package com.jachs.hbase_1_4_13;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

/***
 * 
 * @author zhanchaohan
 * @see InitDataDemo
 */
public class ScanDemo {
	Configuration conf = HBaseConfiguration.create();
	HBaseAdmin admin=null;
	static final String tableName="scanTable";
	
	@Before
	public void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		conf.set("hbase.zookeeper.quorum", "zhanchaohan");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		
		admin = new HBaseAdmin(conf);
	}
	
	private void print(ResultScanner resultScanner) {
		resultScanner.forEach(result->{
			String rowkey = Bytes.toString(result.getRow());
            System.out.println("row key :"+rowkey);
            Cell[] cells  = result.rawCells();
            for(Cell cell : cells) {
            	String rowKey=Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
    			String qualifier=Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
    			String tags=Bytes.toString(cell.getTagsArray(), cell.getTagsOffset(), cell.getTagsLength());
    			String value=Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            	
    			System.out.println(rowKey+"\t"+qualifier+"\t"+tags+"\t"+value+"\t");
            }
            System.out.println("--------------------------");
		});
	}
	//全查询
	@Test
	public void testc() throws IOException {
		Scan scan=new Scan();
		
		HTable table = new HTable(conf, tableName);
		
		ResultScanner  resultScanner=table.getScanner(scan);
		
		print(resultScanner);
	}
	//限制条数
	@Test
	public void test2() throws IOException {
		Scan scan=new Scan();
		
		scan.setLimit(5);
		HTable table = new HTable(conf, tableName);
		
		ResultScanner  resultScanner=table.getScanner(scan);
		
		print(resultScanner);
	}
		
	
}
