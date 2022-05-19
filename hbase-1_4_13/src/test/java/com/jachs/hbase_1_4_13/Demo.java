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
import org.junit.Before;
import org.junit.Test;


/***
 * 
 * @author zhanchaohan
 *
 */
public class Demo {
	Configuration conf = HBaseConfiguration.create();
	HBaseAdmin admin=null;
	
	static final String tableName="people";
	
	@Before
	public void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		conf.set("hbase.zookeeper.quorum", "zhanchaohan");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		
		admin = new HBaseAdmin(conf);
	}
	
	private boolean isTableExist(String tableName) throws IOException { 
		return admin.tableExists(tableName);
	}

	//建表
	@Test
	public void test1() throws IOException {
		String []columnFamily=new String[] {"info"};
		
		//判断表是否存在
		if(isTableExist(tableName)){
			System.out.println("表" + tableName + "已存在");
		}else{
			//创建表属性对象,表名需要转字节
			HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
			//创建多个列族
			for(String cf : columnFamily){
				descriptor.addFamily(new HColumnDescriptor(cf));
			}
			//根据对表的配置，创建表
			admin.createTable(descriptor);
			System.out.println("表" + tableName + "创建成功！");
		}
	}
	
	//删表
	@Test
	public void test2() throws Exception{
		if(isTableExist(tableName)){
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println("表" + tableName + "删除成功！");
		}else{
			System.out.println("表" + tableName + "不存在！");
		}
	}
	
}
