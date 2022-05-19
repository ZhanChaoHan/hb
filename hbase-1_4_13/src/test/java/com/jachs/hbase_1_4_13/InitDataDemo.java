package com.jachs.hbase_1_4_13;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

/***
 * 
 * @author zhanchaohan
 *
 */
public class InitDataDemo {
	Configuration conf = HBaseConfiguration.create();
	HBaseAdmin admin=null;
	static final String tableName="scanTable";
	
	@Before
	public void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		conf.set("hbase.zookeeper.quorum", "zhanchaohan");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		
		admin = new HBaseAdmin(conf);
	}
	//创建测试表
	@Test
	public void create() throws IOException {
		String []columnFamily=new String[] {"name","age","address","gender"};
		HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
		for(String cf : columnFamily){
			descriptor.addFamily(new HColumnDescriptor(cf));
		}
		admin.createTable(descriptor);
		System.out.println("表" + tableName + "创建成功！");
	}
	//添加测试数据
	@Test
	public void testq() throws IOException {
		String[]userList=new String[] {
				"李四1","qweqe","dwa","vre","donkey","皮皮虾","queen","king","thief",
				"网","王","接口","看来","dhuah"
		};
		HTable hTable = new HTable(conf, tableName);
			
		for (int kk = 0; kk < userList.length; kk++) {
			Put put = new Put(Bytes.toBytes(userList[kk]));
				
			put.add("name".getBytes(), "name:name".getBytes(),("小写"+ userList[kk]).getBytes());
			put.add("name".getBytes(), "name:Upname".getBytes(),("大写"+userList[kk].toUpperCase()).getBytes());
			
			put.add("age".getBytes(), "age:age".getBytes(), ( (int)(Math.random()*100+1)+"").getBytes());
			put.add("address".getBytes(), "address:address".getBytes(), ("地址"+kk).getBytes());
			put.add("gender".getBytes(), "gender:gender".getBytes(), (kk%2==0?"男":"女").getBytes());
				
			hTable.put(put);
			hTable.close();
			System.out.println(userList[kk]+"插入数据成功");
		}
	}
}
