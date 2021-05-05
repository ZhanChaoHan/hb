package com.jachs.hb;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/***
 * 表操作测试
 * 
 * @author zhanchaohan
 *
 */
public class TableTest {
	private Connection connection;

	@Before
	public void initHbase() throws IOException {
//        System.setProperty ( "hadoop.home.dir", "F:\\hDriver" );
		Configuration configuration = HBaseConfiguration.create();
		// configuration.set ( "hbase.zookeeper.property.clientPort", "2181" );
		// configuration.set ( "hbase.zookeeper.quorum", "http://101.200.126.12" );
		// configuration.set ( "hbase.master", "http://101.200.126.12:16010" );
		connection = ConnectionFactory.createConnection(configuration);
	}

	@After
	public void destroy() {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// scan扫描表
	@Test
	public void ScanTableTest() {
		TableName tn = TableName.valueOf("jachsTestTR");

		HTable hTable;
		try {
			hTable = (HTable) connection.getTable(tn);

			Scan scan = new Scan();
			scan.addColumn(Bytes.toBytes("id"), Bytes.toBytes("name"));
			ResultScanner scanner = hTable.getScanner(scan);

			for (Result r : scanner) {
				System.out.println("Found row : " + r);
			}
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 列举所有表
	@Test
	public void ListTable() throws IOException {
		HTableDescriptor[] tableDescriptor = connection.getAdmin().listTables();
		for (int i = 0; i < tableDescriptor.length; i++) {
			System.out.println(tableDescriptor[i].getNameAsString());
		}
	}

	// 查看表元描述
	@Test
	public void ShowTableDes() throws IOException {
		TableName tn = TableName.valueOf("jachsTestTR");
		HTableDescriptor hd = connection.getAdmin().getTableDescriptor(tn);
		System.out.println(hd.getFamilies().toString());
	}

	// 创建表
	@Test
	public void createTable() throws IOException {
		String[] cols = new String[] { "id", "username", "password", "gender", "age", "phone", "email" };// 表字段
		TableName tableName = TableName.valueOf("jachsTestTR");// 表名称
		Admin admin = connection.getAdmin();
		if (admin.tableExists(tableName)) {
			System.out.println("表已存在！");
		} else {
			HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
			for (String col : cols) {
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
				hTableDescriptor.addFamily(hColumnDescriptor);
			}
			admin.createTable(hTableDescriptor);
			admin.close();
		}
	}

}
