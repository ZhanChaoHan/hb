package com.jachs.hbase_1_4_13;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.xerces.util.SynchronizedSymbolTable;
import org.junit.Before;
import org.junit.Test;


/***
 * 建表,删表
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
	//获取表信息
	@Test
	public void testRead() throws Exception {
		HTableDescriptor hTableDescriptor=admin.getTableDescriptor(TableName.valueOf(tableName));
		
		//获取全部列
		for (HColumnDescriptor entry : hTableDescriptor.getColumnFamilies()) {
			System.out.println(entry.getNameAsString());
		}
		System.out.println("--------------FamiliesKeys");
		for (byte[] entry : hTableDescriptor.getFamiliesKeys()) {
			System.out.println(new String(entry));
		}
		System.out.println("-----------Coprocessors");
		for (String entry : hTableDescriptor.getCoprocessors()) {
			System.out.println(entry);
		}
		System.out.println("------------Configuration");
		for (String key : hTableDescriptor.getConfiguration().keySet()) {
			System.out.println(key+"\t"+ hTableDescriptor.getConfiguration().get(key));
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
	//创建命名空间
	@Test
	public void test5() throws IOException {
		String desName="AAAA";
		
		NamespaceDescriptor namespaceDescriptor=NamespaceDescriptor.create(desName).build();
        admin.createNamespace(namespaceDescriptor);
	}
	//合并数据
	@Test
	public void test3() throws Exception {
		if(isTableExist(tableName)){
			admin.majorCompact(TableName.valueOf(tableName));
			System.out.println("表" + tableName + "合并成功！");
		}else{
			System.out.println("表" + tableName + "不存在！");
		}
	}
	
}
