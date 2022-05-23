package com.jachs.hbase_1_4_13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

/****
 * 
 * @author zhanchaohan
 *
 */
public class Demo2 {
	Configuration conf = HBaseConfiguration.create();
	HBaseAdmin admin=null;
	static final String tableName="people";
	
	
	@Before
	public void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		conf.set("hbase.zookeeper.quorum", "zhanchaohan");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		
		admin = new HBaseAdmin(conf);
	}
	
	
	//删除多行数据
	public void deleteMultiRow(String tableName, String... rows) throws IOException{
		HTable hTable = new HTable(conf, tableName);
		List<Delete> deleteList = new ArrayList<Delete>();
		for(String row : rows){
			Delete delete = new Delete(Bytes.toBytes(row));
			deleteList.add(delete);
		}
		hTable.delete(deleteList);
		hTable.close();
	}
	//获取所有数据
	public void getAllRows(String tableName) throws IOException{
		HTable hTable = new HTable(conf, tableName);
		//得到用于扫描region的对象
		Scan scan = new Scan();
		//使用HTable得到resultcanner实现类的对象
		ResultScanner resultScanner = hTable.getScanner(scan);
		for(Result result : resultScanner){
			Cell[] cells = result.rawCells();
			for(Cell cell : cells){
				//得到rowkey
				System.out.println("行键:" + Bytes.toString(CellUtil.cloneRow(cell)));
				//得到列族
				System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
				System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
				System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
			}
		}
	}
	//获取某一行数据
	public  void getRow(String tableName, String rowKey) throws IOException{
		HTable table = new HTable(conf, tableName);
		Get get = new Get(Bytes.toBytes(rowKey));
		//get.setMaxVersions();显示所有版本
	    //get.setTimeStamp();显示指定时间戳的版本
		Result result = table.get(get);
		for(Cell cell : result.rawCells()){
			System.out.println("行键:" + Bytes.toString(result.getRow()));
			System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
			System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
			System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
			System.out.println("时间戳:" + cell.getTimestamp());
		}
	}
	//获取某一行指定“列族:列”的数据
	public  void getRowQualifier(String tableName, String rowKey, String family, String	 qualifier) throws IOException{
			HTable table = new HTable(conf, tableName);
			Get get = new Get(Bytes.toBytes(rowKey));
			get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
			Result result = table.get(get);
			for(Cell cell : result.rawCells()){
				System.out.println("行键:" + Bytes.toString(result.getRow()));
				System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
				System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
				System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
			}
	}
	//向表中插入数据
	public  void addRowData(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException{
		//创建HTable对象
		HTable hTable = new HTable(conf, tableName);
		//向表中插入数据
		Put put = new Put(Bytes.toBytes(rowKey));
		//向Put对象中组装数据
		put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
		hTable.put(put);
		hTable.close();
		System.out.println("插入数据成功");
	}
	
	//添加数据
	@Test
	public void test1() throws IOException {
		String row="row";
		
		addRowData(tableName,row,"info","info:name","zhangsan");
		addRowData(tableName,row,"info","info:age","18");
	}
	//查询数据
	@Test
	public void test2() throws IOException {
		String rowKey="tKey110";
		
		getRow(tableName,rowKey);
	}
	//删除数据
	@Test
	public void test3() throws IOException {
		String row="row";
		deleteMultiRow(tableName,row);
	}
	
	
	//添加多条
	@Test
	public void test11() throws IOException {
		for (int kk = 100; kk < 200; kk++) {
			String row="row"+kk;
			
			addRowData(tableName,row,"info","info:name","zhangsan"+kk);
			addRowData(tableName,row,"info","info:age","18"+kk);
		}
	}
	//删除多条
	@Test
	public void test22() throws IOException {
		for (int kk = 100; kk < 200; kk++) {
			String row="row"+kk;
			
			deleteMultiRow(tableName,row);
		}
	}
}
