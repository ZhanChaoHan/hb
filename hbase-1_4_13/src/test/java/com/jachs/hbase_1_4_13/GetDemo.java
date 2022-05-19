package com.jachs.hbase_1_4_13;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Result;
import org.junit.Before;
import org.junit.Test;

/***
 * 
 * @author zhanchaohan
 * @see InitDataDemo
 */
public class GetDemo {
	Configuration conf = HBaseConfiguration.create();
	HBaseAdmin admin=null;
	static final String tableName="scanTable";
	
	@Before
	public void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		conf.set("hbase.zookeeper.quorum", "zhanchaohan");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		
		admin = new HBaseAdmin(conf);
	}
	
	private void print(Result result) {
		for(Cell cell : result.rawCells()){
			System.out.println("行键:" + Bytes.toString(result.getRow()));
			System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
			System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
			System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
			System.out.println("时间戳:" + cell.getTimestamp());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
	}
	//rowKey查询
	@Test
	public void testc() throws IOException {
		HTable table = new HTable(conf, tableName);
		
		Get get=new Get("qweqe".getBytes());
		
		Result result=table.get(get);
		
		print(result);
	}
	//指定rowKey,指定列
	@Test
	public void test1() throws IOException {
		HTable table = new HTable(conf, tableName);
		
		Get get=new Get("qweqe".getBytes());
		
		get.addFamily("name".getBytes());
		
		Result result=table.get(get);
		
		print(result);
	}
	//指定rowKey,指定列,指定列族和列
	@Test
	public void test2() throws IOException {
		HTable table = new HTable(conf, tableName);
		
		Get get=new Get("qweqe".getBytes());
		//指定获取的列族
		get.addFamily("name".getBytes());
		//指定列族和列
		get.addColumn("name".getBytes(), "name:Upname".getBytes());
		
		Result result=table.get(get);
		
		print(result);
	}
}
